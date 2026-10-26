package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.models.Instrutor;
import br.edu.fatecfranca.api.repositories.InstrutorRepository;

@Service
public class InstrutorService {

    private final InstrutorRepository repository;

    public InstrutorService(InstrutorRepository repository) {
        this.repository = repository;
    }

    public List<Instrutor> listarTodos() {
        return repository.findAll();
    }

    public Optional<Instrutor> buscarPorId(String id) {
        return repository.findById(id);
    }

    public Instrutor salvar(Instrutor instrutor) {
        return repository.save(instrutor);
    }

    public void excluir(String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Instrutor não encontrado.");
        }
        repository.deleteById(id);
    }
}
