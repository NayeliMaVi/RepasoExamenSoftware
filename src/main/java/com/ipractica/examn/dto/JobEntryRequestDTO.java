package com.ipractica.examn.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
public class JobEntryRequestDTO {
    private Integer id;


    @NotBlank
    @Size(min = 3, max = 50)
    private String nombre;

    @NotBlank
    @Size(min = 3, max = 50)
    private String apPaterno;

    @NotBlank
    @Size(min = 3, max = 50)
    private String positionWorker;

    @NotNull
    private LocalTime horaInicio;


    private LocalTime horaFin;

    private BigDecimal total;

}
