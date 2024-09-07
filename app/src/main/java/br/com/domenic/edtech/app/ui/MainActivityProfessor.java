package br.com.domenic.edtech.app.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.ui.fragments.MeuPerfilFragment;
import br.com.domenic.edtech.app.ui.fragments.professor.HomeProfessorFragment;
import br.com.domenic.edtech.app.ui.fragments.professor.MeusCursosProfessorFragment;
import br.com.domenic.edtech.app.ui.fragments.professor.MinhasAulasProfessorFragment;
import br.com.domenic.edtech.app.utils.AppUtils;


public class MainActivityProfessor extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.initVisuals(this, R.layout.activity_main_professor);

        bottomNavigationView = findViewById(R.id.bottomNavigationProfessor);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeProfessorFragment();
            } else if (itemId == R.id.nav_meus_cursos) {
                selectedFragment = new MeusCursosProfessorFragment();
            } else if (itemId == R.id.nav_minhas_aulas) {
                selectedFragment = new MinhasAulasProfessorFragment();
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
                .replace(R.id.flMainContentProfessor, fragment)
                .commit();
    }
}