package br.com.domenic.edtech.app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import br.com.domenic.edtech.app.models.Curso;
import java.util.List;
import br.com.domenic.edtech.app.database.dao.base.BaseDao;
import br.com.domenic.edtech.app.models.ProfessorAvaliacao;

@Dao
public interface CursoDao extends BaseDao<Curso> {

    @Query("SELECT * FROM cursos WHERE idProfessor = :idProfessor")
    LiveData<List<Curso>> findByProfessorId(int idProfessor);

    @Query("SELECT * FROM cursos")
    LiveData<List<Curso>> getAll();

    @Query("DELETE FROM cursos")
    void deleteAll();

    @Query("SELECT * FROM cursos")
    List<Curso> getAllDirect();
}