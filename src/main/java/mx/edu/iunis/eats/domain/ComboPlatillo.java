package mx.edu.iunis.eats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "combo_platillo")
@IdClass(ComboPlatilloId.class)
public class ComboPlatillo {

    @Id
    @Column(name = "id_combo")
    private Long idCombo;

    @Id
    @Column(name = "id_platillo")
    private Long idPlatillo;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    // Constructor vacío
    public ComboPlatillo() {
    }

    // Constructor con parámetros
    public ComboPlatillo(Long idCombo, Long idPlatillo, Integer cantidad) {
        this.idCombo = idCombo;
        this.idPlatillo = idPlatillo;
        this.cantidad = cantidad;
    }

    // Getters y Setters

    public Long getIdCombo() {
        return idCombo;
    }

    public void setIdCombo(Long idCombo) {
        this.idCombo = idCombo;
    }

    public Long getIdPlatillo() {
        return idPlatillo;
    }

    public void setIdPlatillo(Long idPlatillo) {
        this.idPlatillo = idPlatillo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}