package br.edu.fatecfranca.api.services;


import java.util.List;
import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import br.edu.fatecfranca.api.models.Instrutor;
import br.edu.fatecfranca.api.repositories.InstrutorRepository;
import br.edu.fatecfranca.api.repositories.TurmaRepository;


@Service
public class InstrutorService {


   private final InstrutorRepository repository;
   private final TurmaRepository turmaRepository;


   public InstrutorService(
           InstrutorRepository repository,
           TurmaRepository turmaRepository) {
       this.repository = repository;
       this.turmaRepository = turmaRepository;
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
           throw new ResponseStatusException(
                   HttpStatus.NOT_FOUND,
                   "Instrutor não encontrado.");
       }


       if (turmaRepository.existsByInstrutorId(id)) {
           throw new ResponseStatusException(
                   HttpStatus.CONFLICT,
                   "Não é possível excluir um instrutor com turmas vinculadas.");
       }


       repository.deleteById(id);
   }
}
