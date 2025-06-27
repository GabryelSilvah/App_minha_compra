package komeya.gabryel.minhacompra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import komeya.gabryel.minhacompra.adapter.AdapterProduto;
import komeya.gabryel.minhacompra.dao.Conexao;
import komeya.gabryel.minhacompra.model.Produto;


public class HomeActivity extends AppCompatActivity {

    private LinearLayout container_central;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        inicializar();
        chamarCadastro();


        //Buscando dados
        Conexao db = Conexao.getInstancia(getApplicationContext());
        List<Produto> listaProdutos = db.produtoRepository().findAll();

        if (listaProdutos.isEmpty()) {

            TextView mensagem = new TextView(this);
            mensagem.setText("Nenhum item de compra registrado");

            this.container_central.addView(mensagem);

        } else {
            //Configurando e exibindo lista na recyclerView
            RecyclerView recycler = findViewById(R.id.lista_recycler_produtos);
            AdapterProduto adapter = new AdapterProduto(listaProdutos, this);
            recycler.setAdapter(adapter);
        }

    }

    public void inicializar() {
        this.container_central = findViewById(R.id.container_central);
    }


    public void chamarCadastro() {
        findViewById(R.id.btn_add_novo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

    }
}