package br.edu.fatecfranca.api.models;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Horario {

    @NotBlank(message = "O dia da semana é obrigatório.")
    @Pattern(
            regexp = "(?i)((segunda|terça|quarta|quinta|sexta)-feira|sábado|domingo)",
            message = "O dia da semana deve ser segunda-feira, terça-feira, quarta-feira, quinta-feira, sexta-feira, sábado ou domingo.")
    private String diaSemana;

    @NotNull(message = "O horário de início é obrigatório.")
    private LocalTime inicio;

    @NotNull(message = "O horário de término é obrigatório.")
    private LocalTime fim;

    // Validação que compara dois atributos, como em Turma
    @JsonIgnore
    @AssertTrue(message = "O horário de término deve ser posterior ao de início.")
    public boolean isIntervaloValido() {
        if (inicio == null || fim == null) {
            return true;
        }

        return fim.isAfter(inicio);
    }

    public Horario() {
    }

    public Horario(String diaSemana, LocalTime inicio, LocalTime fim) {
        this.diaSemana = diaSemana;
        this.inicio = inicio;
        this.fim = fim;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public LocalTime getFim() {
        return fim;
    }

    public void setFim(LocalTime fim) {
        this.fim = fim;
    }
}
