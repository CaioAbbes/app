package br.com.domenic.edtech.app.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import br.com.domenic.edtech.app.utils.DateConverter;

@Entity(
        tableName = "cursos",
        foreignKeys = @ForeignKey(
                entity = Professor.class,
                parentColumns = "idProfessor",
                childColumns = "idProfessor",
                onDelete = ForeignKey.CASCADE // Adiciona a opção de Delete on Cascade
        ),
        indices = {@Index(value = "idProfessor")} // Índice para idProfessor
)
@TypeConverters({DateConverter.class})
public class Curso {
    @PrimaryKey(autoGenerate = true)
    private int idCurso;
    private int idProfessor;
    private String nomeCurso;
    private String descricao;
    private double precoCurso;
    private double precoCursoAulaParticular;
    private int duracaoHoras;
    private Date dataCriacao;
    private int popularidade;
    private int recomendacao;

    public Curso() {
    }

    @Ignore
    public Curso(String nome, String descricao, int duracaoHoras, double precoCurso, double precoCursoAulaParticular, int idProfessor, Date dataCriacao, int popularidade, int recomendacao) {
        this.nomeCurso = nome;
        this.descricao = descricao;
        this.precoCurso = precoCurso;
        this.precoCursoAulaParticular = precoCursoAulaParticular;
        this.duracaoHoras = duracaoHoras;
        this.idProfessor = idProfessor;
        this.dataCriacao = dataCriacao;
        this.popularidade = popularidade;
        this.recomendacao = recomendacao;
    }

    // Getters and Setters
    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoCurso() {
        return precoCurso;
    }

    public void setPrecoCurso(double precoCurso) {
        this.precoCurso = precoCurso;
    }

    public double getPrecoCursoAulaParticular() {
        return precoCursoAulaParticular;
    }

    public void setPrecoCursoAulaParticular(double precoCursoAulaParticular) {
        this.precoCursoAulaParticular = precoCursoAulaParticular;
    }

    public int getDuracaoHoras() {
        return duracaoHoras;
    }

    public void setDuracaoHoras(int duracaoHoras) {
        this.duracaoHoras = duracaoHoras;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getPopularidade() {
        return popularidade;
    }

    public void setPopularidade(int popularidade) {
        this.popularidade = popularidade;
    }

    public int getRecomendacao() {
        return recomendacao;
    }

    public void setRecomendacao(int recomendacao) {
        this.recomendacao = recomendacao;
    }
}