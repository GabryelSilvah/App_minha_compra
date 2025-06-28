package komeya.gabryel.minhacompra.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.TextView;
import android.widget.Toast;

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


                //Alert de exclusão
                AlertDialog.Builder alertExclusao = new AlertDialog.Builder(contexto);
                alertExclusao.setTitle("Confirmar");
                alertExclusao.setIcon(R.drawable.delete);
                alertExclusao.setMessage("Deseja mesmo excluir esse produto?");
                alertExclusao.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Deletando da base de dados
                        Conexao db = Conexao.getInstancia(itemView.getContext());
                        db.produtoRepository().delete(id_produto);

                        Intent intent = new Intent(contexto.getApplicationContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        contexto.startActivity(intent);
                    }
                });

                alertExclusao.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(contexto, "Exclusão cancelada", Toast.LENGTH_LONG).show();
                    }
                });

                alertExclusao.show();
            }
        });


        itemView.findViewById(R.id.btn_editar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto.getApplicationContext(), EditarActivity.class);

                //Enviando dados do produto para editar
                intent.putExtra("id_produto", produto.getId_produto());
                intent.putExtra("nome_produto", produto.getNome_produto());
                intent.putExtra("quantidade_produto", String.valueOf(produto.getQuantidade_produto()));
                intent.putExtra("prioridade_produto", produto.getPrioridade_produto());

                contexto.startActivity(intent);
            }
        });

    }


}
