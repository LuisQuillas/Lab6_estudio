package pe.edu.pucp.cacatua.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.edu.pucp.cacatua.service.JuegoService;

/**
 * Unica ruta (/cacatua_sulfurea) que procesa la accion del usuario y vuelve
 * a renderizar la misma vista (compuesta por los fragmentos tablero + panel),
 * cumpliendo el requisito de "ruta unica de actualizacion".
 */
@Controller
public class CacatuaController {

    @Autowired
    private JuegoService juegoService;

    @GetMapping("/cacatua_sulfurea")
    public String mostrarJuego(Model model) {
        cargarModelo(model);
        return "index";
    }

    @PostMapping("/cacatua_sulfurea")
    public String procesarAccion(@RequestParam("direccion") String direccion, Model model) {
        JuegoService.Direccion dir = JuegoService.Direccion.valueOf(direccion.toUpperCase());
        juegoService.procesarMovimiento(dir);
        cargarModelo(model);
        return "index";
    }

    @PostMapping("/cacatua_sulfurea/reiniciar")
    public String reiniciar(Model model) {
        juegoService.reiniciar();
        cargarModelo(model);
        return "index";
    }

    private void cargarModelo(Model model) {
        model.addAttribute("filas", juegoService.obtenerFilasDto());
        model.addAttribute("estado", juegoService.obtenerEstadoDto());
    }
}
