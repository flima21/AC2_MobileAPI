package com.example.ac2_mobileapi;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ac2_mobileapi.api.AlunoApi;
import com.example.ac2_mobileapi.api.ViaCepApi;
import com.example.ac2_mobileapi.services.AlunoService;
import com.example.ac2_mobileapi.services.ViaCepService;
import com.example.ac2_mobileapi.utils.Aluno;
import com.example.ac2_mobileapi.utils.ViaCep;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormCadastroAluno extends AppCompatActivity {

    private TextInputEditText cep;
    private TextInputEditText logradouro;
    private TextInputEditText bairro;
    private TextInputEditText uf;
    private TextInputEditText cidade;
    private TextInputEditText complemento;
    private TextInputEditText ra;
    private TextInputEditText nome;
    private ViaCepService viaCepService;
    private AlunoService alunoService;

    private Context context;
    private Button btnSalvar;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_cadastro_aluno);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viaCepService = ViaCepApi.getCep();
        alunoService = AlunoApi.getAlunoService();

        nome = (TextInputEditText) findViewById(R.id.nome);
        ra = (TextInputEditText) findViewById(R.id.ra);
        logradouro = (TextInputEditText) findViewById(R.id.logradouro);
        bairro = (TextInputEditText) findViewById(R.id.bairro);
        uf = (TextInputEditText) findViewById(R.id.uf);
        cidade = (TextInputEditText) findViewById(R.id.localidade);
        cep = (TextInputEditText) findViewById(R.id.cep);
        complemento = (TextInputEditText) findViewById(R.id.complemento);

        cep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 8) {
                    buscarCep(s.toString(),FormCadastroAluno.this);
                }
            }
        });

        id = getIntent().getIntExtra("id",0);
        Log.d("ID_VINDO_DA_ACTI",id+"");

        if (id > 0) {
            alunoService.findById(id).enqueue(new Callback<Aluno>() {
                @Override
                public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                    if (response.isSuccessful()) {
                        Log.d("BUSCA_CLIENTE",response.body().toString());
                        if (response.body() != null ) {
                            nome.setText(response.body().getNome());
                            ra.setText(response.body().getRa()+"");
                            cep.setText(response.body().getCep());
                            logradouro.setText(response.body().getLogradouro());
                            complemento.setText(response.body().getComplemento());
                            cidade.setText(response.body().getCidade());
                            uf.setText(response.body().getUf());
                            bairro.setText(response.body().getBairro());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Aluno> call, Throwable t) {
                    Toast.makeText(FormCadastroAluno.this, "Registro não pode ser encontrado", Toast.LENGTH_LONG).show();
                }
            });
        }

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(v -> {
            Aluno aluno = new Aluno();

            aluno.setBairro(bairro.getText().toString());
            aluno.setCidade(cidade.getText().toString());
            aluno.setCep(cep.getText().toString());
            aluno.setComplemento(complemento.getText().toString());
            aluno.setLogradouro(logradouro.getText().toString());
            aluno.setNome(nome.getText().toString());
            aluno.setRa(Integer.parseInt(ra.getText().toString()));

            if (id == 0) {
                insertAluno(aluno);
            }
            else {
                aluno.setId(id);
                updateAluno(aluno);
            }
        });

    }

    public void insertAluno(Aluno aluno) {
        Call<Aluno> call = alunoService.store(aluno);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (response.isSuccessful()) {
                    // A solicitação foi bem-sucedida
                    Aluno createdPost = response.body();
                    Toast.makeText(FormCadastroAluno.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Toast.makeText(FormCadastroAluno.this, "Cadastro não pode ser editado", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateAluno(Aluno aluno) {
        Call<Aluno> call = alunoService.update(id,aluno);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (response.isSuccessful()) {
                    // A solicitação foi bem-sucedida
                    Aluno createdPost = response.body();
                    Toast.makeText(FormCadastroAluno.this, "Editado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Toast.makeText(FormCadastroAluno.this, "Cadastro não pode ser editado", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void buscarCep(String cep, Context context) {
        Call<ViaCep> call = viaCepService.get(cep);
        call.enqueue(new Callback<ViaCep>() {
            @Override
            public void onResponse(Call<ViaCep> call, Response<ViaCep> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCep() == null) {
                        Toast.makeText(context, "CEP Não encontrado", Toast.LENGTH_LONG).show();
                    }
                    else {
                        logradouro.setText(response.body().getLogradouro());
                        bairro.setText(response.body().getBairro());
                        uf.setText(response.body().getUf());
                        cidade.setText(response.body().getLocalidade());
                    }
                }

                else {
                    Toast.makeText(context, "CEP Não encontrado", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ViaCep> call, Throwable t) {
                Toast.makeText(context, "Erro ao carregar a listagem", Toast.LENGTH_LONG).show();
            }
        });
    }
}