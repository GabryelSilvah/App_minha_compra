package komeya.gabryel.minhacompra.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "produtos")
public class Produto {

    @ColumnInfo(name = "id_produto")
    @PrimaryKey(autoGenerate = true)
    private int id_produto;

    @ColumnInfo(name = "nome_produto")
    private String nome_produto;

    @ColumnInfo(name = "quantidade_produto")
    private int quantidade_produto;

    @ColumnInfo(name = "prioridade_produto")
    private String prioridade_produto;

    //Construtor

    public Produto(String nome_produto, int quantidade_produto, String prioridade_produto) {
        this.nome_produto = nome_produto;
        this.quantidade_produto = quantidade_produto;
        this.prioridade_produto = prioridade_produto;
    }


//Gets e sets


    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public int getQuantidade_produto() {
        return quantidade_produto;
    }

    public void setQuantidade_produto(int quantidade_produto) {
        this.quantidade_produto = quantidade_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public String getPrioridade_produto() {
        return prioridade_produto;
    }

    public void setPrioridade_produto(String prioridade_produto) {
        this.prioridade_produto = prioridade_produto;
    }
}
