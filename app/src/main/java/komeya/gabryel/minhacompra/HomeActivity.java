package komeya.gabryel.minhacompra;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import komeya.gabryel.minhacompra.adapter.AdapterProduto;
import komeya.gabryel.minhacompra.dao.Conexao;
import komeya.gabryel.minhacompra.model.Produto;


public class HomeActivity extends AppCompatActivity {

    private LinearLayout container_central;
    private Spinner input_spinner_filtros;
    private RecyclerView recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        //Chamada de método
        inicializar();
        chamarCadastro();
        listarProdutos();


        //Listar por filtro
        aplicarFiltro();
    }


    //Inicializando
    public void inicializar() {
        this.container_central = findViewById(R.id.container_central);


        //Adicionando lista de filtros no spinner
        this.input_spinner_filtros = findViewById(R.id.input_spinner_filtros);
        List<String> listaFiltros = new ArrayList<String>();
        listaFiltros.add("Todos");
        listaFiltros.add("Maior Quantidade");
        listaFiltros.add("Menor Quantidade");
        listaFiltros.add("Prioridade Baixa");
        listaFiltros.add("Prioridade Média");
        listaFiltros.add("Prioridade Alta");
        input_spinner_filtros.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listaFiltros));
        input_spinner_filtros.setSelection(0);//Item padrão

        container_central.addView( LayoutInflater.from(this).inflate(R.layout.recycler_add, container_central, false));

        this.recycler = findViewById(R.id.lista_recycler_produtos);
    }


    //Listando produtos com recyclerView
    public void listarProdutos() {

        //Buscando dados
        Conexao db = Conexao.getInstancia(getApplicationContext());
        List<Produto> listaProdutos = db.produtoRepository().findAll();


        //Validando se a lista está vazia
        if (listaProdutos.isEmpty()) {

            TextView mensagem = new TextView(this);
            mensagem.setText("Nenhum item de compra registrado");

            this.container_central.addView(mensagem);

        } else {
            //Configurando e exibindo lista na recyclerView
            AdapterProduto adapter = new AdapterProduto(listaProdutos, this);
            this.recycler .setAdapter(adapter);
        }
    }


    //Aplicando pesquisar por filtro
    public void aplicarFiltro() {

        input_spinner_filtros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Buscando dados
                Conexao db = Conexao.getInstancia(getApplicationContext());
                List<Produto> listaProdutos = new ArrayList<>();

                switch (position) {
                    case 0:
                        listaProdutos = db.produtoRepository().findAll();
                        break;
                    case 1:
                        listaProdutos = db.produtoRepository().findOrderMaior();
                        break;
                    case 2:
                        listaProdutos = db.produtoRepository().findOrderMenor();
                        break;
                    case 3:
                        listaProdutos = db.produtoRepository().findPrioridade("Baixa");
                        break;
                    case 4:
                        listaProdutos = db.produtoRepository().findPrioridade("Média");
                        break;
                    case 5:
                        listaProdutos = db.produtoRepository().findPrioridade("Alta");
                        break;
                    default:
                        listaProdutos = db.produtoRepository().findAll();

                }


                //Validando se a lista está vazia
                if (listaProdutos.isEmpty()) {

                    TextView mensagem = new TextView(HomeActivity.this);
                    mensagem.setText("Nenhum item encontrado com esse filtro");

                    //Removendo o TextView adicionado quando a lista está vazia para não duplicar o TextView
                    container_central.removeAllViews();
                    recycler.setAdapter(null);

                    //Adicionando nova TextView com a mensagem de nenhum item encontrado
                    container_central.addView(mensagem);


                } else {

                    //Removendo toda e qualquer view que tenha sido adicionada antes dentro do container central, para remover TextView com mensagem
                    container_central.removeAllViews();

                    //Adicionando e vinculando listaRecycler novamento porque pode ter sido removida quando não encontrou algum produto na filtragem
                    container_central.addView( LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_add, container_central, false));
                    recycler = findViewById(R.id.lista_recycler_produtos);


                    //Configurando e exibindo lista na recyclerView
                    AdapterProduto adapter = new AdapterProduto(listaProdutos, HomeActivity.this);
                    recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    //Chamando tela de cadastro
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