package br.edu.fatecfranca.api.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class Horario {

    @NotBlank(message = "O dia da semana é obrigatório.")
    private String diaSemana;

    @NotBlank(message = "O horário de início é obrigatório.")
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "O horário de início deve estar no formato HH:mm.")
    private String inicio;

    @NotBlank(message = "O horário de fim é obrigatório.")
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "O horário de fim deve estar no formato HH:mm.")
    private String fim;

    public Horario() {
    }

    public Horario(
            String diaSemana,
            String inicio,
            String fim) {
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

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }
}
