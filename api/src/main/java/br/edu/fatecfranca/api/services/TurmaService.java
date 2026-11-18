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

    private final TurmaRepository repository;
    private final CursoRepository cursoRepository;
    private final InstrutorRepository instrutorRepository;
    private final MatriculaRepository matriculaRepository;

    public TurmaService(
            TurmaRepository repository,
            CursoRepository cursoRepository,
            InstrutorRepository instrutorRepository,
            MatriculaRepository matriculaRepository) {
        this.repository = repository;
        this.cursoRepository = cursoRepository;
        this.instrutorRepository = instrutorRepository;
        this.matriculaRepository = matriculaRepository;
    }

    private boolean vazio(String valor) {
        return valor == null || valor.isBlank();
    }

    // Monta a resposta do GET de /turmas incluindo curso e instrutor
    private TurmaResponse montarResposta(Turma turma) {
        Curso curso = vazio(turma.getCursoId())
                ? null
                : cursoRepository.findById(turma.getCursoId()).orElse(null);

        Instrutor instrutor = vazio(turma.getInstrutorId())
                ? null
                : instrutorRepository.findById(turma.getInstrutorId()).orElse(null);

        return new TurmaResponse(
                turma.getId(),
                turma.getCursoId(),
                turma.getInstrutorId(),
                turma.getInicio(),
                turma.getFim(),
                turma.getVagas(),
                turma.getStatus(),
                turma.getHorarios(),
                curso,
                instrutor);
    }

    public List<Turma> listarTodos() {
        return repository.findAll();
    }

    public Optional<Turma> buscarPorId(String id) {
        return repository.findById(id);
    }

    public List<Turma> listarPorCurso(String cursoId) {
        return repository.findByCursoId(cursoId);
    }

    public List<TurmaResponse> listarComDetalhes() {
        return repository.findAll().stream()
                .map(this::montarResposta)
                .toList();
    }

    public Optional<TurmaResponse> buscarComDetalhes(String id) {
        return repository.findById(id)
                .map(this::montarResposta);
    }

    public List<TurmaResponse> listarPorCursoComDetalhes(String cursoId) {
        return listarPorCurso(cursoId).stream()
                .map(this::montarResposta)
                .toList();
    }

    public Turma salvar(Turma turma) {
        return repository.save(turma);
    }

    public void excluir(String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Turma não encontrada.");
        }

        if (matriculaRepository.existsByTurmaId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir uma turma com matrículas vinculadas.");
        }

        repository.deleteById(id);
    }
}