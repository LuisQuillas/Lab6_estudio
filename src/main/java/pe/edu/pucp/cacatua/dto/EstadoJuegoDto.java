package pe.edu.pucp.cacatua.dto;

/**
 * DTO para el fragmento "panel": solo lo que el panel lateral necesita
 * mostrar (energia restante y si la partida ya termino, y como termino).
 */
public class EstadoJuegoDto {

    private final int picotazosRestantes;
    private final boolean finalizado;
    private final boolean gano;

    public EstadoJuegoDto(int picotazosRestantes, boolean finalizado, boolean gano) {
        this.picotazosRestantes = picotazosRestantes;
        this.finalizado = finalizado;
        this.gano = gano;
    }

    public int getPicotazosRestantes() {
        return picotazosRestantes;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public boolean isGano() {
        return gano;
    }

    public String getMensaje() {
        if (!finalizado) {
            return null;
        }
        return gano ? "¡EXCELENTE, LA CACATÚA ENCONTRÓ LA SEMILLA!" : "¡LA CACATÚA NO ENCONTRÓ LA SEMILLA!";
    }
}
