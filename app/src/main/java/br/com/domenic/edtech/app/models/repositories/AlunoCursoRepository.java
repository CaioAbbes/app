package br.com.domenic.edtech.app.models.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.domenic.edtech.app.database.AppDatabase;
import br.com.domenic.edtech.app.models.AlunoCurso;
import br.com.domenic.edtech.app.database.dao.AlunoCursoDao;
import br.com.domenic.edtech.app.models.Curso;
import br.com.domenic.edtech.app.models.repositories.base.BaseRepository;

public class AlunoCursoRepository extends BaseRepository<AlunoCurso> {

    private final AlunoCursoDao alunoCursoDao;

    public AlunoCursoRepository(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        alunoCursoDao = db.alunoCursoDao();
    }

    @Override
    public LiveData<List<AlunoCurso>> getAll() {
        return alunoCursoDao.getAll();
    }

    // Novo método para buscar os cursos do aluno
    public LiveData<List<AlunoCurso>> getCursosByAluno(int idAluno) {
        return alunoCursoDao.getCursosByAluno(idAluno);
    }

    @Override
    protected void insertInDatabase(AlunoCurso alunoCurso) {
        alunoCursoDao.insert(alunoCurso);
    }

    @Override
    protected void updateInDatabase(AlunoCurso alunoCurso) {
        alunoCursoDao.update(alunoCurso);
    }

    @Override
    protected void deleteInDatabase(AlunoCurso alunoCurso) {
        alunoCursoDao.delete(alunoCurso);
    }

    public LiveData<List<Curso>> getOnlyCursosByAluno(int idAluno) {
        return alunoCursoDao.getOnlyCursosByAluno(idAluno);
    }

    @Override
    protected void deleteInDatabaseByCriteria(int idAluno) {
        alunoCursoDao.deleteAllByAlunoId(idAluno);
    }

    public void deleteAll() {
        alunoCursoDao.deleteAll();
    }
}