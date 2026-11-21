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

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final CursoRepository cursoRepository;

    public MatriculaService(
        MatriculaRepository matriculaRepository,
        AlunoRepository alunoRepository,
        TurmaRepository turmaRepository,
        CursoRepository cursoRepository
    ) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
        this.cursoRepository = cursoRepository;
    }

    private MatriculaResponse converterParaResponse(Matricula matricula) {
        Aluno aluno = (matricula.getAlunoId() == null || matricula.getAlunoId().isBlank())
            ? null
            : alunoRepository.findById(matricula.getAlunoId()).orElse(null);

        Turma turma = (matricula.getTurmaId() == null || matricula.getTurmaId().isBlank())
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
            turma
        );
    }

    private void validarAluno(String alunoId) {
        if (alunoId == null || alunoId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o alunoId.");
        }
        if (!alunoRepository.existsById(alunoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno inexistente.");
        }
    }

    private void validarTurma(String turmaId) {
        if (turmaId == null || turmaId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o turmaId.");
        }
        if (!turmaRepository.existsById(turmaId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma inexistente.");
        }
    }

    public List<MatriculaResponse> listarPorCursoComDetalhes(String cursoId) {
        if (cursoId == null || cursoId.isBlank()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O identificador do curso é obrigatório."
            );
        }
        if (!cursoRepository.existsById(cursoId)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Curso não encontrado."
            );
        }

        List<String> turmaIds = turmaRepository.findByCursoId(cursoId)
            .stream()
            .map(Turma::getId)
            .toList();

        return matriculaRepository.findByTurmaIdIn(turmaIds)
            .stream()
            .map(this::converterParaResponse)
            .toList();
    }

    public List<Matricula> listarTodos() {
        return matriculaRepository.findAll();
    }

    public List<MatriculaResponse> listarComDetalhes() {
        return matriculaRepository.findAll().stream()
            .map(this::converterParaResponse)
            .toList();
    }

    public Optional<Matricula> buscarPorId(String id) {
        return matriculaRepository.findById(id);
    }

    public Optional<MatriculaResponse> buscarComDetalhes(String id) {
        return matriculaRepository.findById(id).map(this::converterParaResponse);
    }

    public List<Matricula> listarPorAluno(String alunoId) {
        validarAluno(alunoId);
        return matriculaRepository.findByAlunoId(alunoId);
    }

    public List<MatriculaResponse> listarPorAlunoComDetalhes(String alunoId) {
        return listarPorAluno(alunoId).stream()
            .map(this::converterParaResponse)
            .toList();
    }

    public List<Matricula> listarPorTurma(String turmaId) {
        validarTurma(turmaId);
        return matriculaRepository.findByTurmaId(turmaId);
    }

    public List<MatriculaResponse> listarPorTurmaComDetalhes(String turmaId) {
        return listarPorTurma(turmaId).stream()
            .map(this::converterParaResponse)
            .toList();
    }

    public Matricula salvar(Matricula matricula) {
        validarAluno(matricula.getAlunoId());
        validarTurma(matricula.getTurmaId());
        return matriculaRepository.save(matricula);
    }

    public void excluir(String id) {
        matriculaRepository.deleteById(id);
    }
}