package br.com.domenic.edtech.app.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.TypeConverters;

import java.util.Date;

import br.com.domenic.edtech.app.utils.DateConverter;

@Entity(
        tableName = "curso_avaliacao",
        primaryKeys = {"idAluno", "idCurso"},
        foreignKeys = {
                @ForeignKey(entity = Aluno.class, parentColumns = "idAluno", childColumns = "idAluno", onDelete = ForeignKey.CASCADE), // Delete on Cascade para Aluno
                @ForeignKey(entity = Curso.class, parentColumns = "idCurso", childColumns = "idCurso", onDelete = ForeignKey.CASCADE)  // Delete on Cascade para Curso
        },
        indices = {@Index(value = "idCurso")} // Índice para idCurso
)
@TypeConverters({DateConverter.class})
public class CursoAvaliacao {
    private int idAluno;
    private int idCurso;
    private int nota;
    private String comentario;
    private Date dataAvaliacao;

    // Getters and Setters
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