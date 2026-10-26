package mx.edu.iunis.eats.domain;

import java.math.BigDecimal;

public class CarritoItem {
    private Long idPlatillo;
    private String nombre;
    private BigDecimal precio;
    private Integer cantidad;

    public CarritoItem(Long idPlatillo, String nombre, BigDecimal precio, Integer cantidad) {
        this.idPlatillo = idPlatillo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Long getIdPlatillo() { return idPlatillo; }
    public void setIdPlatillo(Long idPlatillo) { this.idPlatillo = idPlatillo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getSubtotal() {
        return precio.multiply(new BigDecimal(cantidad));
    }
}