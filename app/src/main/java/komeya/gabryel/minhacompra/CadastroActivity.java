package komeya.gabryel.minhacompra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import komeya.gabryel.minhacompra.dao.Conexao;
import komeya.gabryel.minhacompra.model.Produto;

public class CadastroActivity extends AppCompatActivity {

    private EditText input_nome;
    private EditText input_quantidade;
    private Spinner input_spinner_prioridade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        inizialiar();
        voltarHome();
        salvar();
    }


    public void inizialiar() {

        this.input_nome = findViewById(R.id.input_nome);
        this.input_quantidade = findViewById(R.id.input_quantidade);

        //Adicionando lista de prioridades no spinner
        this.input_spinner_prioridade = findViewById(R.id.input_spinner_prioridade);
        List<String> listaPrioridades = new ArrayList<String>();
        listaPrioridades.add("Baixa");
        listaPrioridades.add("Média");
        listaPrioridades.add("Alta");
        input_spinner_prioridade.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listaPrioridades));
        input_spinner_prioridade.setSelection(0);
    }


    public void voltarHome() {
        findViewById(R.id.btn_cancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void salvar() {
        findViewById(R.id.btn_salva_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validando se todos os campos foram preenchidos
                if (input_nome.getText().toString().isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Informe o nome do produto", Toast.LENGTH_LONG).show();
                } else if (input_quantidade.getText().toString().isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Informe a qunatidade do produto", Toast.LENGTH_LONG).show();
                } else {

                    //Pegando dados
                    String nome_produto = input_nome.getText().toString();
                    int quantidade_produto = Integer.parseInt(input_quantidade.getText().toString());
                    String prioridade_produto = input_spinner_prioridade.getSelectedItem().toString();


                    //Criando modelo
                    Produto novoProduto = new Produto(nome_produto, quantidade_produto, prioridade_produto);


                    //Salvando
                    Conexao db = Conexao.getInstancia(getApplicationContext());
                    db.produtoRepository().save(novoProduto);

                    //Menssagem de sucesso e retornar para home
                    Toast.makeText(CadastroActivity.this, "Produto salvo com sucesso!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CadastroActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }


            }
        });
    }


}