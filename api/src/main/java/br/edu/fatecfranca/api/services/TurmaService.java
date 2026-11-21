package br.edu.fatecfranca.api.services;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import br.edu.fatecfranca.api.models.Turma;
import br.edu.fatecfranca.api.dtos.TurmaResponse;
import br.edu.fatecfranca.api.repositories.TurmaRepository;
import br.edu.fatecfranca.api.repositories.CursoRepository;
import br.edu.fatecfranca.api.repositories.InstrutorRepository;
import br.edu.fatecfranca.api.repositories.MatriculaRepository;

@Service
public class TurmaService {
    private final TurmaRepository repository;
    private final CursoRepository cursoRepository;
    private final InstrutorRepository instrutorRepository;
    private final MatriculaRepository matriculaRepository;

    public TurmaService(TurmaRepository repository, CursoRepository cursoRepository, InstrutorRepository instrutorRepository, MatriculaRepository matriculaRepository) {
        this.repository = repository;
        this.cursoRepository = cursoRepository;
        this.instrutorRepository = instrutorRepository;
        this.matriculaRepository = matriculaRepository;
    }

    public List<TurmaResponse> listarTodos() {
        return repository.findAll().stream().map(this::montarResposta).toList();
    }

    public Optional<TurmaResponse> buscarPorId(String id) {
        return repository.findById(id).map(this::montarResposta);
    }

    public Turma criar(Turma dados) {
        return salvar(null, dados);
    }

    public Turma atualizar(String id, Turma dados) {
        verificarExistencia(id);
        return salvar(id, dados);
    }

    private Turma salvar(String id, Turma dados) {
        if (!cursoRepository.existsById(dados.cursoId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado.");
        }
        if (!instrutorRepository.existsById(dados.instrutorId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Instrutor não encontrado.");
        }
        return repository.save(new Turma(id, dados.cursoId(), dados.instrutorId(), dados.inicio(), dados.fim(), dados.vagas(), dados.status(), dados.horarios()));
    }

    public void excluir(String id) {
        verificarExistencia(id);
        if (matriculaRepository.existsByTurmaId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A turma possui matrículas.");
        }
        repository.deleteById(id);
    }

    private void verificarExistencia(String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cadastro não encontrado.");
        }
    }

    public List<TurmaResponse> listarPorCurso(String cursoId) {
        if (!cursoRepository.existsById(cursoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado.");
        }
        return repository.findByCursoId(cursoId).stream().map(this::montarResposta).toList();
    }

    TurmaResponse montarResposta(Turma turma) {
        return new TurmaResponse(turma.id(), turma.cursoId(), turma.instrutorId(), turma.inicio(), turma.fim(), turma.vagas(), turma.status(), turma.horarios(),
                cursoRepository.findById(turma.cursoId()).orElse(null),
                instrutorRepository.findById(turma.instrutorId()).orElse(null));
    }
}
