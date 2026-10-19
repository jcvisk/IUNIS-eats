
package mx.edu.iunis.eats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_combo")
@IdClass(MenuCombo.class)
public class MenuCombo {

    @Id
    @Column(name = "id_menu")
    private Long idMenu;

    @Id
    @Column(name = "id_combo")
    private Long idCombo;

    @Column(name = "precio_publicado")
    private BigDecimal precioPublicado;

    @Column(name = "disponibilidad")
    private Boolean disponibilidad;

}
