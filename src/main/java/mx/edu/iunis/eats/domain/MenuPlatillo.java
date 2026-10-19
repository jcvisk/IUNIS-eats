
package mx.edu.iunis.eats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_platillo")
@IdClass(MenuPlatillo.class)
public class MenuPlatillo {

    @Id
    @Column(name = "id_menu")
    private Long idMenu;

    @Id
    @Column(name = "id_platillo")
    private Long idPlatillo;

    @Column(name = "precio_publicado")
    private BigDecimal precioPublicado;

    @Column(name = "disponibilidad")
    private Boolean disponibilidad;

}
