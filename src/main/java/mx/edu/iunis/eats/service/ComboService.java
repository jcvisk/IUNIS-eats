package mx.edu.iunis.eats.service;

import mx.edu.iunis.eats.domain.Combo;
import mx.edu.iunis.eats.domain.ComboPlatillo;

import java.util.List;
import java.util.Optional;

public interface ComboService {

    List<Combo> listarTodos();

    Optional<Combo> obtenerPorId(Long id);

    Combo guardar(Combo combo, List<ComboPlatillo> componentes);

    void eliminar(Long id);

    List<ComboPlatillo> obtenerComponentes(Long idCombo);
}