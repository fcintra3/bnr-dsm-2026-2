package br.edu.fatecfranca.api.models;

import java.util.List;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "instrutores")
public record Instrutor(
        @Id String id,
        @NotBlank String nome,
        @CPF String cpf,
        String telefone,
        @Email String email,
        List<String> especialidades,
        List<Endereco> enderecos) {
}
