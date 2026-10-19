
package mx.edu.iunis.eats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_pedido")
public class HistorialPedido {

    @Id
    @Column(name = "id_historial")
    private Long id;

    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "estado_anterior")
    private String estadoAnterior;

    @Column(name = "estado_nuevo")
    private String estadoNuevo;

    @Column(name = "tipo_accion")
    private String tipoAccion;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "motivo")
    private String motivo;

}
