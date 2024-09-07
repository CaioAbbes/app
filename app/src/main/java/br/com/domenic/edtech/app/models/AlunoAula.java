package br.com.domenic.edtech.app.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import br.com.domenic.edtech.app.utils.DateConverter;

@Entity(
        tableName = "aluno_aula",
        foreignKeys = @ForeignKey(
                entity = AulaCurso.class, // Referencia a entidade correta
                parentColumns = "idAulaCurso",
                childColumns = "idAulaCurso",
                onDelete = ForeignKey.CASCADE // Aplica o delete on cascade
        ),
        indices = @Index(value = "idAulaCurso")  // Adiciona um índice na coluna idAulaCurso
)
@TypeConverters({DateConverter.class})
public class AlunoAula {
    @PrimaryKey(autoGenerate = true)
    private int idAlunoAula;
    private int idAluno;
    private int idAulaCurso;
}