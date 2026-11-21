package br.edu.fatecfranca.api.models;

import java.time.LocalTime;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

public record Horario(
        @NotBlank String diaSemana,
        @NotNull LocalTime inicio,
        @NotNull LocalTime fim) {
    @JsonIgnore
    @AssertTrue(message = "O fim não pode ser anterior ao início.")
    public boolean isPeriodoValido() {
        return inicio == null || fim == null || !fim.isBefore(inicio);
    }

}
