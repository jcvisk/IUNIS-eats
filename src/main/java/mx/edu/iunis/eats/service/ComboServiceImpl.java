package mx.edu.iunis.eats.service;

import mx.edu.iunis.eats.domain.Combo;
import mx.edu.iunis.eats.domain.ComboPlatillo;
import mx.edu.iunis.eats.domain.Platillo;
import mx.edu.iunis.eats.repository.ComboRepository;
import mx.edu.iunis.eats.repository.ComboPlatilloRepository;
import mx.edu.iunis.eats.service.PlatilloService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ComboServiceImpl implements ComboService {

    private final ComboRepository comboRepository;
    private final ComboPlatilloRepository comboPlatilloRepository;
    private final PlatilloService platilloService;

    public ComboServiceImpl(
            ComboRepository comboRepository,
            ComboPlatilloRepository comboPlatilloRepository,
            PlatilloService platilloService) {

        this.comboRepository = comboRepository;
        this.comboPlatilloRepository = comboPlatilloRepository;
        this.platilloService = platilloService;
    }

    @Override
    public List<Combo> listarTodos() {
        return comboRepository.findAll();
    }

    @Override
    public Optional<Combo> obtenerPorId(Long id) {
        return comboRepository.findById(id);
    }

    @Override
    public List<ComboPlatillo> obtenerComponentes(Long idCombo) {
        return comboPlatilloRepository.findByIdCombo(idCombo);
    }

    @Override
    @Transactional
    public Combo guardar(Combo combo, List<ComboPlatillo> componentes) {

        // Validar nombre
        if (combo.getNombre() == null ||
                combo.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del combo es obligatorio.");
        }

        // Validar precio
        if (combo.getPrecio() == null ||
                combo.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "El precio debe ser mayor que cero.");
        }

        // Validar componentes
        if (componentes == null || componentes.isEmpty()) {
            throw new IllegalArgumentException(
                    "El combo debe contener al menos un platillo.");
        }

        Set<Long> idsPlatillos = new HashSet<>();

        for (ComboPlatillo componente : componentes) {

            if (componente.getIdPlatillo() == null) {
                throw new IllegalArgumentException(
                        "Debes seleccionar un platillo.");
            }

            if (componente.getCantidad() == null ||
                    componente.getCantidad() <= 0) {
                throw new IllegalArgumentException(
                        "La cantidad debe ser mayor que cero.");
            }

            // Evitar platillos repetidos
            if (!idsPlatillos.add(componente.getIdPlatillo())) {
                throw new IllegalArgumentException(
                        "No puedes repetir un platillo en el mismo combo.");
            }

            // Comprobar que el platillo exista
            Optional<Platillo> platillo =
                    platilloService.obtenerPorId(
                            componente.getIdPlatillo());

            if (platillo.isEmpty()) {
                throw new IllegalArgumentException(
                        "Uno de los platillos seleccionados no existe.");
            }
        }

        // Evitar disponibilidad nula
        if (combo.getDisponibilidad() == null) {
            combo.setDisponibilidad(true);
        }

        // Guardar datos generales del combo
        Combo comboGuardado = comboRepository.save(combo);

        // Eliminar componentes anteriores al actualizar
        List<ComboPlatillo> anteriores =
                comboPlatilloRepository.findByIdCombo(comboGuardado.getId());

        comboPlatilloRepository.deleteAll(anteriores);
        comboPlatilloRepository.flush();

        // Guardar la nueva composición
        for (ComboPlatillo componente : componentes) {
            componente.setIdCombo(comboGuardado.getId());
            comboPlatilloRepository.save(componente);
        }

        return comboGuardado;
    }

    @Override
    @Transactional
    public void eliminar(Long id) {

        Combo combo = comboRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El combo no existe."));

        // Desactivar en lugar de borrar para conservar el historial
        combo.setDisponibilidad(false);
        comboRepository.save(combo);
    }
}