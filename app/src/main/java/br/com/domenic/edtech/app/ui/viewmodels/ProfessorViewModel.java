package br.com.domenic.edtech.app.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.domenic.edtech.app.models.Aluno;
import br.com.domenic.edtech.app.models.Professor;
import br.com.domenic.edtech.app.models.repositories.ProfessorRepository;
import br.com.domenic.edtech.app.ui.viewmodels.base.BaseViewModel;

public class ProfessorViewModel extends BaseViewModel<Professor> {

    private final ProfessorRepository professorRepository;

    public ProfessorViewModel(@NonNull Application application) {
        super(application);
        professorRepository = new ProfessorRepository(application);
    }

    @Override
    public LiveData<List<Professor>> getAll() {
        return professorRepository.getAll();
    }

    public LiveData<Professor> findByEmailAndSenha(String email, String senha) {
        return professorRepository.findByEmailAndSenha(email, senha);
    }

    @Override
    public void insert(Professor professor, Runnable onCompletion) {
        professorRepository.insert(professor, onCompletion);
    }

    @Override
    public void update(Professor professor) {
        professorRepository.update(professor);
    }

    @Override
    public void delete(Professor professor) {
        professorRepository.delete(professor);
    }

    public LiveData<Professor> findById(int idProfessor) {
        return professorRepository.findById(idProfessor);
    }

    public void deleteAll() {
        professorRepository.deleteAll();
    }
}