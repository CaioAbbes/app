package br.com.domenic.edtech.app.models.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.domenic.edtech.app.database.AppDatabase;
import br.com.domenic.edtech.app.database.dao.AulaCursoDao;
import br.com.domenic.edtech.app.models.AulaCurso;
import br.com.domenic.edtech.app.models.repositories.base.BaseRepository;

public class AulaCursoRepository extends BaseRepository<AulaCurso> {

    private final AulaCursoDao aulaCursoDao;

    public AulaCursoRepository(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        aulaCursoDao = db.aulaCursoDao();
    }

    @Override
    public LiveData<List<AulaCurso>> getAll() {
        return aulaCursoDao.getAll();
    }

    @Override
    protected void insertInDatabase(AulaCurso aulaCurso) {
        aulaCursoDao.insert(aulaCurso);
    }

    @Override
    protected void updateInDatabase(AulaCurso aulaCurso) {
        aulaCursoDao.update(aulaCurso);
    }

    @Override
    protected void deleteInDatabase(AulaCurso aulaCurso) {
        aulaCursoDao.delete(aulaCurso);
    }

    @Override
    protected void deleteInDatabaseByCriteria(int criteria) {
        //
    }

    public LiveData<List<AulaCurso>> findAulasByCursoId(int idCurso) {
        return aulaCursoDao.findAulasByCursoId(idCurso);
    }

    public LiveData<List<AulaCurso>> findAulasByProfessorId(int idProfessor) {
        return aulaCursoDao.findAulasByProfessorId(idProfessor);
    }

    public LiveData<List<AulaCurso>> findAulasByAlunoId(int idAluno) {
        return aulaCursoDao.findAulasByAlunoId(idAluno);
    }

    public void deleteAll() {
        aulaCursoDao.deleteAll();
    }
}