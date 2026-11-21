package br.edu.fatecfranca.api.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class Modulo {

    @NotBlank(message = "O título do módulo é obrigatório.")
    private String titulo; 

    @NotBlank(message = "A descrição do módulo é obrigatória.")
    private String descricao; 

    @NotNull(message = "A carga horária do módulo é obrigatória.")
    @Positive(message = "A carga horária deve ser maior que zero.")
    private Integer cargaHoraria;
    private String nome;
    private String conteudo;

    public Modulo() {
    }

    public Modulo(String nome, String conteudo) {
        this.nome = nome;
        this.conteudo = conteudo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}