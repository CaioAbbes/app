package br.com.domenic.edtech.app.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import br.com.domenic.edtech.app.models.ProfessorAvaliacao;
import br.com.domenic.edtech.app.models.repositories.ProfessorAvaliacaoRepository;
import br.com.domenic.edtech.app.ui.viewmodels.base.BaseViewModel;

public class ProfessorAvaliacaoViewModel extends BaseViewModel<ProfessorAvaliacao> {

    private final ProfessorAvaliacaoRepository professorAvaliacaoRepository;

    public ProfessorAvaliacaoViewModel(@NonNull Application application) {
        super(application);
        professorAvaliacaoRepository = new ProfessorAvaliacaoRepository(application);
    }

    @Override
    public LiveData<List<ProfessorAvaliacao>> getAll() {
        return professorAvaliacaoRepository.getAll();
    }

    @Override
    public void insert(ProfessorAvaliacao professorAvaliacao, Runnable onCompletion) {
        professorAvaliacaoRepository.insert(professorAvaliacao, onCompletion);
    }

    @Override
    public void update(ProfessorAvaliacao professorAvaliacao) {
        professorAvaliacaoRepository.update(professorAvaliacao);
    }

    @Override
    public void delete(ProfessorAvaliacao professorAvaliacao) {
        professorAvaliacaoRepository.delete(professorAvaliacao);
    }

    public LiveData<Double> getRating() {
        return Transformations.map(getAll(), avaliacoes -> {
            if (avaliacoes == null || avaliacoes.isEmpty()) {
                return 0.0;
            }
            double sum = 0;
            for (ProfessorAvaliacao avaliacao : avaliacoes) {
                sum += avaliacao.getNota();
            }
            return sum / avaliacoes.size();
        });
    }

    public void deleteAll() {
        professorAvaliacaoRepository.deleteAll();
    }
}