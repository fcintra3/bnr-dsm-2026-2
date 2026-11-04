package br.edu.fatecfranca.api.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matriculas")
public class Matricula {

    @Id
    private String id;

    @NotNull(message = "O ID do aluno é obrigatório.")
    private String alunoId;
    @NotNull(message = "O ID da turma é obrigatório.")
    private String turmaId;
    @NotNull(message = "A data da matrícula é obrigatória.")
    private LocalDate data;
    @NotBlank(message = "A situação da matrícula é obrigatória.")
    private String situacao;
    @Valid
    private List<Nota> notas;

    public Matricula() {
    }

    public Matricula(
            String id,
            String alunoId,
            String turmaId,
            LocalDate data,
            String situacao,
            List<Nota> notas) {
        this.id = id;
        this.alunoId = alunoId;
        this.turmaId = turmaId;
        this.data = data;
        this.situacao = situacao;
        this.notas = notas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(String alunoId) {
        this.alunoId = alunoId;
    }

    public String getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(String turmaId) {
        this.turmaId = turmaId;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }
}