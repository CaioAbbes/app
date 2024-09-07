package br.com.domenic.edtech.app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import br.com.domenic.edtech.app.database.dao.base.BaseDao;
import br.com.domenic.edtech.app.models.AulaCurso;

@Dao
public interface AulaCursoDao extends BaseDao<AulaCurso> {
    @Query("SELECT * FROM aula_curso")
    LiveData<List<AulaCurso>> getAll();

    @Query("SELECT * FROM aula_curso WHERE idCurso = :idCurso")
    LiveData<List<AulaCurso>> findAulasByCursoId(int idCurso);

    @Query("SELECT * FROM aula_curso WHERE idProfessor = :idProfessor")
    LiveData<List<AulaCurso>> findAulasByProfessorId(int idProfessor);

    @Query("SELECT aula_curso.* FROM aula_curso JOIN aula_curso_inscrito ON aula_curso.idAulaCurso = aula_curso_inscrito.idAulaCurso WHERE aula_curso_inscrito.idAluno = :idAluno")
    LiveData<List<AulaCurso>> findAulasByAlunoId(int idAluno);

    @Query("DELETE FROM aula_curso")
    void deleteAll();

    @Query("SELECT * FROM aula_curso")
    List<AulaCurso> getAllDirect();
}