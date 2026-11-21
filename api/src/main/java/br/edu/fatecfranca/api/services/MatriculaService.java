package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fatecfranca.api.models.Matricula;
import br.edu.fatecfranca.api.repositories.AlunoRepository;
import br.edu.fatecfranca.api.repositories.MatriculaRepository;
import br.edu.fatecfranca.api.repositories.TurmaRepository;

@Service
public class MatriculaService {

    private final MatriculaRepository repository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;

    public MatriculaService(
            MatriculaRepository repository,
            AlunoRepository alunoRepository,
            TurmaRepository turmaRepository) {

        this.repository = repository;
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
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

    public List<Matricula> listarPorCurso(String cursoId) {

        List<String> turmaIds = turmaRepository.findByCursoId(cursoId)
                .stream()
                .map(turma -> turma.getId())
                .toList();

        if (turmaIds.isEmpty()) {
            return List.of();
        }

        return repository.findByTurmaIdIn(turmaIds);
    }

    public Matricula salvar(Matricula matricula) {
        verificarAluno(matricula.getAlunoId());
        return repository.save(matricula);
    }

    public void excluir(String id) {
        repository.deleteById(id);
    }
}
