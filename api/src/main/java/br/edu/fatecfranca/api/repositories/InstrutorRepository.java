package br.edu.fatecfranca.api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.fatecfranca.api.models.Instrutor;

public interface InstrutorRepository
        extends MongoRepository<Instrutor, String> {
}