package br.edu.fatecfranca.api.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Document(collection = "matriculas")
public class Matricula {

    @Id
    private String id;

    @NotBlank(message = "O identificador do aluno é obrigatório.")
    private String alunoId;

    @NotBlank(message = "O identificador da turma é obrigatório.")
    private String turmaId;

    @NotBlank(message = "A data da matrícula é obrigatória.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "A data da matrícula deve estar no formato AAAA-MM-DD.")
    private String dataMatricula;

    @NotBlank(message = "A situação da matrícula é obrigatória.")
    private String situacao;

    private List<@Valid Nota> notas;

    public Matricula() {
        
    }

    public Matricula(String id, String alunoId, String turmaId, String dataMatricula, List<Nota> notas) {
        this.id = id;
        this.alunoId = alunoId;
        this.turmaId = turmaId;
        this.dataMatricula = dataMatricula;
        this.notas = notas;
    }

    @JsonIgnore
    @AssertTrue(message = "A data da matrícula deve ser uma data válida.")
    public boolean isDataMatriculaValida() {
        if (dataMatricula == null || !dataMatricula.matches("\\d{4}-\\d{2}-\\d{2}")) return true;
        try {
            LocalDate.parse(dataMatricula);
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
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

    public String getDataMatricula() {
         return dataMatricula;
         }
    public void setDataMatricula(String dataMatricula) { 
        this.dataMatricula = dataMatricula;
     }

    public List<Nota> getNotas() {
        return notas;
     }
    public void setNotas(List<Nota> notas) {
         this.notas = notas;
         }
    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}