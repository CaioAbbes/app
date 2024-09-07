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
import br.com.domenic.edtech.app.models.Curso;

public class AvaliarCursoDialog {

    private final TextView textView;
    private final Dialog dialog;
    private final Button btnAvaliar, btnCancelar;
    private final RatingBar ratingBar;
    private final EditText etComentario;

    public AvaliarCursoDialog(Context context, Curso curso) {
        View view = LayoutInflater.from(context).inflate(R.layout.modal_curso_avaliacao, null);
        dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.setCancelable(true);

        textView = view.findViewById(R.id.tvNomeCurso);
        btnAvaliar = view.findViewById(R.id.btnAvaliarCurso);
        btnCancelar = view.findViewById(R.id.btnCancelarAvaliarCurso);
        ratingBar = view.findViewById(R.id.ratingBarAvaliarCurso);
        etComentario = view.findViewById(R.id.etComentario);

        textView.setText(curso.getNomeCurso());

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