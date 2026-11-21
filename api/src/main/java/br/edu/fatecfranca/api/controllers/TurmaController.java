package br.edu.fatecfranca.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fatecfranca.api.dtos.TurmaResponse;
import br.edu.fatecfranca.api.models.Turma;
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
        return service.listarComDetalhes();
    }

    @GetMapping("/curso/{cursoId}")
    public List<TurmaResponse> listarPorCurso(
            @PathVariable String cursoId) {
        return service.listarPorCursoComDetalhes(cursoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaResponse> buscarPorId(@PathVariable String id) {
        return service.buscarComDetalhes(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Cadastrar turma - STATUS 201
    @PostMapping
    public ResponseEntity<Turma> salvar(@RequestBody Turma turma) {
        Turma turmaSalva = service.salvar(turma);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(turmaSalva);
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> excluir(@PathVariable String id) {
    //     service.excluir(id);
    //     return ResponseEntity.noContent().build();
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        service.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> atualizar(
            @PathVariable String id,
            @RequestBody Turma turma) {

        turma.setId(id);

        Turma turmaAtualizada = service.salvar(turma);

        return ResponseEntity.ok(turmaAtualizada);
    }
}
