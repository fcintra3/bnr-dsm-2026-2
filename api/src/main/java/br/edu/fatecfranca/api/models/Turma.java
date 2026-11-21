package br.edu.fatecfranca.api.models;

import java.util.List;
import java.time.LocalDate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "turmas")
public record Turma(
        @Id String id,
        @NotBlank String cursoId,
        @NotBlank String instrutorId,
        @NotNull LocalDate inicio,
        @NotNull LocalDate fim,
        @NotNull @Positive Integer vagas,
        @NotBlank @Pattern(regexp = "PREVISTA|ATIVA|ENCERRADA|CANCELADA") String status,
        List<@Valid Horario> horarios) {
    @JsonIgnore
    @AssertTrue(message = "O fim não pode ser anterior ao início.")
    public boolean isPeriodoValido() {
        return inicio == null || fim == null || !fim.isBefore(inicio);
    }

}
