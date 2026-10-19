package mx.edu.iunis.eats.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "periodo_servicio")
public class PeriodoServicio {

    @Id
    @Column(name = "id_periodo")
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "limite_hora")
    private LocalTime limiteHora;

    @Column(name = "estado_periodo")
    private String estadoPeriodo;
}