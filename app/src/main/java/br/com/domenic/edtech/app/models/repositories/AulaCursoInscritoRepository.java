package br.com.domenic.edtech.app.models.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.domenic.edtech.app.database.AppDatabase;
import br.com.domenic.edtech.app.database.dao.AulaCursoInscritoDao;
import br.com.domenic.edtech.app.models.AulaCursoInscrito;
import br.com.domenic.edtech.app.models.repositories.base.BaseRepository;

public class AulaCursoInscritoRepository extends BaseRepository<AulaCursoInscrito> {

    private final AulaCursoInscritoDao aulaCursoInscritoDao;

    public AulaCursoInscritoRepository(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        aulaCursoInscritoDao = db.aulaCursoInscritoDao();
    }

    @Override
    public LiveData<List<AulaCursoInscrito>> getAll() {
        return aulaCursoInscritoDao.getAll();
    }

    @Override
    protected void insertInDatabase(AulaCursoInscrito aulaCursoInscrito) {
        aulaCursoInscritoDao.insert(aulaCursoInscrito);
    }

    @Override
    protected void updateInDatabase(AulaCursoInscrito aulaCursoInscrito) {
        aulaCursoInscritoDao.update(aulaCursoInscrito);
    }

    @Override
    protected void deleteInDatabase(AulaCursoInscrito aulaCursoInscrito) {
        aulaCursoInscritoDao.delete(aulaCursoInscrito);
    }

    @Override
    protected void deleteInDatabaseByCriteria(int criteria) {
        //
    }

    public void deleteAll() {
        aulaCursoInscritoDao.deleteAll();
    }
}