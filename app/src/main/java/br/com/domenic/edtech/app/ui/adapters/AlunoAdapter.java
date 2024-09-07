package br.com.domenic.edtech.app.ui.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.domenic.edtech.app.R;
import br.com.domenic.edtech.app.models.Aluno;
import br.com.domenic.edtech.app.ui.adapters.base.BaseAdapter;

public class AlunoAdapter extends BaseAdapter<Aluno, AlunoAdapter.AlunoViewHolder> {

    public AlunoAdapter(Context context, List<Aluno> alunos, OnItemClickListener<Aluno> itemClickListener) {
        super(context, alunos, itemClickListener);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.card_aluno; // Certifique-se de que card_aluno.xml está no diretório correto
    }

    @Override
    protected AlunoViewHolder createViewHolder(View view) {
        return new AlunoViewHolder(view);
    }

    @Override
    protected void bindViewHolder(AlunoViewHolder holder, Aluno aluno) {
        holder.nomeAluno.setText(aluno.getNomeCompleto());
        holder.emailAluno.setText(aluno.getEmail());
        // Se houver uma imagem do aluno, você pode configurá-la aqui também.
        // Exemplo: holder.fotoAluno.setImageResource(...); // Adicione a lógica para carregar a imagem
    }

    @Override
    protected boolean areItemsEqual(Aluno oldItem, Aluno newItem) {
        return oldItem.getIdAluno() == newItem.getIdAluno();
    }

    @Override
    protected boolean areContentsEqual(Aluno oldItem, Aluno newItem) {
        return oldItem.equals(newItem); // Certifique-se de que a classe Aluno possui um método equals adequado
    }

    public static class AlunoViewHolder extends RecyclerView.ViewHolder {
        TextView nomeAluno, emailAluno;
        ImageView fotoAluno;

        public AlunoViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeAluno = itemView.findViewById(R.id.tvNomeAluno);
            emailAluno = itemView.findViewById(R.id.tvEmailAluno);
            fotoAluno = itemView.findViewById(R.id.ivAlunoFoto); // Certifique-se de que o ID está correto no layout card_aluno.xml
        }
    }
}