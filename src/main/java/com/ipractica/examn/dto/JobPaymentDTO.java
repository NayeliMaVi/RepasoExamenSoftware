package com.ipractica.examn.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class JobPaymentDTO {
    private Integer id;
    private BigDecimal total;
    private String nombre;
    private String horasTrabajadas;
}
