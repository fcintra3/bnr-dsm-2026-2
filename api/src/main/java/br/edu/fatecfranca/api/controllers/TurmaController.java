package br.edu.fatecfranca.api.controllers;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.fatecfranca.api.models.Turma;
import br.edu.fatecfranca.api.dtos.TurmaResponse;
import br.edu.fatecfranca.api.services.TurmaService;

@RestController
@RequestMapping("/turmas")
public class TurmaController {
    private final TurmaService service;

    public TurmaController(TurmaService service) {
        this.service = service;
    }

    @GetMapping
    public List<TurmaResponse> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaResponse> buscarPorId(@PathVariable String id) {
        return ResponseEntity.of(service.buscarPorId(id));
    }

    @GetMapping("/curso/{cursoId}")
    public List<TurmaResponse> listarPorCurso(@PathVariable String cursoId) {
        return service.listarPorCurso(cursoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Turma criar(@Valid @RequestBody Turma dados) {
        return service.criar(dados);
    }

    @PutMapping("/{id}")
    public Turma atualizar(@PathVariable String id, @Valid @RequestBody Turma dados) {
        return service.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable String id) {
        service.excluir(id);
    }
}
