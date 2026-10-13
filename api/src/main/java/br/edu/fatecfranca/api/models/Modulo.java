package br.edu.fatecfranca.api.models;


public class Modulo {


   private String titulo;
   private String descricao;
   private Integer cargaHoraria;


   public Modulo() {
   }


   public Modulo(
           String titulo,
           String descricao,
           Integer cargaHoraria) {
       this.titulo = titulo;
       this.descricao = descricao;
       this.cargaHoraria = cargaHoraria;
   }


   public String getTitulo() {
       return titulo;
   }


   public void setTitulo(String titulo) {
       this.titulo = titulo;
   }


   public String getDescricao() {
       return descricao;
   }


   public void setDescricao(String descricao) {
       this.descricao = descricao;
   }


   public Integer getCargaHoraria() {
       return cargaHoraria;
   }


   public void setCargaHoraria(Integer cargaHoraria) {
       this.cargaHoraria = cargaHoraria;
   }
}
