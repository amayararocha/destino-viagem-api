package com.destinoviagem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViagemDTO {
    private Long id;
    private String titulo;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private Long destinoId;
}
