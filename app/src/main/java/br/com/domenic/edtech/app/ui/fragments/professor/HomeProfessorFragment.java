package br.com.domenic.edtech.app.ui.fragments.professor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.ui.viewmodels.ProfessorAvaliacaoViewModel;

public class HomeProfessorFragment extends Fragment {

    private ProfessorAvaliacaoViewModel professorAvaliacaoViewModel;
    private RatingBar ratingProfessor;
    private TextView tvNomeProfessor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_professor, container, false);

        // Inicializando os elementos do layout
        ratingProfessor = view.findViewById(R.id.ratingProfessor);
        tvNomeProfessor = view.findViewById(R.id.tvProfessorNome);

        // Inicializando o ViewModel
        professorAvaliacaoViewModel = new ViewModelProvider(this).get(ProfessorAvaliacaoViewModel.class);

        // Configurando a observação do LiveData para a média das avaliações
        professorAvaliacaoViewModel.getRating().observe(getViewLifecycleOwner(), averageRating -> {
            // Atualiza o RatingBar com a média das avaliações
            ratingProfessor.setRating(averageRating.floatValue());
        });

        // Configurando o nome do professor
        // Isso pode ser obtido de outra forma ou passado pelo bundle, se necessário
        String nomeProfessor = "Nome do Professor";  // Exemplo fixo, altere conforme necessário
        tvNomeProfessor.setText(nomeProfessor);

        return view;
    }
}