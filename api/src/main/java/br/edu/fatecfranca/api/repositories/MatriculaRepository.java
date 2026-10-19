package br.edu.fatecfranca.api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.fatecfranca.api.models.Matricula;

public interface MatriculaRepository
        extends MongoRepository<Matricula, String> {
}
