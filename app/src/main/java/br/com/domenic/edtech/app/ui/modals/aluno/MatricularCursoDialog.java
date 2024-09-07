package br.com.domenic.edtech.app.ui.modals.aluno;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.domenic.edtech.app.R;

public class MatricularCursoDialog {

    private final Dialog dialog;
    private final Button btnAderir, btnCancelar;
    private final TextView tvNomeCurso, tvDescricaoCurso, tvPrecoCurso;

    public MatricularCursoDialog(Context context, String nomeCurso, String descricaoCurso, double precoCurso) {
        View view = LayoutInflater.from(context).inflate(R.layout.modal_matricula_curso, null);
        dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.setCancelable(true);

        // Inicializando os TextViews
        tvNomeCurso = view.findViewById(R.id.tvCursoModalNome);
        tvDescricaoCurso = view.findViewById(R.id.tvCursoModalDescricao);
        tvPrecoCurso = view.findViewById(R.id.tvCursoModalPreco);

        // Preenche os dados do curso
        tvNomeCurso.setText(nomeCurso);
        tvDescricaoCurso.setText(descricaoCurso);
        tvPrecoCurso.setText(String.format("Preço: R$ %.2f", precoCurso));

        // Botões de ação
        btnAderir = view.findViewById(R.id.btnAderirMatriculaCurso);
        btnCancelar = view.findViewById(R.id.btnCancelarMatricula);

        btnCancelar.setOnClickListener(v -> dialog.dismiss());
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setOnAderirListener(View.OnClickListener listener) {
        btnAderir.setOnClickListener(listener);
    }
}