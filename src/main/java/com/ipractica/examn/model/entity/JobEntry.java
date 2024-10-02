package com.ipractica.examn.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "job_entries")
public class JobEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "worker_name", nullable = false)
    private String nombre;

    @Column(name = "worker_lastname", nullable = false)
    private String apPaterno;

    @Column(name = "worker_position", nullable = false)
    private String positionWorker;

    @Column(name = "entry_time", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "exit_time")
    private LocalTime horaFin;

    @Column(name = "totalAmount")
    private BigDecimal total;

}
