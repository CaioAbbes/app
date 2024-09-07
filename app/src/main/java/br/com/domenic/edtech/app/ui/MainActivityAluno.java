package br.com.domenic.edtech.app.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.ui.fragments.MeuPerfilFragment;
import br.com.domenic.edtech.app.ui.fragments.aluno.HomeAlunoFragment;

import br.com.domenic.edtech.app.ui.fragments.aluno.MeusCursosAlunoFragment;
import br.com.domenic.edtech.app.ui.fragments.aluno.MinhasAulasAlunoFragment;
import br.com.domenic.edtech.app.utils.AppUtils;

public class MainActivityAluno extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.initVisuals(this, R.layout.activity_main_aluno);

        bottomNavigationView = findViewById(R.id.bottomNavigationAluno);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeAlunoFragment();
            } else if (itemId == R.id.nav_meus_cursos) {
                selectedFragment = new MeusCursosAlunoFragment();
            } else if (itemId == R.id.nav_minhas_aulas) {
                selectedFragment = new MinhasAulasAlunoFragment();
            } else if (itemId == R.id.nav_meu_perfil) {
                selectedFragment = new MeuPerfilFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;
        });

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flMainContentAluno, fragment)
                .commit();
    }
}