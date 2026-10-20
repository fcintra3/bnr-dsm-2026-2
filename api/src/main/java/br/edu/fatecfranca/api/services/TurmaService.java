package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.fatecfranca.api.models.Turma;
import br.edu.fatecfranca.api.repositories.TurmaRepository;

@Service
public class TurmaService {

   private final TurmaRepository repository;

   public TurmaService(TurmaRepository repository) {
       this.repository = repository;
   }

   public List<Turma> listarTodos() {
       return repository.findAll();
   }

   public Optional<Turma> buscarPorId(String id) {
       return repository.findById(id);
   }

   public Turma salvar(Turma turma) {
       return repository.save(turma);
   }

   public void excluir(String id) {
       repository.deleteById(id);
   }
}