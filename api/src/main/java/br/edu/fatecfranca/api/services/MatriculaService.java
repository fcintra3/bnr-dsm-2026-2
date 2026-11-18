package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.fatecfranca.api.dtos.MatriculaResponse;
import br.edu.fatecfranca.api.models.Aluno;
import br.edu.fatecfranca.api.models.Matricula;
import br.edu.fatecfranca.api.models.Turma;
import br.edu.fatecfranca.api.repositories.AlunoRepository;
import br.edu.fatecfranca.api.repositories.MatriculaRepository;
import br.edu.fatecfranca.api.repositories.TurmaRepository;

@Service
public class MatriculaService {

    private final MatriculaRepository repository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;

    public MatriculaService(
            MatriculaRepository repository,
            AlunoRepository alunoRepository,
            TurmaRepository turmaRepository) {
        this.repository = repository;
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
    }

    private boolean vazio(String valor) {
        return valor == null || valor.isBlank();
    }

    private MatriculaResponse montarResposta(Matricula matricula) {
        Aluno aluno = vazio(matricula.getAlunoId())
                ? null
                : alunoRepository.findById(matricula.getAlunoId()).orElse(null);

        Turma turma = vazio(matricula.getTurmaId())
                ? null
                : turmaRepository.findById(matricula.getTurmaId()).orElse(null);

        return new MatriculaResponse(
                matricula.getId(),
                matricula.getAlunoId(),
                matricula.getTurmaId(),
                matricula.getData(),
                matricula.getSituacao(),
                matricula.getNotas(),
                aluno,
                turma);
    }

    public List<MatriculaResponse> listarComDetalhes() {
        return repository.findAll().stream()
                .map(this::montarResposta)
                .toList();
    }

    public List<MatriculaResponse> listarPorAluno(String alunoId) {
        return repository.findByAlunoId(alunoId).stream()
                .map(this::montarResposta)
                .toList();
    }

    public List<MatriculaResponse> listarPorCurso(String cursoId) {
        List<Turma> turmas = turmaRepository.findByCursoId(cursoId);

        List<String> turmaIds = turmas.stream()
                .map(turma -> turma.getId())
                .toList();

        if (turmaIds.isEmpty()) {
            return List.of();
        }

        return repository.findByTurmaIdIn(turmaIds).stream()
                .map(this::montarResposta)
                .toList();
    }

    public Optional<MatriculaResponse> buscarComDetalhes(String id) {
        return repository.findById(id)
                .map(this::montarResposta);
    }

    public List<Matricula> listarTodos() {
        return repository.findAll();
    }

    public Optional<Matricula> buscarPorId(String id) {
        return repository.findById(id);
    }

    public Matricula salvar(Matricula matricula) {
        return repository.save(matricula);
    }

    public void excluir(String id) {
        repository.deleteById(id);
    }
}