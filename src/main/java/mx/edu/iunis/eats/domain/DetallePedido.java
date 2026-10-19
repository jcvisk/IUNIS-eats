
package mx.edu.iunis.eats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @Column(name = "id_detalle")
    private Long id;

    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "id_platillo")
    private Long idPlatillo;

    @Column(name = "id_combo")
    private Long idCombo;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_aplicado")
    private BigDecimal precioAplicado;

}
