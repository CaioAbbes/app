package br.com.domenic.edtech.app.models.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.domenic.edtech.app.database.AppDatabase;
import br.com.domenic.edtech.app.models.ProfessorAvaliacao;
import br.com.domenic.edtech.app.database.dao.ProfessorAvaliacaoDao;
import br.com.domenic.edtech.app.models.repositories.base.BaseRepository;

public class ProfessorAvaliacaoRepository extends BaseRepository<ProfessorAvaliacao> {

    private final ProfessorAvaliacaoDao professorAvaliacaoDao;

    public ProfessorAvaliacaoRepository(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        professorAvaliacaoDao = db.professorAvaliacaoDao();
    }

    @Override
    public LiveData<List<ProfessorAvaliacao>> getAll() {
        return professorAvaliacaoDao.getAll();
    }

    @Override
    protected void insertInDatabase(ProfessorAvaliacao professorAvaliacao) {
        professorAvaliacaoDao.insert(professorAvaliacao);
    }

    @Override
    protected void updateInDatabase(ProfessorAvaliacao professorAvaliacao) {
        professorAvaliacaoDao.update(professorAvaliacao);
    }

    @Override
    protected void deleteInDatabase(ProfessorAvaliacao professorAvaliacao) {
        professorAvaliacaoDao.delete(professorAvaliacao);
    }

    @Override
    protected void deleteInDatabaseByCriteria(int criteria) {
        //
    }

    public void deleteAll() {
        professorAvaliacaoDao.deleteAll();
    }
}