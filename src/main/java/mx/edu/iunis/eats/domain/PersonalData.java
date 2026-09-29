package mx.edu.iunis.eats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "personal_data")
public class PersonalData implements Serializable {
    @Id
    private Long id;

    @Column(name = "names")
    private String names;

    @Column(name ="last_names")
    private String lastNames;

    @Column(name ="age")
    private Integer age;

    @Column(name ="gender")
    private String gender;

    @Column(name ="user_id")
    private Long userId;
}
