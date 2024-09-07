package br.com.domenic.edtech.app.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.domenic.edtech.app.models.Curso;
import br.com.domenic.edtech.app.models.repositories.CursoRepository;
import br.com.domenic.edtech.app.ui.viewmodels.base.BaseViewModel;

public class CursoViewModel extends BaseViewModel<Curso> {

    private final CursoRepository cursoRepository;

    public CursoViewModel(@NonNull Application application) {
        super(application);
        cursoRepository = new CursoRepository(application);
    }

    @Override
    public LiveData<List<Curso>> getAll() {
        return cursoRepository.getAll();
    }

    @Override
    public void insert(Curso curso, Runnable onCompletion) {
        cursoRepository.insert(curso, onCompletion);
    }

    @Override
    public void update(Curso curso) {
        cursoRepository.update(curso);
    }

    @Override
    public void delete(Curso curso) {
        cursoRepository.delete(curso);
    }

    public LiveData<List<Curso>> findByProfessorId(int idProfessor) {
        return cursoRepository.findByProfessorId(idProfessor);
    }

    public void deleteAll() {
        cursoRepository.deleteAll();
    }
}