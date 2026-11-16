package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.dtos.MatriculaResponse;
import br.edu.fatecfranca.api.models.Aluno;
import br.edu.fatecfranca.api.models.Matricula;
import br.edu.fatecfranca.api.models.Turma;
import br.edu.fatecfranca.api.repositories.AlunoRepository;
import br.edu.fatecfranca.api.repositories.CursoRepository;
import br.edu.fatecfranca.api.repositories.MatriculaRepository;
import br.edu.fatecfranca.api.repositories.TurmaRepository;

@Service
public class MatriculaService {

    private final MatriculaRepository repository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final CursoRepository cursoRepository;

    public MatriculaService(
            MatriculaRepository repository,
            AlunoRepository alunoRepository,
            TurmaRepository turmaRepository,
            CursoRepository cursoRepository) {
        this.repository = repository;
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
        this.cursoRepository = cursoRepository;
    }

    private void verificarAluno(String alunoId) {
        if (alunoId == null || alunoId.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador do aluno é obrigatório.");
        }

        if (!alunoRepository.existsById(alunoId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Aluno não encontrado.");
        }
    }

    private void verificarTurma(String turmaId) {
        if (turmaId == null || turmaId.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O identificador da turma é obrigatório.");
        }

        if (!turmaRepository.existsById(turmaId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Turma não encontrada.");
        }
    }

    private void verificarCurso(String cursoId) {
        if (!cursoRepository.existsById(cursoId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Curso não encontrado.");
        }
    }

    // Monta a resposta do GET de /matriculas
    // incluindo os dados completos de aluno e turma
    private MatriculaResponse montarResposta(Matricula matricula) {
        Aluno aluno = alunoRepository.findById(matricula.getAlunoId())
                .orElse(null);

        Turma turma = turmaRepository.findById(matricula.getTurmaId())
                .orElse(null);

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

    public Optional<MatriculaResponse> buscarComDetalhes(String id) {
        return repository.findById(id)
                .map(this::montarResposta);
    }

    public List<MatriculaResponse> listarPorAlunoComDetalhes(String alunoId) {
        return listarPorAluno(alunoId).stream()
                .map(this::montarResposta)
                .toList();
    }

    public List<MatriculaResponse> listarPorTurmaComDetalhes(String turmaId) {
        return listarPorTurma(turmaId).stream()
                .map(this::montarResposta)
                .toList();
    }

    // Matrículas de um curso: busca as turmas do curso
    // e depois as matrículas dessas turmas
    public List<MatriculaResponse> listarPorCursoComDetalhes(String cursoId) {
        verificarCurso(cursoId);

        List<String> turmaIds = turmaRepository.findByCursoId(cursoId).stream()
                .map(Turma::getId)
                .toList();

        return repository.findByTurmaIdIn(turmaIds).stream()
                .map(this::montarResposta)
                .toList();
    }

    public List<Matricula> listarTodos() {
        return repository.findAll();
    }

    public Optional<Matricula> buscarPorId(String id) {
        return repository.findById(id);
    }

    public List<Matricula> listarPorAluno(String alunoId) {
        verificarAluno(alunoId);
        return repository.findByAlunoId(alunoId);
    }

    public List<Matricula> listarPorTurma(String turmaId) {
        verificarTurma(turmaId);
        return repository.findByTurmaId(turmaId);
    }

    public Matricula salvar(Matricula matricula) {
        verificarAluno(matricula.getAlunoId());
        verificarTurma(matricula.getTurmaId());
        return repository.save(matricula);
    }

    public void excluir(String id) {
        repository.deleteById(id);
    }
}