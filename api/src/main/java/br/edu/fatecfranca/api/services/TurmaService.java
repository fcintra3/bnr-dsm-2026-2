package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import br.edu.fatecfranca.api.dtos.TurmaResponse;
import br.edu.fatecfranca.api.models.Curso;
import br.edu.fatecfranca.api.models.Instrutor;
import br.edu.fatecfranca.api.models.Turma;
import br.edu.fatecfranca.api.repositories.CursoRepository;
import br.edu.fatecfranca.api.repositories.InstrutorRepository;
import br.edu.fatecfranca.api.repositories.MatriculaRepository;
import br.edu.fatecfranca.api.repositories.TurmaRepository;

@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final CursoRepository cursoRepository;
    private final InstrutorRepository instrutorRepository;
    private final MatriculaRepository matriculaRepository;

    public TurmaService(
        TurmaRepository turmaRepository,
        CursoRepository cursoRepository,
        InstrutorRepository instrutorRepository,
        MatriculaRepository matriculaRepository
    ) {
        this.turmaRepository = turmaRepository;
        this.cursoRepository = cursoRepository;
        this.instrutorRepository = instrutorRepository;
        this.matriculaRepository = matriculaRepository;
    }

    private TurmaResponse converterParaResponse(Turma turma) {
        Curso curso = (turma.getCursoId() == null || turma.getCursoId().isBlank())
            ? null
            : cursoRepository.findById(turma.getCursoId()).orElse(null);

        Instrutor instrutor = (turma.getInstrutorId() == null || turma.getInstrutorId().isBlank())
            ? null
            : instrutorRepository.findById(turma.getInstrutorId()).orElse(null);

        return new TurmaResponse(
            turma.getId(),
            turma.getCursoId(),
            turma.getInstrutorId(),
            turma.getInicio(),
            turma.getFim(),
            turma.getVagas(),
            turma.getHorarios(),
            curso,
            instrutor
        );
    }

    private void validarCurso(String cursoId) {
        if (cursoId == null || cursoId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o cursoId.");
        }
        if (!cursoRepository.existsById(cursoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso inexistente.");
        }
    }

    public List<Turma> listarTodos() {
        return turmaRepository.findAll();
    }

    public List<TurmaResponse> listarComDetalhes() {
        return turmaRepository.findAll().stream()
            .map(this::converterParaResponse)
            .toList();
    }

    public Optional<Turma> buscarPorId(String id) {
        return turmaRepository.findById(id);
    }

    public Optional<TurmaResponse> buscarComDetalhes(String id) {
        return turmaRepository.findById(id).map(this::converterParaResponse);
    }

    public List<Turma> listarPorCurso(String cursoId) {
        validarCurso(cursoId);
        return turmaRepository.findByCursoId(cursoId);
    }

    public List<TurmaResponse> listarPorCursoComDetalhes(String cursoId) {
        return listarPorCurso(cursoId).stream()
            .map(this::converterParaResponse)
            .toList();
    }

    public Turma salvar(Turma turma) {
        validarCurso(turma.getCursoId());
        return turmaRepository.save(turma);
    }

    public void excluir(String id) {
        if (!turmaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma inexistente.");
        }
        if (matriculaRepository.existsByTurmaId(id)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Exclusão bloqueada: existem matrículas associadas a esta turma."
            );
        }
        turmaRepository.deleteById(id);
    }
}