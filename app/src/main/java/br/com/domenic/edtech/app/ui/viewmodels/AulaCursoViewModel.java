package br.com.domenic.edtech.app.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.domenic.edtech.app.models.AulaCurso;
import br.com.domenic.edtech.app.models.repositories.AulaCursoRepository;
import br.com.domenic.edtech.app.ui.viewmodels.base.BaseViewModel;

public class AulaCursoViewModel extends BaseViewModel<AulaCurso> {

    private final AulaCursoRepository aulaCursoRepository;

    public AulaCursoViewModel(@NonNull Application application) {
        super(application);
        aulaCursoRepository = new AulaCursoRepository(application);
    }

    @Override
    public LiveData<List<AulaCurso>> getAll() {
        return aulaCursoRepository.getAll();
    }

    @Override
    public void insert(AulaCurso aulaCurso, Runnable onCompletion) {
        aulaCursoRepository.insert(aulaCurso, onCompletion);
    }

    @Override
    public void update(AulaCurso aulaCurso) {
        aulaCursoRepository.update(aulaCurso);
    }

    @Override
    public void delete(AulaCurso aulaCurso) {
        aulaCursoRepository.delete(aulaCurso);
    }

    public LiveData<List<AulaCurso>> findAulasByCursoId(int idCurso) {
        return aulaCursoRepository.findAulasByCursoId(idCurso);
    }

    public LiveData<List<AulaCurso>> findAulasByProfessorId(int idProfessor) {
        return aulaCursoRepository.findAulasByProfessorId(idProfessor);
    }

    public LiveData<List<AulaCurso>> findAulasByAlunoId(int idAluno) {
        return aulaCursoRepository.findAulasByAlunoId(idAluno);
    }

    public void deleteAll() {
        aulaCursoRepository.deleteAll();
    }
}