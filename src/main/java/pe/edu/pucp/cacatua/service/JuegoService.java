package pe.edu.pucp.cacatua.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.cacatua.dto.CeldaDto;
import pe.edu.pucp.cacatua.dto.EstadoJuegoDto;
import pe.edu.pucp.cacatua.model.Celda;
import pe.edu.pucp.cacatua.model.CeldaInicial;
import pe.edu.pucp.cacatua.model.EstadoInicial;
import pe.edu.pucp.cacatua.model.EstadoJuego;
import pe.edu.pucp.cacatua.repository.CeldaInicialRepository;
import pe.edu.pucp.cacatua.repository.CeldaRepository;
import pe.edu.pucp.cacatua.repository.EstadoInicialRepository;
import pe.edu.pucp.cacatua.repository.EstadoJuegoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Logica de negocio del juego: mover/picotear, validar limites del tablero,
 * detectar victoria/derrota y reiniciar la partida desde las tablas de respaldo.
 * Toda la informacion (tablero, posicion, energia) viene SIEMPRE de la base de
 * datos, nunca de variables hardcodeadas en el controlador.
 */
@Service
public class JuegoService {

    public enum Direccion { U, D, L, R }

    @Autowired
    private CeldaRepository celdaRepository;
    @Autowired
    private CeldaInicialRepository celdaInicialRepository;
    @Autowired
    private EstadoJuegoRepository estadoJuegoRepository;
    @Autowired
    private EstadoInicialRepository estadoInicialRepository;

    /** Arma la lista de CeldaDto que necesita el fragmento "tablero". */
    @Transactional
    public List<CeldaDto> obtenerCeldasDto() {
        EstadoJuego estado = obtenerEstado();
        List<Celda> celdas = celdaRepository.findAllByOrderByFilaAscColumnaAsc();
        return celdas.stream()
                .map(c -> new CeldaDto(
                        c.getFila(),
                        c.getColumna(),
                        c.getNivel(),
                        c.getFila() == estado.getFilaActual() && c.getColumna() == estado.getColumnaActual(),
                        c.isEsSemilla()))
                .collect(Collectors.toList());
    }

    /**
     * Agrupa los CeldaDto por fila, para que la vista pueda dibujar el
     * tablero como una matriz (una <tr> por fila, con th:each anidado).
     */
    @Transactional
    public List<List<CeldaDto>> obtenerFilasDto() {
        List<CeldaDto> planas = obtenerCeldasDto();
        List<List<CeldaDto>> filasAgrupadas = new ArrayList<>();
        int filaActual = -1;
        List<CeldaDto> filaEnCurso = null;
        for (CeldaDto c : planas) {
            if (c.getFila() != filaActual) {
                filaActual = c.getFila();
                filaEnCurso = new ArrayList<>();
                filasAgrupadas.add(filaEnCurso);
            }
            filaEnCurso.add(c);
        }
        return filasAgrupadas;
    }

    /** Arma el EstadoJuegoDto que necesita el fragmento "panel". */
    @Transactional
    public EstadoJuegoDto obtenerEstadoDto() {
        EstadoJuego estado = obtenerEstado();
        return new EstadoJuegoDto(estado.getPicotazosRestantes(), estado.isFinalizado(), estado.isGano());
    }

    private EstadoJuego obtenerEstado() {
        return estadoJuegoRepository.findById(1)
                .orElseThrow(() -> new IllegalStateException(
                        "No existe estado_juego; ejecute cacatua_sulfurea.sql antes de iniciar"));
    }

    private int filas() {
        return celdaRepository.findAllByOrderByFilaAscColumnaAsc().stream()
                .mapToInt(Celda::getFila).max().orElse(0) + 1;
    }

    private int columnas() {
        return celdaRepository.findAllByOrderByFilaAscColumnaAsc().stream()
                .mapToInt(Celda::getColumna).max().orElse(0) + 1;
    }

    /**
     * Procesa un movimiento del ave. Reglas (segun el enunciado):
     * - Si la celda destino esta fuera del tablero: no pasa nada (sin consumir energia).
     * - Si la partida ya termino: no permite mas acciones.
     * - Si la celda destino tiene madera (nivel 1,2,3): se "picotea" (nivel - 1),
     *   el ave NO se mueve, se descuenta un picotazo.
     * - Si la celda destino esta libre (nivel 0): el ave se mueve ahi, se descuenta un picotazo.
     * - Luego se evalua fin de partida: derrota si se quedo sin picotazos sin llegar a la
     *   semilla; victoria si esta sobre la semilla y aun le queda al menos un picotazo.
     */
    @Transactional
    public void procesarMovimiento(Direccion direccion) {
        EstadoJuego estado = obtenerEstado();
        if (estado.isFinalizado()) {
            return; // partida ya termino, no se aceptan mas acciones
        }

        int filaDestino = estado.getFilaActual();
        int columnaDestino = estado.getColumnaActual();
        switch (direccion) {
            case U -> filaDestino--;
            case D -> filaDestino++;
            case L -> columnaDestino--;
            case R -> columnaDestino++;
        }

        // Restriccion de vuelo: fuera del tablero -> no hace nada
        if (filaDestino < 0 || filaDestino >= filas() || columnaDestino < 0 || columnaDestino >= columnas()) {
            return;
        }

        Optional<Celda> celdaDestinoOpt = celdaRepository.findByFilaAndColumna(filaDestino, columnaDestino);
        if (celdaDestinoOpt.isEmpty()) {
            return;
        }
        Celda celdaDestino = celdaDestinoOpt.get();

        if (celdaDestino.getNivel() > 0) {
            // Picoteo: reduce la dureza, el ave no se desplaza
            celdaDestino.setNivel(celdaDestino.getNivel() - 1);
            celdaRepository.save(celdaDestino);
        } else {
            // Casilla libre: el ave se desplaza
            estado.setFilaActual(filaDestino);
            estado.setColumnaActual(columnaDestino);
        }

        estado.setPicotazosRestantes(estado.getPicotazosRestantes() - 1);
        evaluarFinDePartida(estado, celdaDestino);
        estadoJuegoRepository.save(estado);
    }

    private void evaluarFinDePartida(EstadoJuego estado, Celda celdaDestino) {
        boolean llegoASemilla = celdaDestino.isEsSemilla()
                && estado.getFilaActual() == celdaDestino.getFila()
                && estado.getColumnaActual() == celdaDestino.getColumna();

        if (llegoASemilla && estado.getPicotazosRestantes() > 0) {
            estado.setFinalizado(true);
            estado.setGano(true);
        } else if (estado.getPicotazosRestantes() <= 0) {
            estado.setFinalizado(true);
            estado.setGano(false);
        }
    }

    /** Restaura tablero y estado desde las tablas de respaldo (tablero_inicial / estado_inicial). */
    @Transactional
    public void reiniciar() {
        List<CeldaInicial> respaldo = celdaInicialRepository.findAllByOrderByFilaAscColumnaAsc();
        List<Celda> actuales = celdaRepository.findAllByOrderByFilaAscColumnaAsc();

        for (int i = 0; i < actuales.size() && i < respaldo.size(); i++) {
            Celda actual = actuales.get(i);
            CeldaInicial original = respaldo.get(i);
            actual.setNivel(original.getNivel());
            celdaRepository.save(actual);
        }

        EstadoInicial estadoInicial = estadoInicialRepository.findById(1)
                .orElseThrow(() -> new IllegalStateException("No existe estado_inicial de respaldo"));

        EstadoJuego estado = obtenerEstado();
        estado.setFilaActual(estadoInicial.getFilaInicial());
        estado.setColumnaActual(estadoInicial.getColumnaInicial());
        estado.setPicotazosRestantes(estadoInicial.getEnergiaMaxima());
        estado.setFinalizado(false);
        estado.setGano(false);
        estadoJuegoRepository.save(estado);
    }
}
