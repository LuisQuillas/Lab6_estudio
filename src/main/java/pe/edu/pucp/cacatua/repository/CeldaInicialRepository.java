package pe.edu.pucp.cacatua.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.pucp.cacatua.model.CeldaInicial;

import java.util.List;

public interface CeldaInicialRepository extends JpaRepository<CeldaInicial, Integer> {

    List<CeldaInicial> findAllByOrderByFilaAscColumnaAsc();
}
