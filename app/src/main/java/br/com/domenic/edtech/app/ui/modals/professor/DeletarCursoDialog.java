package br.com.domenic.edtech.app.ui.modals.professor;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.Curso;

public class DeletarCursoDialog {
    private final Dialog dialog;

    public DeletarCursoDialog(Context context, Curso curso, OnDeletarCursoDialogListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.modal_deletar_curso, null);
        dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.setCancelable(true);

        TextView tvConfirmacaoDeletar = view.findViewById(R.id.tvConfirmacaoDeletar);
        tvConfirmacaoDeletar.setText(String.format("Você realmente deseja excluir o curso '%s'?", curso.getNomeCurso()));

        view.findViewById(R.id.btnConfirmarDeletar).setOnClickListener(v -> {
            if (listener != null) {
                listener.onConfirmarDeletar(curso);
            }
            dialog.dismiss();
        });

        view.findViewById(R.id.btnCancelarDeletar).setOnClickListener(v -> dialog.dismiss());
    }

    public void show() {
        dialog.show();
    }

    public interface OnDeletarCursoDialogListener {
        void onConfirmarDeletar(Curso curso);
    }
}