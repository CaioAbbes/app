package br.com.domenic.edtech.app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import br.com.domenic.edtech.app.models.Curso;
import br.com.domenic.edtech.app.models.CursoAvaliacao;
import java.util.List;
import br.com.domenic.edtech.app.database.dao.base.BaseDao;

@Dao
public interface CursoAvaliacaoDao extends BaseDao<CursoAvaliacao> {

    @Query("SELECT * FROM curso_avaliacao WHERE idCurso = :idCurso")
    LiveData<List<CursoAvaliacao>> getAvaliacoesByCursoId(int idCurso);

    @Query("SELECT * FROM curso_avaliacao")
    LiveData<List<CursoAvaliacao>> getAll();

    @Query("DELETE FROM curso_avaliacao")
    void deleteAll();

    @Query("SELECT * FROM curso_avaliacao")
    List<CursoAvaliacao> getAllDirect();
}