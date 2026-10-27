package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.dtos.MatriculaResponse;
import br.edu.fatecfranca.api.dtos.TurmaResponse;
import br.edu.fatecfranca.api.models.Aluno;
import br.edu.fatecfranca.api.models.Matricula;
import br.edu.fatecfranca.api.repositories.AlunoRepository;
import br.edu.fatecfranca.api.repositories.MatriculaRepository;
import br.edu.fatecfranca.api.repositories.TurmaRepository;

@Service
public class MatriculaService {

    private final MatriculaRepository repository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final TurmaService turmaService;

    public MatriculaService(
            MatriculaRepository repository,
            AlunoRepository alunoRepository,
            TurmaRepository turmaRepository,
            TurmaService turmaService) {
        this.repository = repository;
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
        this.turmaService = turmaService;
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

    private MatriculaResponse montarResposta(Matricula matricula) {
        Aluno aluno = null;
        if (matricula.getAlunoId() != null && !matricula.getAlunoId().isBlank()) {
            aluno = alunoRepository.findById(matricula.getAlunoId()).orElse(null);
        }

        TurmaResponse turma = null;
        if (matricula.getTurmaId() != null && !matricula.getTurmaId().isBlank()) {
            turma = turmaService.buscarComDetalhes(matricula.getTurmaId()).orElse(null);
        }

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
        return repository.findById(id).map(this::montarResposta);
    }

    public List<MatriculaResponse> listarPorAlunoComDetalhes(String alunoId) {
        verificarAluno(alunoId);
        return repository.findByAlunoId(alunoId).stream()
                .map(this::montarResposta)
                .toList();
    }

    public List<MatriculaResponse> listarPorTurmaComDetalhes(String turmaId) {
        verificarTurma(turmaId);
        return repository.findByTurmaId(turmaId).stream()
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
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Matrícula não encontrada.");
        }

        repository.deleteById(id);
    }
}
