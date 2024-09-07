package br.com.domenic.edtech.app.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.domenic.edtech.app.models.AlunoCurso;
import br.com.domenic.edtech.app.models.Curso;
import br.com.domenic.edtech.app.models.repositories.AlunoCursoRepository;
import br.com.domenic.edtech.app.ui.viewmodels.base.BaseViewModel;

public class AlunoCursoViewModel extends BaseViewModel<AlunoCurso> {

    private final AlunoCursoRepository alunoCursoRepository;

    public AlunoCursoViewModel(@NonNull Application application) {
        super(application);
        alunoCursoRepository = new AlunoCursoRepository(application);
    }

    @Override
    public LiveData<List<AlunoCurso>> getAll() {
        return alunoCursoRepository.getAll();
    }

    public LiveData<List<AlunoCurso>> getCursosByAluno(int idAluno) {
        return alunoCursoRepository.getCursosByAluno(idAluno);
    }

    @Override
    public void insert(AlunoCurso alunoCurso, Runnable onCompletion) {
        alunoCursoRepository.insert(alunoCurso, onCompletion);
    }

    @Override
    public void update(AlunoCurso alunoCurso) {
        alunoCursoRepository.update(alunoCurso);
    }

    @Override
    public void delete(AlunoCurso alunoCurso) {
        alunoCursoRepository.delete(alunoCurso);
    }

    public LiveData<List<Curso>> getOnlyCursosByAluno(int idAluno) {
        return alunoCursoRepository.getOnlyCursosByAluno(idAluno);
    }

    public void deleteAllCursosByAlunoId(int idAluno, Runnable onCompletion) {
        alunoCursoRepository.deleteByCriteria(idAluno, onCompletion);
    }

    public void deleteAll() {
        alunoCursoRepository.deleteAll();
    }
}