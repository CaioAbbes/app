package br.com.domenic.edtech.app.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import br.com.domenic.edtech.app.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this);

        String userType = sessionManager.getUserType();

        if (userType == null) {
            redirectToLogin();
        } else if ("aluno".equals(userType)) {
            redirectToMainActivityAluno();
        } else if ("professor".equals(userType)) {
            redirectToMainActivityProfessor();
        }
    }

    private void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void redirectToMainActivityAluno() {
        Intent intent = new Intent(this, MainActivityAluno.class);
        startActivity(intent);
        finish();
    }

    private void redirectToMainActivityProfessor() {
        Intent intent = new Intent(this, MainActivityProfessor.class);
        startActivity(intent);
        finish();
    }
}