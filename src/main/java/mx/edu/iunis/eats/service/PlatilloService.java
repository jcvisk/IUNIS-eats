package mx.edu.iunis.eats.service;

import mx.edu.iunis.eats.domain.Platillo;
import mx.edu.iunis.eats.repository.PlatilloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlatilloService {

    @Autowired
    private PlatilloRepository platilloRepository;

    public List<Platillo> obtenerTodos() {
        return platilloRepository.findAll();
    }

    public List<Platillo> obtenerDisponibles() {
        return platilloRepository.findByDisponibilidadTrue();
    }

    public Optional<Platillo> obtenerPorId(Long id) {
        return platilloRepository.findById(id);
    }

    public Platillo guardar(Platillo platillo) {
        platillo.setFechaActualizacion(LocalDateTime.now());
        return platilloRepository.save(platillo);
    }

    public void eliminar(Long id) {
        platilloRepository.deleteById(id);
    }

    public void cambiarDisponibilidad(Long id, boolean disponible) {
        platilloRepository.findById(id).ifPresent(p -> {
            p.setDisponibilidad(disponible);
            p.setFechaActualizacion(LocalDateTime.now());
            platilloRepository.save(p);
        });
    }
}