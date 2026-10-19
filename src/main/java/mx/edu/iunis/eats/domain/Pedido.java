package mx.edu.iunis.eats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @Column(name = "id_pedido")
    private Long id;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_periodo")
    private Long idPeriodo;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "estado")
    private String estado;
}