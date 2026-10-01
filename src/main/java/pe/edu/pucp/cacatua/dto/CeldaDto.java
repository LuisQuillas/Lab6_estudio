package pe.edu.pucp.cacatua.dto;

/**
 * DTO que transporta a la vista solo lo que el fragmento "celda" necesita
 * para dibujarse: su posicion, su nivel actual de dureza, si el ave esta
 * parada ahi y si es la celda donde esta la semilla.
 *
 * No es una entidad JPA: nunca se guarda tal cual en base de datos, es
 * construido por el service a partir de Celda + EstadoJuego.
 */
public class CeldaDto {

    private final int fila;
    private final int columna;
    private final int nivel;
    private final boolean tieneAve;
    private final boolean esSemilla;

    public CeldaDto(int fila, int columna, int nivel, boolean tieneAve, boolean esSemilla) {
        this.fila = fila;
        this.columna = columna;
        this.nivel = nivel;
        this.tieneAve = tieneAve;
        this.esSemilla = esSemilla;
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

    public boolean isTieneAve() {
        return tieneAve;
    }

    public boolean isEsSemilla() {
        return esSemilla;
    }

    /** Clase CSS a usar en la vista segun el nivel de dureza. */
    public String getClaseCss() {
        if (tieneAve) {
            return "celda celda-ave";
        }
        if (esSemilla && nivel == 0) {
            return "celda celda-semilla";
        }
        return switch (nivel) {
            case 3 -> "celda madera-dura";
            case 2 -> "celda madera-media";
            case 1 -> "celda madera-blanda";
            default -> "celda celda-libre";
        };
    }
}
