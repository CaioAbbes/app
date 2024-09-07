package br.com.domenic.edtech.app.ui.modals.professor;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.Curso;

public class CursoOptionProfessorDialog {
    private final Dialog dialog;

    public CursoOptionProfessorDialog(Context context, Curso curso, OnCursoOptionDialogListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.modal_curso_opcoes_professor, null);
        dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.setCancelable(true);

        TextView tvNomeCurso = view.findViewById(R.id.tvNomeCurso);
        TextView tvDescricaoCurso = view.findViewById(R.id.tvDescricaoCurso);
        TextView tvDuracaoCurso = view.findViewById(R.id.tvDuracaoCurso);

        tvNomeCurso.setText(curso.getNomeCurso());
        tvDescricaoCurso.setText(curso.getDescricao());
        tvDuracaoCurso.setText(String.format("Duração: %d horas", curso.getDuracaoHoras()));

        // Ação para editar curso
        view.findViewById(R.id.btnEditarCurso).setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditCurso(curso);
            }
            dialog.dismiss();
        });

        // Ação para deletar curso
        view.findViewById(R.id.btnDeletarCurso).setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteCurso(curso);
            }
            dialog.dismiss();
        });

        // Ação para agendar aula em grupo
        view.findViewById(R.id.btnAgendarAulaEmGrupo).setOnClickListener(v -> {
            if (listener != null) {
                listener.onAgendarAulaEmGrupo(curso);
            }
            dialog.dismiss();
        });

        // Ação para cancelar
        view.findViewById(R.id.btnCancelarCurso).setOnClickListener(v -> dialog.dismiss());
    }

    public void show() {
        dialog.show();
    }

    public interface OnCursoOptionDialogListener {
        void onEditCurso(Curso curso);
        void onDeleteCurso(Curso curso);
        void onAgendarAulaEmGrupo(Curso curso); // Nova ação para agendar aula em grupo
    }
}