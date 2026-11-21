
package br.edu.fatecfranca.api.models;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "turmas")
public class Turma {

    @Id
    private String id;
    private String nome;

    @NotBlank(message = "O curso da turma é obrigatório.")
    private String cursoId;
    private String instrutorId;

    @NotNull(message = "A data inicial é obrigatória.")
    private LocalDate inicio;

    @NotNull(message = "A data final é obrigatória.")
    private LocalDate fim;

    @NotNull(message = "O número de vagas é obrigatório.")
    @Positive(message = "O número de vagas deve ser maior que zero.")
    private Integer vagas;

    @NotBlank(message = "O status é obrigatório.")
    @Pattern(regexp = "PREVISTA|ATIVA|ENCERRADA|CANCELADA", message = "O status deve ser PREVISTA, ATIVA, ENCERRADA ou CANCELADA.")
    private String status;

    @NotEmpty(message = "A turma deve possuir ao menos um horário.")
    private List<@Valid Horario> horarios;


    public Turma() {
    }

    public Turma(
            String id,
            String nome,
            String cursoId,
            String instrutorId,
            LocalDate inicio,
            LocalDate fim,
            Integer vagas,
            List<Horario> horarios) {

        this.id = id;
        this.nome = nome;
        this.cursoId = cursoId;
        this.instrutorId = instrutorId;
        this.inicio = inicio;
        this.fim = fim;
        this.vagas = vagas;
        this.horarios = horarios;
    }
    @JsonIgnore
    @AssertTrue(message = "A data final não pode ser anterior à data inicial.")
    public boolean isPeriodoValido() {
        if (inicio == null || fim == null) {
            return true;
        }
        return !fim.isBefore(inicio);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCursoId() {
        return cursoId;
    }

    public void setCursoId(String cursoId) {
        this.cursoId = cursoId;
    }

    public String getInstrutorId() {
        return instrutorId;
    }

    public void setInstrutorId(String instrutorId) {
        this.instrutorId = instrutorId;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
