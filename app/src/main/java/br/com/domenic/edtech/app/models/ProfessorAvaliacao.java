package br.com.domenic.edtech.app.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.TypeConverters;

import java.util.Date;

import br.com.domenic.edtech.app.utils.DateConverter;

@Entity(
        tableName = "professor_avaliacao",
        primaryKeys = {"idAluno", "idProfessor"},
        foreignKeys = {
                @ForeignKey(entity = Aluno.class, parentColumns = "idAluno", childColumns = "idAluno", onDelete = ForeignKey.CASCADE), // Delete on Cascade para Aluno
                @ForeignKey(entity = Professor.class, parentColumns = "idProfessor", childColumns = "idProfessor", onDelete = ForeignKey.CASCADE) // Delete on Cascade para Professor
        },
        indices = {@Index(value = "idProfessor")} // Índice para idProfessor
)
@TypeConverters({DateConverter.class})
public class ProfessorAvaliacao {
    private int idAluno;
    private int idProfessor;
    private int nota;
    private String comentario;
    private Date dataAvaliacao;

    public ProfessorAvaliacao() {

    }

    @Ignore
    public ProfessorAvaliacao(int idAluno, int idProfessor, int nota, String comentario, Date dataAvaliacao) {
        this.idAluno = idAluno;
        this.idProfessor = idProfessor;
        this.nota = nota;
        this.comentario = comentario;
        this.dataAvaliacao = dataAvaliacao;
    }

    // Getters and Setters
    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(Date dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }
}