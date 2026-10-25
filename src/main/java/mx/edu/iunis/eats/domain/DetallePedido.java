package mx.edu.iunis.eats.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }

    public Long getIdPlatillo() { return idPlatillo; }
    public void setIdPlatillo(Long idPlatillo) { this.idPlatillo = idPlatillo; }

    public Long getIdCombo() { return idCombo; }
    public void setIdCombo(Long idCombo) { this.idCombo = idCombo; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioAplicado() { return precioAplicado; }
    public void setPrecioAplicado(BigDecimal precioAplicado) { this.precioAplicado = precioAplicado; }
}