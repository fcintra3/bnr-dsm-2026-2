package br.edu.fatecfranca.api.controllers;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.fatecfranca.api.models.Instrutor;
import br.edu.fatecfranca.api.services.InstrutorService;

@RestController
@RequestMapping("/instrutores")
public class InstrutorController {
    private final InstrutorService service;

    public InstrutorController(InstrutorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Instrutor> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instrutor> buscarPorId(@PathVariable String id) {
        return ResponseEntity.of(service.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Instrutor criar(@Valid @RequestBody Instrutor dados) {
        return service.criar(dados);
    }

}
