package br.com.domenic.edtech.app.models.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.domenic.edtech.app.database.AppDatabase;
import br.com.domenic.edtech.app.models.Aluno;
import br.com.domenic.edtech.app.models.Professor;
import br.com.domenic.edtech.app.database.dao.ProfessorDao;
import br.com.domenic.edtech.app.models.repositories.base.BaseRepository;

public class ProfessorRepository extends BaseRepository<Professor> {

    private final ProfessorDao professorDao;

    public ProfessorRepository(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        professorDao = db.professorDao();
    }

    @Override
    public LiveData<List<Professor>> getAll() {
        return professorDao.getAll();
    }

    public LiveData<Professor> findByEmailAndSenha(String email, String senha) {
        return professorDao.findByEmailAndSenha(email, senha);
    }

    @Override
    protected void insertInDatabase(Professor professor) {
        professorDao.insert(professor);
    }

    @Override
    protected void updateInDatabase(Professor professor) {
        professorDao.update(professor);
    }

    @Override
    protected void deleteInDatabase(Professor professor) {
        professorDao.delete(professor);
    }

    public LiveData<Professor> findById(int idProfessor) {
        return professorDao.findById(idProfessor);
    }

    @Override
    protected void deleteInDatabaseByCriteria(int criteria) {
        //
    }

    public void deleteAll() {
        professorDao.deleteAll();
    }
}