package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import br.edu.fatecfranca.api.models.Matricula;
import br.edu.fatecfranca.api.models.Turma;
import br.edu.fatecfranca.api.dtos.MatriculaResponse;
import br.edu.fatecfranca.api.repositories.MatriculaRepository;
import br.edu.fatecfranca.api.repositories.AlunoRepository;
import br.edu.fatecfranca.api.repositories.CursoRepository;
import br.edu.fatecfranca.api.repositories.TurmaRepository;

@Service
public class MatriculaService {
    private final MatriculaRepository repository;
    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;
    private final TurmaRepository turmaRepository;
    private final TurmaService turmaService;

    public MatriculaService(MatriculaRepository repository, AlunoRepository alunoRepository, CursoRepository cursoRepository, TurmaRepository turmaRepository, TurmaService turmaService) {
        this.repository = repository;
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
        this.turmaRepository = turmaRepository;
        this.turmaService = turmaService;
    }

    public List<MatriculaResponse> listarTodos() {
        return repository.findAll().stream().map(this::montarResposta).toList();
    }

    public Optional<MatriculaResponse> buscarPorId(String id) {
        return repository.findById(id).map(this::montarResposta);
    }

    public Matricula criar(Matricula dados) {
        return salvar(null, dados);
    }

    public Matricula atualizar(String id, Matricula dados) {
        verificarExistencia(id);
        return salvar(id, dados);
    }

    private Matricula salvar(String id, Matricula dados) {
        if (!alunoRepository.existsById(dados.alunoId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado.");
        }
        if (!turmaRepository.existsById(dados.turmaId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrado.");
        }
        return repository.save(new Matricula(id, dados.alunoId(), dados.turmaId(), dados.data(), dados.situacao(), dados.notas()));
    }

    public void excluir(String id) {
        verificarExistencia(id);
        repository.deleteById(id);
    }

    private void verificarExistencia(String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cadastro não encontrado.");
        }
    }

    public List<MatriculaResponse> listarPorAluno(String alunoId) {
        if (!alunoRepository.existsById(alunoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado.");
        }
        return repository.findByAlunoId(alunoId).stream().map(this::montarResposta).toList();
    }

    public List<MatriculaResponse> listarPorCurso(String cursoId) {
        if (!cursoRepository.existsById(cursoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado.");
        }
        var turmaIds = turmaRepository.findByCursoId(cursoId).stream().map(Turma::id).toList();
        if (turmaIds.isEmpty()) return List.of();
        return repository.findByTurmaIdIn(turmaIds).stream().map(this::montarResposta).toList();
    }

    private MatriculaResponse montarResposta(Matricula matricula) {
        return new MatriculaResponse(matricula.id(), matricula.alunoId(), matricula.turmaId(), matricula.data(), matricula.situacao(), matricula.notas(),
                alunoRepository.findById(matricula.alunoId()).orElse(null),
                turmaRepository.findById(matricula.turmaId()).map(turmaService::montarResposta).orElse(null));
    }
}
