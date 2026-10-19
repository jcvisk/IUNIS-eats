
package mx.edu.iunis.eats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion")
public class Notificacion {

    @Id
    @Column(name = "id_notificacion")
    private Long id;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "leida")
    private Boolean leida;

}
