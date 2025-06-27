package komeya.gabryel.minhacompra.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import komeya.gabryel.minhacompra.CadastroActivity;
import komeya.gabryel.minhacompra.EditarActivity;
import komeya.gabryel.minhacompra.HomeActivity;
import komeya.gabryel.minhacompra.R;
import komeya.gabryel.minhacompra.dao.Conexao;
import komeya.gabryel.minhacompra.model.Produto;


public class HolderProduto extends RecyclerView.ViewHolder {

    public int id_produto;
    public TextView nome_produto;
    public TextView quantidade;
    public TextView prioridade;
    private Context contexto;
    public Produto produto;


    public HolderProduto(@NonNull View itemView, Context contexto) {

        super(itemView);
        nome_produto = itemView.findViewById(R.id.nome_produto);
        quantidade = itemView.findViewById(R.id.quantidade);
        prioridade = itemView.findViewById(R.id.prioridade);
        this.contexto = contexto;

        itemView.findViewById(R.id.btn_excluir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Deletando da base de dados
                Conexao db = Conexao.getInstancia(itemView.getContext());
                db.produtoRepository().delete(id_produto);

                Intent intent = new Intent(contexto.getApplicationContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                contexto.startActivity(intent);
            }
        });


        itemView.findViewById(R.id.btn_editar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto.getApplicationContext(), EditarActivity.class);

                //Enviando dados do produto para editar
                intent.putExtra("nome_produto",produto.getNome_produto());
                intent.putExtra("quantidade_produto",String.valueOf(produto.getQuantidade_produto()));
                intent.putExtra("prioridade_produto",produto.getPrioridade_produto());

                contexto.startActivity(intent);
            }
        });
    }


}
