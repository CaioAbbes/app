package br.com.domenic.edtech.app.models.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.domenic.edtech.app.database.AppDatabase;
import br.com.domenic.edtech.app.models.Aluno;
import br.com.domenic.edtech.app.models.Curso;
import br.com.domenic.edtech.app.database.dao.CursoDao;
import br.com.domenic.edtech.app.models.repositories.base.BaseRepository;

public class CursoRepository extends BaseRepository<Curso> {

    private final CursoDao cursoDao;

    public CursoRepository(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        cursoDao = db.cursoDao();
    }

    @Override
    public LiveData<List<Curso>> getAll() {
        return cursoDao.getAll();
    }

    @Override
    protected void insertInDatabase(Curso curso) {
        cursoDao.insert(curso);
    }

    @Override
    protected void updateInDatabase(Curso curso) {
        cursoDao.update(curso);
    }

    @Override
    protected void deleteInDatabase(Curso curso) {
        cursoDao.delete(curso);
    }

    public LiveData<List<Curso>> findByProfessorId(int idProfessor) {
        return cursoDao.findByProfessorId(idProfessor);
    }

    @Override
    protected void deleteInDatabaseByCriteria(int criteria) {
        //
    }

    public void deleteAll() {
        cursoDao.deleteAll();
    }
}