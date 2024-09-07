package br.com.domenic.edtech.app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import br.com.domenic.edtech.app.database.dao.base.BaseDao;
import br.com.domenic.edtech.app.models.Aluno;

@Dao
public interface AlunoDao extends BaseDao<Aluno> {

    @Query("SELECT * FROM alunos WHERE idAluno = :idAluno")
    LiveData<Aluno> findById(int idAluno);

    @Query("SELECT * FROM alunos WHERE email = :email AND senha = :senha LIMIT 1")
    LiveData<Aluno> findByEmailAndSenha(String email, String senha);

    @Query("SELECT * FROM alunos")
    LiveData<List<Aluno>> getAll();

    @Query("DELETE FROM alunos")
    void deleteAll();

    @Query("SELECT * FROM alunos")
    List<Aluno> getAllDirect();
}