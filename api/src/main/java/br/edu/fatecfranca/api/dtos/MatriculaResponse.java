package br.edu.fatecfranca.api.dtos;

import java.time.LocalDate;
import java.util.List;

import br.edu.fatecfranca.api.models.Aluno;
import br.edu.fatecfranca.api.models.Nota;

public record MatriculaResponse(
        String id,
        String alunoId,
        String turmaId,
        LocalDate data,
        String situacao,
        List<Nota> notas,
        Aluno aluno,
        TurmaResponse turma) {
}
