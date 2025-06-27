package komeya.gabryel.minhacompra.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import komeya.gabryel.minhacompra.model.Produto;
import komeya.gabryel.minhacompra.repository.ProdutoRepository;




@Database(entities = {Produto.class}, version = 1)
public abstract class Conexao extends RoomDatabase {


    public abstract ProdutoRepository produtoRepository();
    private static Conexao INSTANCIA;


    public static Conexao getInstancia(Context contexto) {

        if (INSTANCIA == null) {
            INSTANCIA = Room.databaseBuilder(contexto.getApplicationContext(), Conexao.class, "db_minhas_compras")
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCIA;
    }


}