package br.edu.fatecfranca.api.models;

import jakarta.validation.constraints.*;

public record Modulo(
        @NotBlank String nome,
        String descricao,
        @Positive Integer cargaHoraria) {
}
