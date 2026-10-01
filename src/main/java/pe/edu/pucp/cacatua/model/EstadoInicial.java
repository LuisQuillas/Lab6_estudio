package pe.edu.pucp.cacatua.model;

import jakarta.persistence.*;

/**
 * Respaldo del estado inicial de la partida (posicion de partida del ave
 * y energia maxima), usado por el boton "Reiniciar".
 */
@Entity
@Table(name = "estado_inicial")
public class EstadoInicial {

    @Id
    private Integer id = 1;

    @Column(name = "fila_inicial")
    private int filaInicial;

    @Column(name = "columna_inicial")
    private int columnaInicial;

    @Column(name = "energia_maxima")
    private int energiaMaxima;

    public EstadoInicial() {
    }

    public EstadoInicial(int filaInicial, int columnaInicial, int energiaMaxima) {
        this.id = 1;
        this.filaInicial = filaInicial;
        this.columnaInicial = columnaInicial;
        this.energiaMaxima = energiaMaxima;
    }

    public Integer getId() {
        return id;
    }

    public int getFilaInicial() {
        return filaInicial;
    }

    public int getColumnaInicial() {
        return columnaInicial;
    }

    public int getEnergiaMaxima() {
        return energiaMaxima;
    }
}
