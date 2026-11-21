
package br.edu.fatecfranca.api.controllers;

import java.util.List;
import java.util.Optional;

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

import br.edu.fatecfranca.api.models.Matricula;
import br.edu.fatecfranca.api.services.MatriculaService;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaService service;

    public MatriculaController(MatriculaService service) {
        this.service = service;
    }

    // Listar todas as matrículas
    @GetMapping
    public List<Matricula> listarTodos() {
        return service.listarTodos();
    }

    // Buscar matrícula por ID
    @GetMapping("/{id}")
    public Optional<Matricula> buscarPorId(@PathVariable String id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/aluno/{alunoId}")
    public List<Matricula> listarPorAluno(
            @PathVariable String alunoId) {
        return service.listarPorAluno(alunoId);
    }

    @GetMapping("/curso/{cursoId}")
    public List<Matricula> listarPorCurso(
            @PathVariable String cursoId) {
        return service.listarPorCurso(cursoId);
    }

    // Cadastrar matrícula
    @PostMapping
    public ResponseEntity<Matricula> salvar(@RequestBody Matricula matricula) {
        Matricula matriculaSalva = service.salvar(matricula);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(matriculaSalva);
    }

    // Atualizar matrícula
    @PutMapping("/{id}")
    public Matricula atualizar(
            @PathVariable String id,
            @RequestBody Matricula matricula) {

        matricula.setId(id);
        return service.salvar(matricula);
    }

    // Excluir matrícula
    // @DeleteMapping("/{id}")
    // public void excluir(@PathVariable String id) {
    // service.excluir(id);
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        service.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
