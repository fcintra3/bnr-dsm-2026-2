package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.models.Turma;
import br.edu.fatecfranca.api.repositories.CursoRepository;
import br.edu.fatecfranca.api.repositories.MatriculaRepository;
import br.edu.fatecfranca.api.repositories.TurmaRepository;

@Service
public class TurmaService {

    private final TurmaRepository repository;
    private final CursoRepository cursoRepository;
    private final MatriculaRepository matriculaRepository;

    public TurmaService(TurmaRepository repository, CursoRepository cursoRepository,
            MatriculaRepository matriculaRepository) {
        this.repository = repository;
        this.cursoRepository = cursoRepository;
        this.matriculaRepository = matriculaRepository;
    }

    private void verificarCurso(String cursoId) {
        if (cursoId == null || cursoId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O identificador do curso é obrigatório.");
        }
        if (!cursoRepository.existsById(cursoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado.");
        }
    }

    public List<Turma> listarTodos() {
        return repository.findAll();
    }

    public Optional<Turma> buscarPorId(String id) {
        return repository.findById(id);
    }

    public List<Turma> listarPorCurso(String cursoId) {
        verificarCurso(cursoId);
        return repository.findByCursoId(cursoId);
    }

    public Turma salvar(Turma turma) {
        verificarCurso(turma.getCursoId());
        return repository.save(turma);
    }

    public void excluir(String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada.");
        }
        if (matriculaRepository.existsByTurmaId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Não é possível excluir uma turma com matrículas vinculadas.");
        }
        repository.deleteById(id);
    }
}
