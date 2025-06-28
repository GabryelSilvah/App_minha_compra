package komeya.gabryel.minhacompra.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import komeya.gabryel.minhacompra.model.Produto;

@Dao
public interface ProdutoRepository {

    @Query("select * from produtos")
    public List<Produto> findAll();

    @Query("select * from produtos WHERE prioridade_produto = :prioridade")
    public List<Produto> findPrioridade(String prioridade);

    @Query("select * from produtos ORDER BY quantidade_produto DESC")
    public List<Produto> findOrderMaior();

    @Query("select * from produtos ORDER BY quantidade_produto ASC")
    public List<Produto> findOrderMenor();

    @Query("select * from produtos where id_produto = :id")
    public Produto findById(int id);

    @Insert
    public void save(Produto produto);

    @Query("DELETE FROM produtos WHERE id_produto = :id_produto")
    public void delete(int id_produto);

    @Query("UPDATE produtos SET nome_produto = :nome, quantidade_produto = :quantidade, prioridade_produto = :prioridade WHERE id_produto = :id_produto")
    public void updateProduto(String nome, int quantidade, String prioridade, int id_produto);

}