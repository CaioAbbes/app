package br.com.domenic.edtech.app.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import br.com.domenic.edtech.app.models.Aluno;
import br.com.domenic.edtech.app.models.AlunoCurso;
import java.util.List;
import br.com.domenic.edtech.app.database.dao.base.BaseDao;
import br.com.domenic.edtech.app.models.Curso;

@Dao
public interface AlunoCursoDao extends BaseDao<AlunoCurso> {

    @Query("SELECT * FROM aluno_curso WHERE idAluno = :idAluno")
    LiveData<List<AlunoCurso>> getCursosByAluno(int idAluno);

    @Query("SELECT * FROM aluno_curso WHERE idCurso = :idCurso")
    LiveData<List<AlunoCurso>> getAlunosByCurso(int idCurso);

    @Query("SELECT cursos.* FROM cursos JOIN aluno_curso ON cursos.idCurso = aluno_curso.idCurso WHERE aluno_curso.idAluno = :idAluno")
    LiveData<List<Curso>> getOnlyCursosByAluno(int idAluno);

    @Query("SELECT * FROM aluno_curso")
    LiveData<List<AlunoCurso>> getAll();

    @Query("DELETE FROM aluno_curso WHERE idAluno = :idAluno")
    void deleteAllByAlunoId(int idAluno);

    @Query("DELETE FROM aluno_curso")
    void deleteAll();

    @Query("SELECT * FROM aluno_curso")
    List<AlunoCurso> getAllDirect();
}