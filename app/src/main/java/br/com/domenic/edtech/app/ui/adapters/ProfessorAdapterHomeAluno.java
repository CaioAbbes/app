package br.com.domenic.edtech.app.ui.adapters;

import android.content.Context;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.Professor;
import br.com.domenic.edtech.app.ui.adapters.base.BaseAdapter;

public class ProfessorAdapterHomeAluno extends BaseAdapter<Professor, ProfessorAdapterHomeAluno.ProfessorViewHolder> {

    public ProfessorAdapterHomeAluno(Context context, List<Professor> professorList, OnItemClickListener<Professor> itemClickListener) {
        super(context, professorList, itemClickListener);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.card_professor_com_nota_aluno;
    }

    @Override
    protected ProfessorViewHolder createViewHolder(View view) {
        return new ProfessorViewHolder(view);
    }

    @Override
    protected void bindViewHolder(ProfessorViewHolder holder, Professor professor) {
        holder.tvNomeProfessor.setText(professor.getNomeCompleto());
        holder.tvEspecialidade.setText(professor.getEspecialidade());
//        holder.tvEmail.setText(professor.getEmail());
        holder.ratingBar.setRating(calculateProfessorRating(professor.getIdProfessor()));
    }

    @Override
    protected boolean areItemsEqual(Professor oldItem, Professor newItem) {
        return oldItem.getIdProfessor() == newItem.getIdProfessor();
    }

    @Override
    protected boolean areContentsEqual(Professor oldItem, Professor newItem) {
        return oldItem.equals(newItem);
    }

    private float calculateProfessorRating(int professorId) {
        // Lógica para calcular a média das avaliações do professor
        return 4.5f; // Exemplo de nota fixa
    }

    public static class ProfessorViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeProfessor, tvEspecialidade, tvEmail;
        RatingBar ratingBar;

        public ProfessorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeProfessor = itemView.findViewById(R.id.tvProfessorNome);
            tvEspecialidade = itemView.findViewById(R.id.tvProfessorEspecialidade);
//            tvEmail = itemView.findViewById(R.id.tvProfessorEmail);
            ratingBar = itemView.findViewById(R.id.ratingProfessor);
        }
    }
}