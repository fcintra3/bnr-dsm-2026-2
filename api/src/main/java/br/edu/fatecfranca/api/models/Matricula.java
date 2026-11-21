package br.edu.fatecfranca.api.models;

import java.util.List;
import java.time.LocalDate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matriculas")
public record Matricula(
        @Id String id,
        @NotBlank String alunoId,
        @NotBlank String turmaId,
        @NotNull LocalDate data,
        @NotBlank @Pattern(regexp = "(?i)ativa|inativa|conclu[ií]da|cancelada|trancada") String situacao,
        List<@Valid Nota> notas) {
}
