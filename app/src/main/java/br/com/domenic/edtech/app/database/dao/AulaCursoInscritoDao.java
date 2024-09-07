package br.com.domenic.edtech.app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import br.com.domenic.edtech.app.database.dao.base.BaseDao;
import br.com.domenic.edtech.app.models.AulaCurso;
import br.com.domenic.edtech.app.models.AulaCursoInscrito;

@Dao
public interface AulaCursoInscritoDao extends BaseDao<AulaCursoInscrito> {
    @Query("SELECT * FROM aula_curso_inscrito")
    LiveData<List<AulaCursoInscrito>> getAll();

    @Query("SELECT * FROM aula_curso_inscrito WHERE idAulaCurso = :idAulaCurso")
    List<AulaCursoInscrito> findInscritosPorAula(int idAulaCurso);

    @Query("SELECT * FROM aula_curso_inscrito WHERE idAluno = :idAluno")
    List<AulaCursoInscrito> findAulaCursoByAlunoId(int idAluno);

    @Query("DELETE FROM aula_curso_inscrito")
    void deleteAll();

    @Query("SELECT * FROM aula_curso_inscrito")
    List<AulaCursoInscrito> getAllDirect();
}