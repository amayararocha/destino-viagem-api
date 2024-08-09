package com.destinoviagem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DestinoDTO {
    private Long id;
    private String nome;
    private String pais;
}
