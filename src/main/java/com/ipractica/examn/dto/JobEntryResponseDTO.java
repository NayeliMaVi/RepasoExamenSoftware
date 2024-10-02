package com.ipractica.examn.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
public class JobEntryResponseDTO {
    private Integer id;
    private String nombre;
    private String apPaterno;
    private LocalTime horaInicio;


}
