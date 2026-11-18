package br.edu.fatecfranca.api.models;

public class Nota {

    private String avaliacao;
    private Double valor;

    public Nota() {
    }

    public Nota(String avaliacao, Double valor) {
        this.avaliacao = avaliacao;
        this.valor = valor;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}