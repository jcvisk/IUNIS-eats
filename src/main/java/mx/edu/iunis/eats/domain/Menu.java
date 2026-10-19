
package mx.edu.iunis.eats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @Column(name = "id_menu")
    private Long id;

    @Column(name = "id_periodo")
    private Long idPeriodo;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;

    @Column(name = "estado_publicacion")
    private String estadoPublicacion;

}
