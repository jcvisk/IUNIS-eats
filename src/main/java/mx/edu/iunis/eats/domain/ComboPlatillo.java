
package mx.edu.iunis.eats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "combo_platillo")
@IdClass(ComboPlatillo.class)
public class ComboPlatillo {

    @Id
    @Column(name = "id_combo")
    private Long idCombo;

    @Id
    @Column(name = "id_platillo")
    private Long idPlatillo;

    @Column(name = "cantidad")
    private Integer cantidad;

}
