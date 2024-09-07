package br.com.domenic.edtech.app.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.domenic.edtech.app.models.Aluno;
import br.com.domenic.edtech.app.models.repositories.AlunoRepository;
import br.com.domenic.edtech.app.ui.viewmodels.base.BaseViewModel;

public class AlunoViewModel extends BaseViewModel<Aluno> {

    private final AlunoRepository alunoRepository;

    public AlunoViewModel(@NonNull Application application) {
        super(application);
        alunoRepository = new AlunoRepository(application);
    }

    @Override
    public LiveData<List<Aluno>> getAll() {
        return alunoRepository.getAll();
    }

    public LiveData<Aluno> findByEmailAndSenha(String email, String senha) {
        return alunoRepository.findByEmailAndSenha(email, senha);
    }

    @Override
    public void insert(Aluno aluno, Runnable onCompletion) {
        alunoRepository.insert(aluno, onCompletion);
    }

    @Override
    public void update(Aluno aluno) {
        alunoRepository.update(aluno);
    }

    @Override
    public void delete(Aluno aluno) {
        alunoRepository.delete(aluno);
    }

    public LiveData<Aluno> findById(int idAluno) {
        return alunoRepository.findById(idAluno);
    }

    public void deleteAll() {
        alunoRepository.deleteAll();
    }
}