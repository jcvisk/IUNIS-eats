package mx.edu.iunis.eats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles_user")
@IdClass(RolesUser.class)
public class RolesUser {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "role_id")
    private Long roleId;
}
