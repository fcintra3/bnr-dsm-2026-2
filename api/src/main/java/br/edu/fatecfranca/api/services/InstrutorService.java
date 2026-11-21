package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
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

    public Instrutor criar(Instrutor dados) {
        return repository.save(new Instrutor(null, dados.nome(), dados.cpf(), dados.telefone(), dados.email(), dados.especialidades(), dados.enderecos()));
    }
}
