package br.com.domenic.edtech.app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import java.util.List;

import br.com.domenic.edtech.app.models.Professor;
import br.com.domenic.edtech.app.models.ProfessorAvaliacao;
import br.com.domenic.edtech.app.database.dao.base.BaseDao;

@Dao
public interface ProfessorAvaliacaoDao extends BaseDao<ProfessorAvaliacao> {

    // Método para obter avaliações de um professor específico
    @Query("SELECT * FROM professor_avaliacao WHERE idProfessor = :idProfessor")
    LiveData<List<ProfessorAvaliacao>> getAvaliacoesByProfessor(int idProfessor);

    @Query("SELECT * FROM professor_avaliacao")
    LiveData<List<ProfessorAvaliacao>> getAll();

    @Query("DELETE FROM professor_avaliacao")
    void deleteAll();

    @Query("SELECT * FROM professor_avaliacao")
    List<ProfessorAvaliacao> getAllDirect();
}