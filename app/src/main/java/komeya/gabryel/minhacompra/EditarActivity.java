package komeya.gabryel.minhacompra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import komeya.gabryel.minhacompra.dao.Conexao;
import komeya.gabryel.minhacompra.model.Produto;

public class EditarActivity extends AppCompatActivity {

    private EditText input_nome;
    private EditText input_quantidade;
    private Spinner input_spinner_prioridade;
    private Intent intentRecebida;
    private AppCompatButton btn_salva_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        inizialiar();
        voltarHome();
        salvar();
    }


    public void inizialiar() {
        this.intentRecebida = getIntent();
        this.input_nome = findViewById(R.id.input_nome);
        this.input_quantidade = findViewById(R.id.input_quantidade);
        this.btn_salva_item = findViewById(R.id.btn_salva_item);

        //Adicionando lista de prioridades no spinner
        this.input_spinner_prioridade = findViewById(R.id.input_spinner_prioridade);
        List<String> listaPrioridades = new ArrayList<String>();
        listaPrioridades.add("Baixa");
        listaPrioridades.add("Média");
        listaPrioridades.add("Alta");
        input_spinner_prioridade.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listaPrioridades));

        //Procurando indece onde prioridade do produto é igual a registrada no banco
        int indecePrioridade = listaPrioridades.indexOf(intentRecebida.getStringExtra("prioridade_produto"));

        //Indece que já vem marcado no formulário por ser o mesmo registrdo no banco
        input_spinner_prioridade.setSelection(indecePrioridade);

        //Setando no formulário informações já cadastadas
        this.input_nome.setText(intentRecebida.getStringExtra("nome_produto"));
        this.input_quantidade.setText(intentRecebida.getStringExtra("quantidade_produto"));

        //Alterando nome do button de salvar para alterar
        this.btn_salva_item.setText("Alterar");
    }


    //Método para voltar a tela principal após alterar produto
    public void voltarHome() {
        findViewById(R.id.btn_cancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


    //Salvando alterações
    public void salvar() {
        this.btn_salva_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validando se todos os campos foram preenchidos
                if (input_nome.getText().toString().isEmpty()) {
                    Toast.makeText(EditarActivity.this, "Informe o nome do produto", Toast.LENGTH_LONG).show();
                } else if (input_quantidade.getText().toString().isEmpty()) {
                    Toast.makeText(EditarActivity.this, "Informe a qunatidade do produto", Toast.LENGTH_LONG).show();
                } else {

                    //Pegando dados
                    String nome_produto = input_nome.getText().toString();
                    int quantidade_produto = Integer.parseInt(input_quantidade.getText().toString());
                    String prioridade_produto = input_spinner_prioridade.getSelectedItem().toString();


                    //Salvando
                    Conexao db = Conexao.getInstancia(getApplicationContext());
                    db.produtoRepository().updateProduto(nome_produto, quantidade_produto, prioridade_produto, intentRecebida.getIntExtra("id_produto", 0));

                    //Menssagem de sucesso e retornar para home
                    Toast.makeText(EditarActivity.this, "Produto salvo com sucesso!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EditarActivity.this, HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }


            }
        });
    }


}