package pe.edu.pucp.cacatua.model;

import jakarta.persistence.*;

/**
 * Representa una celda del tablero (tronco) en su estado ACTUAL de juego.
 * nivel: 0 = espacio libre / cielo, 1 = madera blanda, 2 = madera media, 3 = madera dura.
 */
@Entity
@Table(name = "tablero")
public class Celda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int fila;
    private int columna;
    private int nivel;

    @Column(name = "es_semilla")
    private boolean esSemilla;

    public Celda() {
    }

    public Celda(int fila, int columna, int nivel, boolean esSemilla) {
        this.fila = fila;
        this.columna = columna;
        this.nivel = nivel;
        this.esSemilla = esSemilla;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public boolean isEsSemilla() {
        return esSemilla;
    }

    public void setEsSemilla(boolean esSemilla) {
        this.esSemilla = esSemilla;
    }
}
