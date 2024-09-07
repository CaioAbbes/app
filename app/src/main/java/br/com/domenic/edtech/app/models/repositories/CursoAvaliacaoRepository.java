package br.com.domenic.edtech.app.models.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.domenic.edtech.app.database.AppDatabase;
import br.com.domenic.edtech.app.models.CursoAvaliacao;
import br.com.domenic.edtech.app.database.dao.CursoAvaliacaoDao;
import br.com.domenic.edtech.app.models.Professor;
import br.com.domenic.edtech.app.models.repositories.base.BaseRepository;

public class CursoAvaliacaoRepository extends BaseRepository<CursoAvaliacao> {

    private final CursoAvaliacaoDao cursoAvaliacaoDao;

    public CursoAvaliacaoRepository(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        cursoAvaliacaoDao = db.cursoAvaliacaoDao();
    }

    @Override
    public LiveData<List<CursoAvaliacao>> getAll() {
        return cursoAvaliacaoDao.getAll();
    }

    @Override
    protected void insertInDatabase(CursoAvaliacao cursoAvaliacao) {
        cursoAvaliacaoDao.insert(cursoAvaliacao);
    }

    @Override
    protected void updateInDatabase(CursoAvaliacao cursoAvaliacao) {
        cursoAvaliacaoDao.update(cursoAvaliacao);
    }

    @Override
    protected void deleteInDatabase(CursoAvaliacao cursoAvaliacao) {
        cursoAvaliacaoDao.delete(cursoAvaliacao);
    }

    @Override
    protected void deleteInDatabaseByCriteria(int criteria) {
        //
    }

    public LiveData<List<CursoAvaliacao>> findAvaliacoesByCursoId(int cursoId) {
        return cursoAvaliacaoDao.getAvaliacoesByCursoId(cursoId);
    }

    public void deleteAll() {
        cursoAvaliacaoDao.deleteAll();
    }
}