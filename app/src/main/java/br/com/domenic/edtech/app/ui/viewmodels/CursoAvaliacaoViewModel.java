package br.com.domenic.edtech.app.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.domenic.edtech.app.models.CursoAvaliacao;
import br.com.domenic.edtech.app.models.Professor;
import br.com.domenic.edtech.app.models.repositories.CursoAvaliacaoRepository;
import br.com.domenic.edtech.app.ui.viewmodels.base.BaseViewModel;

public class CursoAvaliacaoViewModel extends BaseViewModel<CursoAvaliacao> {

    private final CursoAvaliacaoRepository cursoAvaliacaoRepository;

    public CursoAvaliacaoViewModel(@NonNull Application application) {
        super(application);
        cursoAvaliacaoRepository = new CursoAvaliacaoRepository(application);
    }

    @Override
    public LiveData<List<CursoAvaliacao>> getAll() {
        return cursoAvaliacaoRepository.getAll();
    }

    @Override
    public void insert(CursoAvaliacao cursoAvaliacao, Runnable onCompletion) {
        cursoAvaliacaoRepository.insert(cursoAvaliacao, onCompletion);
    }

    @Override
    public void update(CursoAvaliacao cursoAvaliacao) {
        cursoAvaliacaoRepository.update(cursoAvaliacao);
    }

    @Override
    public void delete(CursoAvaliacao cursoAvaliacao) {
        cursoAvaliacaoRepository.delete(cursoAvaliacao);
    }

    public LiveData<List<CursoAvaliacao>> findAvaliacoesByCursoId(int idProfessor) {
        return cursoAvaliacaoRepository.findAvaliacoesByCursoId(idProfessor);
    }

    public void deleteAll() {
        cursoAvaliacaoRepository.deleteAll();
    }
}