package br.edu.fatecfranca.api.services;


import java.util.List;
import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import br.edu.fatecfranca.api.models.Matricula;
import br.edu.fatecfranca.api.repositories.AlunoRepository;
import br.edu.fatecfranca.api.repositories.MatriculaRepository;


@Service
public class MatriculaService {


   private final MatriculaRepository repository;
   private final AlunoRepository alunoRepository;


   public MatriculaService(MatriculaRepository repository, AlunoRepository alunoRepository) {
       this.repository = repository;
       this.alunoRepository = alunoRepository;
   }


   private void verificarAluno(String alunoId) {
       if (alunoId == null || alunoId.isBlank()) {
           throw new ResponseStatusException(
                   HttpStatus.BAD_REQUEST,
                   "O identificador do aluno é obrigatório.");
       }


       if (!alunoRepository.existsById(alunoId)) {
           throw new ResponseStatusException(
                   HttpStatus.NOT_FOUND,
                   "Aluno não encontrado.");
       }
   }


   public List<Matricula> listarTodos() {
       return repository.findAll();
   }


   public Optional<Matricula> buscarPorId(String id) {
       return repository.findById(id);
   }


   public List<Matricula> listarPorAluno(String alunoId) {
       verificarAluno(alunoId);
       return repository.findByAlunoId(alunoId);
   }


   public Matricula salvar(Matricula matricula) {
       verificarAluno(matricula.getAlunoId());
       return repository.save(matricula);
   }


   public void excluir(String id) {
       repository.deleteById(id);
   }
}
