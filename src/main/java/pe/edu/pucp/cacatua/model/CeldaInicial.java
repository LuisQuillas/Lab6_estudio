package pe.edu.pucp.cacatua.model;

import jakarta.persistence.*;

/**
 * Copia de respaldo del tablero en su configuracion ORIGINAL (foto inicial).
 * Se usa exclusivamente para restaurar el juego con el boton "Reiniciar".
 */
@Entity
@Table(name = "tablero_inicial")
public class CeldaInicial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int fila;
    private int columna;
    private int nivel;

    @Column(name = "es_semilla")
    private boolean esSemilla;

    public CeldaInicial() {
    }

    public CeldaInicial(int fila, int columna, int nivel, boolean esSemilla) {
        this.fila = fila;
        this.columna = columna;
        this.nivel = nivel;
        this.esSemilla = esSemilla;
    }

    public Integer getId() {
        return id;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public int getNivel() {
        return nivel;
    }

    public boolean isEsSemilla() {
        return esSemilla;
    }
}
