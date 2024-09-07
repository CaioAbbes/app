package br.com.domenic.edtech.app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import br.com.domenic.edtech.app.models.Professor;
import java.util.List;
import br.com.domenic.edtech.app.database.dao.base.BaseDao;

@Dao
public interface ProfessorDao extends BaseDao<Professor> {

    @Query("SELECT * FROM professores WHERE idProfessor = :idProfessor")
    LiveData<Professor> findById(int idProfessor);

    @Query("SELECT * FROM professores WHERE email = :email AND senha = :senha LIMIT 1")
    LiveData<Professor> findByEmailAndSenha(String email, String senha);

    @Query("SELECT * FROM professores")
    LiveData<List<Professor>> getAll();

    @Query("DELETE FROM professores")
    void deleteAll();

    @Query("SELECT * FROM professores")
    List<Professor> getAllDirect();
}