package br.edu.fatecfranca.api.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.fatecfranca.api.models.Curso;

public interface CursoRepository
        extends MongoRepository<Curso, String> {

    List<Curso> findByNomeContainingIgnoreCase(String nome);
}