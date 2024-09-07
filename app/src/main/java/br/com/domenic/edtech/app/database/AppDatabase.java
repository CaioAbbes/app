package br.com.domenic.edtech.app.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import br.com.domenic.edtech.app.models.*;
import br.com.domenic.edtech.app.database.dao.*;

@Database(entities = {
        Professor.class,
        Aluno.class,
        Curso.class,
        AulaCurso.class,
        AulaCursoInscrito.class,
        AlunoCurso.class,
        CursoAvaliacao.class,
        ProfessorAvaliacao.class
}, version = 22, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ProfessorDao professorDao();
    public abstract AlunoDao alunoDao();
    public abstract CursoDao cursoDao();
    public abstract AulaCursoDao aulaCursoDao();
    public abstract AulaCursoInscritoDao aulaCursoInscritoDao();
    public abstract AlunoCursoDao alunoCursoDao();
    public abstract CursoAvaliacaoDao cursoAvaliacaoDao();
    public abstract ProfessorAvaliacaoDao professorAvaliacaoDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "edtech-db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}