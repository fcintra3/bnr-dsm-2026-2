package br.edu.fatecfranca.api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.fatecfranca.api.models.Turma;

public interface TurmaRepository
       extends MongoRepository<Turma, String> {
}