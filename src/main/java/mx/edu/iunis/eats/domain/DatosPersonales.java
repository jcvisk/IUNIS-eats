
package mx.edu.iunis.eats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "datos_personales")
public class DatosPersonales {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "genero")
    private String genero;

    @Column(name = "foto")
    private String foto;

    @Column(name = "user_id")
    private Long userId;

}
