package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.models.Curso;
import br.edu.fatecfranca.api.repositories.CursoRepository;
import br.edu.fatecfranca.api.repositories.TurmaRepository;

@Service
public class CursoService {

    private final CursoRepository repository;
    private final TurmaRepository turmaRepository;

    public CursoService(
            CursoRepository repository,
            TurmaRepository turmaRepository) {
        this.repository = repository;
        this.turmaRepository = turmaRepository;
    }

    public List<Curso> listarTodos() {
        return repository.findAll();
    }

    public Optional<Curso> buscarPorId(String id) {
        return repository.findById(id);
    }

    public Curso salvar(Curso curso) {
        return repository.save(curso);
    }

    public void excluir(String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Curso não encontrado.");
        }

        if (turmaRepository.existsByCursoId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir um curso com turmas vinculadas.");
        }

        repository.deleteById(id);
    }
}
