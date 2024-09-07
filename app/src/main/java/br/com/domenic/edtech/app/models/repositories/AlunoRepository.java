package br.com.domenic.edtech.app.models.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.domenic.edtech.app.database.AppDatabase;
import br.com.domenic.edtech.app.models.Aluno;
import br.com.domenic.edtech.app.database.dao.AlunoDao;
import br.com.domenic.edtech.app.models.repositories.base.BaseRepository;

public class AlunoRepository extends BaseRepository<Aluno> {

    private final AlunoDao alunoDao;

    public AlunoRepository(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        alunoDao = db.alunoDao();
    }

    @Override
    public LiveData<List<Aluno>> getAll() {
        return alunoDao.getAll();
    }

    public LiveData<Aluno> findByEmailAndSenha(String email, String senha) {
        return alunoDao.findByEmailAndSenha(email, senha);
    }

    @Override
    protected void insertInDatabase(Aluno aluno) {
        alunoDao.insert(aluno);
    }

    @Override
    protected void updateInDatabase(Aluno aluno) {
        alunoDao.update(aluno);
    }

    @Override
    protected void deleteInDatabase(Aluno aluno) {
        alunoDao.delete(aluno);
    }

    public LiveData<Aluno> findById(int idAluno) {
        return alunoDao.findById(idAluno);
    }

    @Override
    protected void deleteInDatabaseByCriteria(int criteria) {
        //
    }

    public void deleteAll() {
        alunoDao.deleteAll();
    }
}