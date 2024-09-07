package br.com.domenic.edtech.app.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.domenic.edtech.app.models.AulaCurso;
import br.com.domenic.edtech.app.models.AulaCursoInscrito;
import br.com.domenic.edtech.app.models.repositories.AulaCursoInscritoRepository;
import br.com.domenic.edtech.app.models.repositories.AulaCursoRepository;
import br.com.domenic.edtech.app.ui.viewmodels.base.BaseViewModel;

public class AulaCursoInscritoViewModel extends BaseViewModel<AulaCursoInscrito> {

    private final AulaCursoInscritoRepository aulaCursoInscritoRepository;

    public AulaCursoInscritoViewModel(@NonNull Application application) {
        super(application);
        aulaCursoInscritoRepository = new AulaCursoInscritoRepository(application);
    }

    @Override
    public LiveData<List<AulaCursoInscrito>> getAll() {
        return aulaCursoInscritoRepository.getAll();
    }

    @Override
    public void insert(AulaCursoInscrito aulaCurso, Runnable onCompletion) {
        aulaCursoInscritoRepository.insert(aulaCurso, onCompletion);
    }

    @Override
    public void update(AulaCursoInscrito aulaCurso) {
        aulaCursoInscritoRepository.update(aulaCurso);
    }

    @Override
    public void delete(AulaCursoInscrito aulaCurso) {
        aulaCursoInscritoRepository.delete(aulaCurso);
    }

    public void deleteAll() {
        aulaCursoInscritoRepository.deleteAll();
    }
}