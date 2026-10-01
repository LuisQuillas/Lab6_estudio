package pe.edu.pucp.cacatua.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.pucp.cacatua.model.EstadoJuego;

public interface EstadoJuegoRepository extends JpaRepository<EstadoJuego, Integer> {
}
