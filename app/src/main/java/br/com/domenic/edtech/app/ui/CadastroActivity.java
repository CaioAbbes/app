package br.com.domenic.edtech.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.Aluno;
import br.com.domenic.edtech.app.models.Professor;
import br.com.domenic.edtech.app.ui.viewmodels.AlunoViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.ProfessorViewModel;
import br.com.domenic.edtech.app.ui.viewmodels.factories.base.BaseFactory;
import br.com.domenic.edtech.app.utils.AppUtils;
import br.com.domenic.edtech.app.utils.geradores.GeradorDados;

public class CadastroActivity extends AppCompatActivity {

    private TextInputLayout nomeInput, emailInput, senhaInput, confirmarSenhaInput, cpfInput, celularInput, especialidadeInput;
    private Spinner spinnerEspecialidade;
    private RadioGroup perfilGroup;
    private RadioButton perfilAluno, perfilProfessor;
    private MaterialButton cadastrarButton;
    private AlunoViewModel alunoViewModel;
    private ProfessorViewModel professorViewModel;
    private String especialidadeSelecionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.initVisuals(this, R.layout.activity_cadastro);

        initToolbar();
        initViews();
        setupViewModels();
        setupListeners();
    }

    private void initToolbar() {
        // Inicializa e configura a Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Habilita o ícone de voltar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);
        }
    }

    private void initViews() {
        nomeInput = findViewById(R.id.tiNomeCompleto);
        emailInput = findViewById(R.id.tiEmailCadastro);
        senhaInput = findViewById(R.id.tiSenhaCadastro);
        confirmarSenhaInput = findViewById(R.id.tiConfirmarSenha);
        cpfInput = findViewById(R.id.tiCPF);
        celularInput = findViewById(R.id.tiCelular);
        especialidadeInput = findViewById(R.id.tiEspecialidade);
        spinnerEspecialidade = findViewById(R.id.spinnerEspecialidade);
        perfilGroup = findViewById(R.id.rgPerfil);
        perfilAluno = findViewById(R.id.rbAluno);
        perfilProfessor = findViewById(R.id.rbProfessor);
        cadastrarButton = findViewById(R.id.btnCadastrar);

        // Geração de dados aleatórios
        nomeInput.getEditText().setText(GeradorDados.gerarNomeAleatorio());
        cpfInput.getEditText().setText(GeradorDados.gerarCpfAleatorio());
        celularInput.getEditText().setText(GeradorDados.gerarCelularAleatorio());

        // Spinner Listener
        spinnerEspecialidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                especialidadeSelecionada = parent.getItemAtPosition(position).toString(); // Armazena a especialidade selecionada
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                especialidadeSelecionada = ""; // Caso nenhuma especialidade seja selecionada
            }
        });
    }

    private void setupViewModels() {
        alunoViewModel = new ViewModelProvider(this, new BaseFactory<>(getApplication(), AlunoViewModel.class)).get(AlunoViewModel.class);
        professorViewModel = new ViewModelProvider(this, new BaseFactory<>(getApplication(), ProfessorViewModel.class)).get(ProfessorViewModel.class);
    }

    private void setupListeners() {
        perfilGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbProfessor) {
                especialidadeInput.setVisibility(View.VISIBLE);
            } else {
                especialidadeInput.setVisibility(View.GONE);
            }
        });

        cadastrarButton.setOnClickListener(v -> cadastrarUsuario());
    }

    private void cadastrarUsuario() {
        String nome = nomeInput.getEditText().getText().toString();
        String email = emailInput.getEditText().getText().toString();
        String senha = senhaInput.getEditText().getText().toString();
        String confirmarSenha = confirmarSenhaInput.getEditText().getText().toString();
        String cpf = cpfInput.getEditText().getText().toString();
        String celular = celularInput.getEditText().getText().toString();
        int perfilSelecionado = perfilGroup.getCheckedRadioButtonId();

        if (isInputValid(nome, email, senha, confirmarSenha, cpf, celular)) {
            if (perfilSelecionado == R.id.rbAluno) {
                Aluno novoAluno = new Aluno();
                novoAluno.setNomeCompleto(nome);
                novoAluno.setEmail(email);
                novoAluno.setSenha(senha);
                novoAluno.setCpf(cpf);
                novoAluno.setCelular(celular);

                alunoViewModel.insert(novoAluno, () -> {
                    runOnUiThread(() -> {
                        showToast("Cadastro realizado com sucesso!");
                        irParaLogin();
                    });
                });

            } else if (perfilSelecionado == R.id.rbProfessor) {
                // Verifica se uma especialidade válida foi selecionada
                if (especialidadeSelecionada.isEmpty() || especialidadeSelecionada.equals("Selecione sua especialidade:")) {
                    showToast("Por favor, selecione uma especialidade válida.");
                    return;
                }

                Professor novoProfessor = new Professor();
                novoProfessor.setNomeCompleto(nome);
                novoProfessor.setEmail(email);
                novoProfessor.setSenha(senha);
                novoProfessor.setCpf(cpf);
                novoProfessor.setCelular(celular);
                novoProfessor.setEspecialidade(especialidadeSelecionada);

                professorViewModel.insert(novoProfessor, () -> {
                    runOnUiThread(() -> {
                        showToast("Cadastro realizado com sucesso!");
                        irParaLogin();
                    });
                });
            }
        }
    }

    private boolean isInputValid(String nome, String email, String senha, String confirmarSenha, String cpf, String celular) {
        // Verificação se algum campo obrigatório está vazio
        if (nome.isEmpty()) {
            showToast("Por favor, preencha o nome.");
            return false;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Por favor, insira um e-mail válido.");
            return false;
        }
        if (senha.isEmpty()) {
            showToast("Por favor, preencha a senha.");
            return false;
        }
        if (confirmarSenha.isEmpty()) {
            showToast("Por favor, confirme a senha.");
            return false;
        }
        if (!senha.equals(confirmarSenha)) {
            showToast("As senhas não coincidem.");
            return false;
        }

        if (cpf.isEmpty()) {
            showToast("Por favor, preencha o cpf.");
            return false;
        }

        if (celular.isEmpty()) {
            showToast("Por favor, preencha o celular.");
            return false;
        }

        if (cpf.isEmpty() || cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            showToast("Por favor, insira um CPF válido (11 dígitos numéricos).");
            return false;
        }
        if (celular.isEmpty() || celular.length() != 11 || !celular.matches("\\d{11}")) {
            showToast("Por favor, insira um número de celular válido (com DDD, 11 dígitos).");
            return false;
        }

        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void irParaLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Utilize o dispatcher para lidar com o botão de voltar
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}