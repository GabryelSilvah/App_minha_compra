package komeya.gabryel.minhacompra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import komeya.gabryel.minhacompra.R;
import komeya.gabryel.minhacompra.model.Produto;


public class AdapterProduto extends RecyclerView.Adapter<HolderProduto> {

    private List<Produto> lista_produtos;
    private Context contexto;

    public AdapterProduto(List<Produto> lista, Context contexto) {
        this.lista_produtos = lista;
        this.contexto = contexto;
    }


    @NonNull
    @Override
    public HolderProduto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_produto, parent, false);
        return new HolderProduto(view, contexto);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProduto holder, int position) {
        Produto item = lista_produtos.get(position);

        String nome = "Nome: " + item.getNome_produto();
        String quantidade = "Quantidade: " + String.valueOf(item.getQuantidade_produto());
        String prioridade = "Prioridade: " + item.getPrioridade_produto();

        holder.id_produto = item.getId_produto();
        holder.nome_produto.setText(nome);
        holder.quantidade.setText(quantidade);
        holder.prioridade.setText(prioridade);
        holder.produto = lista_produtos.get(position);
    }

    @Override
    public int getItemCount() {
        return lista_produtos.size();
    }
}
