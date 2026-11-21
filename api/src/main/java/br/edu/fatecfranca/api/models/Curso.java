package br.edu.fatecfranca.api.models;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Document(collection = "cursos")
public class Curso {

    @Id
    private String id;

    @NotBlank(message = "O nome do curso é obrigatório.")
    private String nome;

    @NotNull(message = "A carga horária é obrigatória.")
    @Positive(message = "A carga horária deve ser maior que zero.")
    private Integer cargaHoraria;

    // Valor monetário: armazenado como Decimal128 no MongoDB
    @NotNull(message = "O valor do curso é obrigatório.")
    @Positive(message = "O valor do curso deve ser maior que zero.")
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal valor;

    @NotEmpty(message = "O curso deve ter ao menos um módulo.")
    @Valid
    private List<Modulo> modulos;

    // Construtor genérico
    public Curso() {
    }

    // Construtor personalizado completo, com todos os atributos
    public Curso(
            String id,
            String nome,
            Integer cargaHoraria,
            BigDecimal valor,
            List<Modulo> modulos) {
        this.id = id;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.valor = valor;
        this.modulos = modulos;
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

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public List<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(List<Modulo> modulos) {
        this.modulos = modulos;
    }
}
