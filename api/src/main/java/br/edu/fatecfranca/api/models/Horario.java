package br.edu.fatecfranca.api.models;

import java.time.LocalTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Horario {

    @NotBlank(message = "O dia da semana é obrigatório.")
    private String diaSemana;

    @NotNull(message = "O horário de início é obrigatório.")
    private LocalTime inicio;

    @NotNull(message = "O horário de fim é obrigatório.")
    private LocalTime fim;

    public Horario() {
    }

    public Horario(
            String diaSemana,
            LocalTime inicio,
            LocalTime fim) {
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