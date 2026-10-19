package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.fatecfranca.api.models.Curso;
import br.edu.fatecfranca.api.repositories.CursoRepository;

@Service
public class CursoService {

    private final CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
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
        repository.deleteById(id);
    }
}