package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.fatecfranca.api.models.Matricula;
import br.edu.fatecfranca.api.repositories.MatriculaRepository;

@Service
public class MatriculaService {

    private final MatriculaRepository repository;

    public MatriculaService(MatriculaRepository repository) {
        this.repository = repository;
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
