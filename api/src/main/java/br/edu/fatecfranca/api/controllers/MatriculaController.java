package br.edu.fatecfranca.api.controllers;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.fatecfranca.api.models.Matricula;
import br.edu.fatecfranca.api.dtos.MatriculaResponse;
import br.edu.fatecfranca.api.services.MatriculaService;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {
    private final MatriculaService service;

    public MatriculaController(MatriculaService service) {
        this.service = service;
    }

    @GetMapping
    public List<MatriculaResponse> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaResponse> buscarPorId(@PathVariable String id) {
        return ResponseEntity.of(service.buscarPorId(id));
    }

    @GetMapping("/aluno/{alunoId}")
    public List<MatriculaResponse> listarPorAluno(@PathVariable String alunoId) {
        return service.listarPorAluno(alunoId);
    }

    @GetMapping("/curso/{cursoId}")
    public List<MatriculaResponse> listarPorCurso(@PathVariable String cursoId) {
        return service.listarPorCurso(cursoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Matricula criar(@Valid @RequestBody Matricula dados) {
        return service.criar(dados);
    }

    @PutMapping("/{id}")
    public Matricula atualizar(@PathVariable String id, @Valid @RequestBody Matricula dados) {
        return service.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable String id) {
        service.excluir(id);
    }
}
