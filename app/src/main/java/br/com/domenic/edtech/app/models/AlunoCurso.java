package br.com.domenic.edtech.app.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import br.com.domenic.edtech.app.utils.DateConverter;

@Entity(
        tableName = "aluno_curso",
        foreignKeys = {
                @ForeignKey(
                        entity = Aluno.class,
                        parentColumns = "idAluno",
                        childColumns = "idAluno",
                        onDelete = ForeignKey.CASCADE // Aplica o delete on cascade para a entidade Aluno
                ),
                @ForeignKey(
                        entity = Curso.class,
                        parentColumns = "idCurso",
                        childColumns = "idCurso",
                        onDelete = ForeignKey.CASCADE // Aplica o delete on cascade para a entidade Curso
                )
        },
        indices = {
                @Index(value = "idAluno"),  // Índice na coluna idAluno
                @Index(value = "idCurso")   // Índice na coluna idCurso
        }
)
@TypeConverters({DateConverter.class})
public class AlunoCurso {
    @PrimaryKey(autoGenerate = true)
    private int idAlunoCurso;
    private int idAluno;
    private int idCurso;
    private Date dataMatricula;
    private Date dataConclusao;
    private String status;
    private int qtdCursoAulaParticular;
    private Double notaFinal;
    private String comentarios;

    public AlunoCurso() {
    }

    @Ignore
    public AlunoCurso(int idAluno, int idCurso, Date dataMatricula, Date dataConclusao, String status, int qtdCursoAulaParticular, Double notaFinal, String comentarios) {
        this.idAluno = idAluno;
        this.idCurso = idCurso;
        this.dataMatricula = dataMatricula;
        this.dataConclusao = dataConclusao;
        this.status = status;
        this.qtdCursoAulaParticular = qtdCursoAulaParticular;
        this.notaFinal = notaFinal;
        this.comentarios = comentarios;
    }

    // Getters and Setters
    public int getIdAlunoCurso() {
        return idAlunoCurso;
    }

    public void setIdAlunoCurso(int idAlunoCurso) {
        this.idAlunoCurso = idAlunoCurso;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public Date getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(Date dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public Date getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(Date dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQtdCursoAulaParticular() {
        return qtdCursoAulaParticular;
    }

    public void setQtdCursoAulaParticular(int qtdCursoAulaParticular) {
        this.qtdCursoAulaParticular = qtdCursoAulaParticular;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}