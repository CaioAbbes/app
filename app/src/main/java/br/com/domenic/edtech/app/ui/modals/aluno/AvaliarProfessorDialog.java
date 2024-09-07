package br.com.domenic.edtech.app.ui.modals.aluno;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import br.com.domenic.edtech.app.R;

public class AvaliarProfessorDialog {

    private final TextView textView;
    private final Dialog dialog;
    private final Button btnAvaliar, btnCancelar;
    private final RatingBar ratingBar;
    private final EditText etComentario;

    public AvaliarProfessorDialog(Context context, String professorNome) {
        View view = LayoutInflater.from(context).inflate(R.layout.modal_professor_avaliacao, null);
        dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.setCancelable(true);

        textView = view.findViewById(R.id.tvNomeProfessor);
        btnAvaliar = view.findViewById(R.id.btnAvaliarProfessor);
        btnCancelar = view.findViewById(R.id.btnCancelarAvaliarProfessor);
        ratingBar = view.findViewById(R.id.ratingBarAvaliarProfessor);
        etComentario = view.findViewById(R.id.etComentario);

        textView.setText(professorNome);

        btnCancelar.setOnClickListener(v -> dialog.dismiss());
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setOnAvaliarListener(View.OnClickListener listener) {
        btnAvaliar.setOnClickListener(listener);
    }

    public float getNota() {
        return ratingBar.getRating();
    }

    public String getComentario() {
        return etComentario.getText().toString();
    }
}