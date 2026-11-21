package br.edu.fatecfranca.api.models;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public record Nota(
        @NotBlank String avaliacao,
        @NotNull @DecimalMin("0") @DecimalMax("10") BigDecimal valor) {
}
