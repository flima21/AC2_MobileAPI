package com.example.ac2_mobileapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ac2_mobileapi.adapter.AlunoAdapter;
import com.example.ac2_mobileapi.api.AlunoApi;
import com.example.ac2_mobileapi.services.AlunoService;
import com.example.ac2_mobileapi.utils.Aluno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListaAlunoActivity extends AppCompatActivity {
    private RecyclerView list;
    private AlunoAdapter alunoAdapter;
    private List<Aluno> listaAlunos;
    private Retrofit retrofit;
    private AlunoService alunoService;
    private Context context;
    private FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_aluno);

        list = (RecyclerView) findViewById(R.id.list);
        alunoService = AlunoApi.getAlunoService();
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this,FormCadastroAluno.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAlunos();
    }

    public void setupRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);

        alunoAdapter = new AlunoAdapter(listaAlunos,this);
        list.setAdapter(alunoAdapter);
        list.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    public void getAlunos() {
        Call<List<Aluno>> call = alunoService.findAll();
        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Call<List<Aluno>> call, Response<List<Aluno>> response) {
                listaAlunos = response.body();
                setupRecycler();
            }

            @Override
            public void onFailure(Call<List<Aluno>> call, Throwable t) {
                Toast.makeText(context, "Erro ao carregar a listagem", Toast.LENGTH_LONG).show();
            }
        });


    }
}