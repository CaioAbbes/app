package br.com.domenic.edtech.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.ui.viewmodels.AlunoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.ProfessorViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.factories.base.BaseFactory;
import br.com.domenic.edtech.app.utils.AppUtils;
import br.com.domenic.edtech.app.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout emailInput, senhaInput;
    private MaterialButton loginButton;
    private TextView cadastroButton, tvTitulo;
    private AlunoViewModel alunoViewModel;
    private ProfessorViewModel professorViewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.initVisuals(this, R.layout.activity_login);

        initViews();
        setupViewModels();
        setupListeners();
    }

    private void initViews() {
        emailInput = findViewById(R.id.tiEmail);
        senhaInput = findViewById(R.id.tiSenha);
        loginButton = findViewById(R.id.btnLogin);
        cadastroButton = findViewById(R.id.tvSubtitleNaoPossuiCadastro);
        tvTitulo = findViewById(R.id.tvTitulo);
    }

    private void setupViewModels() {
        alunoViewModel = new ViewModelProvider(this, new BaseFactory<>(getApplication(), AlunoViewModel.class)).get(AlunoViewModel.class);
        professorViewModel = new ViewModelProvider(this, new BaseFactory<>(getApplication(), ProfessorViewModel.class)).get(ProfessorViewModel.class);
        sessionManager = new SessionManager(this);
    }

    private void setupListeners() {
        loginButton.setOnClickListener(v -> validarCredenciais());
        cadastroButton.setOnClickListener(v -> irParaCadastro());
        tvTitulo.setOnClickListener(v -> startGeraMassa());
    }

    private void startGeraMassa() {
        Intent intent = new Intent(LoginActivity.this, GerarMassaActivity.class);
        startActivity(intent);
        finish();
    }

    private void validarCredenciais() {
        String email = emailInput.getEditText().getText().toString();
        String senha = senhaInput.getEditText().getText().toString();

        if (isInputValid(email, senha)) {
            alunoViewModel.findByEmailAndSenha(email, senha).observe(this, aluno -> {
                if (aluno != null) {
                    sessionManager.saveAlunoSession(aluno);
                    showToast("Bem-vindo, " + aluno.getNomeCompleto());
                    irParaMainActivityAluno();
                } else {
                    professorViewModel.findByEmailAndSenha(email, senha).observe(this, professor -> {
                        if (professor != null) {
                            sessionManager.saveProfessorSession(professor);
                            showToast("Bem-vindo, " + professor.getNomeCompleto());
                            irParaMainActivityProfessor();
                        } else {
                            showToast("Credenciais inválidas");
                        }
                    });
                }
            });
        }
    }

    private boolean isInputValid(String email, String senha) {
        if (email.isEmpty() || senha.isEmpty()) {
            showToast("Por favor, preencha todos os campos");
            return false;
        }
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void irParaMainActivityAluno() {
        Intent intent = new Intent(this, MainActivityAluno.class);
        startActivity(intent);
        finish();
    }

    private void irParaMainActivityProfessor() {
        Intent intent = new Intent(this, MainActivityProfessor.class);
        startActivity(intent);
        finish();
    }

    private void irParaCadastro() {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }
}