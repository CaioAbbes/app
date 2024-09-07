package br.com.domenic.edtech.app.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "aula_curso_inscrito",
        foreignKeys = @ForeignKey(
                entity = AulaCurso.class,
                parentColumns = "idAulaCurso",
                childColumns = "idAulaCurso",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(value = "idAulaCurso") // Índice para idAulaCurso
)
public class AulaCursoInscrito {
    @PrimaryKey(autoGenerate = true)
    private int idAulaCursoInscrito;
    private int idAulaCurso;
    private int idAluno;

    public AulaCursoInscrito() {

    }

    @Ignore
    public AulaCursoInscrito(int idAulaCurso, int idAluno) {
        this.idAulaCurso = idAulaCurso;
        this.idAluno = idAluno;
    }

    public int getIdAulaCursoInscrito() {
        return idAulaCursoInscrito;
    }

    public void setIdAulaCursoInscrito(int idAulaCursoInscrito) {
        this.idAulaCursoInscrito = idAulaCursoInscrito;
    }

    public int getIdAulaCurso() {
        return idAulaCurso;
    }

    public void setIdAulaCurso(int idAulaCurso) {
        this.idAulaCurso = idAulaCurso;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }
}