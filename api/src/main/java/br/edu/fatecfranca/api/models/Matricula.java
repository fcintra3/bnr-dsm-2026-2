package br.edu.fatecfranca.api.models;


import java.time.LocalDate;
import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "matriculas")
public class Matricula {


   @Id
   private String id;


   private String alunoId;
   private String turmaId;


   private LocalDate data;
   private String situacao;


   private List<Nota> notas;


   // Construtor genérico
   public Matricula() {
   }


   // Construtor personalizado completo, com todos os atributos
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
