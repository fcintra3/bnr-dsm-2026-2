package br.edu.fatecfranca.api.models;

import java.util.List;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cursos")
public record Curso(
        @Id String id,
        @NotBlank String nome,
        String descricao,
        @Positive Integer cargaHoraria,
        List<Modulo> modulos) {
}
