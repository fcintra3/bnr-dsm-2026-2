package br.edu.fatecfranca.api.dtos;

import java.util.List;
import br.edu.fatecfranca.api.models.Aluno;
import br.edu.fatecfranca.api.models.Nota;

public record MatriculaResponse(
        String id,
        String alunoId,
        String turmaId,
        String dataMatricula,
        String situacao,
        List<Nota> notas,
        Aluno aluno,
        TurmaResponse turma) {
}