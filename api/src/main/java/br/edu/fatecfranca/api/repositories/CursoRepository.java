package br.edu.fatecfranca.api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.fatecfranca.api.models.Curso;

public interface CursoRepository
        extends MongoRepository<Curso, String> {
}
