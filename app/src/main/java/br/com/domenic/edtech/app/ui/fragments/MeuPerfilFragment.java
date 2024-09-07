package br.com.domenic.edtech.app.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.button.MaterialButton;
import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.Aluno;
import br.com.domenic.edtech.app.models.Professor;
import br.com.domenic.edtech.app.ui.LoginActivity;
import br.com.domenic.edtech.app.ui.viewmodels.AlunoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.ProfessorViewModel;
import br.com.domenic.edtech.app.utils.SessionManager;

public class MeuPerfilFragment extends Fragment {

    private AlunoViewModel alunoViewModel;
    private ProfessorViewModel professorViewModel;
    private TextView tvNomeUsuario;
    private TextView tvEmailUsuario;
    private MaterialButton btnLogout; // Referência para o botão de logout
    private SessionManager sessionManager;
    private String userType; // Tipo de usuário: aluno ou professor

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meu_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvNomeUsuario = view.findViewById(R.id.tvNomeUsuario);
        tvEmailUsuario = view.findViewById(R.id.tvEmailUsuario);
        btnLogout = view.findViewById(R.id.btnLogout); // Inicializando o botão de logout

        // Inicializando o SessionManager
        sessionManager = new SessionManager(requireContext());
        userType = sessionManager.getUserType(); // Recupera o tipo de usuário da sessão
        int userId = sessionManager.getUserId();

        alunoViewModel = new ViewModelProvider(this).get(AlunoViewModel.class);
        professorViewModel = new ViewModelProvider(this).get(ProfessorViewModel.class);

        if ("aluno".equals(userType)) {
            alunoViewModel.findById(userId).observe(getViewLifecycleOwner(), new Observer<Aluno>() {
                @Override
                public void onChanged(@Nullable Aluno aluno) {
                    if (aluno != null) {
                        tvNomeUsuario.setText(aluno.getNomeCompleto());
                        tvEmailUsuario.setText(aluno.getEmail());
                    }
                }
            });
        } else if ("professor".equals(userType)) {
            professorViewModel.findById(userId).observe(getViewLifecycleOwner(), new Observer<Professor>() {
                @Override
                public void onChanged(@Nullable Professor professor) {
                    if (professor != null) {
                        tvNomeUsuario.setText(professor.getNomeCompleto());
                        tvEmailUsuario.setText(professor.getEmail());
                    }
                }
            });
        }

        // Ação de logout ao clicar no botão
        btnLogout.setOnClickListener(v -> {
            sessionManager.clearSession(); // Limpar a sessão
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpa a pilha de atividades
            startActivity(intent);
        });
    }
}