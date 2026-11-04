package br.edu.fatecfranca.api.dtos;

import java.time.LocalDate;
import java.util.List;

import br.edu.fatecfranca.api.models.Curso;
import br.edu.fatecfranca.api.models.Horario;
import br.edu.fatecfranca.api.models.Instrutor;

public record TurmaResponse(
        String id,
        String cursoId,
        String instrutorId,
        LocalDate inicio,
        LocalDate fim,
        Integer vagas,
        String status,
        List<Horario> horarios,
        Curso curso,
        Instrutor instrutor) {
}