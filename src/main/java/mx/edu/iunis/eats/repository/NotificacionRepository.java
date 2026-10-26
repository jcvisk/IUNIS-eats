package mx.edu.iunis.eats.repository;

import mx.edu.iunis.eats.domain.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    // Buscar notificaciones de un usuario ordenadas por la más reciente
    List<Notificacion> findByIdUsuarioOrderByFechaHoraDesc(Long idUsuario);
}