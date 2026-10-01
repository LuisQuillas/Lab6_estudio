package pe.edu.pucp.cacatua.model;

import jakarta.persistence.*;

/**
 * Guarda el estado ACTUAL de la partida (posicion del ave, energia restante,
 * si ya termino y si gano). Siempre existe una unica fila (id = 1) porque
 * el juego es de un solo jugador / una sola partida activa.
 */
@Entity
@Table(name = "estado_juego")
public class EstadoJuego {

    @Id
    private Integer id = 1;

    @Column(name = "fila_actual")
    private int filaActual;

    @Column(name = "columna_actual")
    private int columnaActual;

    @Column(name = "picotazos_restantes")
    private int picotazosRestantes;

    private boolean finalizado;
    private boolean gano;

    public EstadoJuego() {
    }

    public EstadoJuego(int filaActual, int columnaActual, int picotazosRestantes) {
        this.id = 1;
        this.filaActual = filaActual;
        this.columnaActual = columnaActual;
        this.picotazosRestantes = picotazosRestantes;
        this.finalizado = false;
        this.gano = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFilaActual() {
        return filaActual;
    }

    public void setFilaActual(int filaActual) {
        this.filaActual = filaActual;
    }

    public int getColumnaActual() {
        return columnaActual;
    }

    public void setColumnaActual(int columnaActual) {
        this.columnaActual = columnaActual;
    }

    public int getPicotazosRestantes() {
        return picotazosRestantes;
    }

    public void setPicotazosRestantes(int picotazosRestantes) {
        this.picotazosRestantes = picotazosRestantes;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public boolean isGano() {
        return gano;
    }

    public void setGano(boolean gano) {
        this.gano = gano;
    }
}
