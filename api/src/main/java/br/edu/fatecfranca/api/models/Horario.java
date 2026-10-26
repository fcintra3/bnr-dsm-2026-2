package br.edu.fatecfranca.api.models;

import java.time.LocalTime;

public class Horario {

    private String diaSemana;
    private LocalTime inicio;
    private LocalTime fim;

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
