package mx.edu.iunis.eats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "des", unique = true)
    private String des;
}