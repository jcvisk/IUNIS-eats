package mx.edu.iunis.eats.repository;

import mx.edu.iunis.eats.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEstadoOrderByFechaHoraAsc(String estado);
    List<Pedido> findAllByOrderByFechaHoraDesc();
    long countByEstado(String estado);

    // ESTE ES EL MÉTODO QUE FALTABA
    List<Pedido> findByIdUsuarioOrderByFechaHoraDesc(Long idUsuario);
}