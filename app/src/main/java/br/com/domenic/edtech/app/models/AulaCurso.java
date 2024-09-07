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
        tableName = "aula_curso",
        foreignKeys = {
                @ForeignKey(
                        entity = Curso.class,
                        parentColumns = "idCurso",
                        childColumns = "idCurso",
                        onDelete = ForeignKey.CASCADE  // Define o comportamento ao deletar o curso
                ),
                @ForeignKey(
                        entity = Professor.class,
                        parentColumns = "idProfessor",
                        childColumns = "idProfessor",
                        onDelete = ForeignKey.CASCADE  // Define o comportamento ao deletar o professor
                )
        },
        indices = {
                @Index(value = "idCurso"),      // Índice na coluna idCurso
                @Index(value = "idProfessor")   // Índice na coluna idProfessor
        }
)
@TypeConverters({DateConverter.class})
public class AulaCurso {
    @PrimaryKey(autoGenerate = true)
    private int idAulaCurso;
    private int idCurso;
    private int idProfessor;
    private boolean aulaParticular;
    private boolean aulaOnline;
    private String titulo;
    private String descricao;
    private Date dataAula;
    private String linkAulaAoVivo;

    public AulaCurso() {

    }

    @Ignore
    public AulaCurso(int idCurso, int idProfessor, boolean aulaParticular, boolean aulaOnline, String titulo, String descricao, Date dataAula, String linkAulaAoVivo) {
        this.idCurso = idCurso;
        this.idProfessor = idProfessor;
        this.aulaParticular = aulaParticular;
        this.aulaOnline = aulaOnline;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataAula = dataAula;
        this.linkAulaAoVivo = linkAulaAoVivo;
    }

    public int getIdAulaCurso() {
        return idAulaCurso;
    }

    public void setIdAulaCurso(int idAulaCurso) {
        this.idAulaCurso = idAulaCurso;
    }

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

    public boolean isAulaParticular() {
        return aulaParticular;
    }

    public void setAulaParticular(boolean aulaParticular) {
        this.aulaParticular = aulaParticular;
    }

    public boolean isAulaOnline() {
        return aulaOnline;
    }

    public void setAulaOnline(boolean aulaOnline) {
        this.aulaOnline = aulaOnline;
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

    public Date getDataAula() {
        return dataAula;
    }

    public void setDataAula(Date dataAula) {
        this.dataAula = dataAula;
    }

    public String getLinkAulaAoVivo() {
        return linkAulaAoVivo;
    }

    public void setLinkAulaAoVivo(String linkAulaAoVivo) {
        this.linkAulaAoVivo = linkAulaAoVivo;
    }
}