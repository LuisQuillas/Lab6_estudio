package pe.edu.pucp.cacatua.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.pucp.cacatua.model.Celda;

import java.util.List;
import java.util.Optional;

public interface CeldaRepository extends JpaRepository<Celda, Integer> {

    List<Celda> findAllByOrderByFilaAscColumnaAsc();

    Optional<Celda> findByFilaAndColumna(int fila, int columna);
}
