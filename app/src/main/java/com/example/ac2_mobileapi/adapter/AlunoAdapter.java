package com.example.ac2_mobileapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ac2_mobileapi.FormCadastroAluno;
import com.example.ac2_mobileapi.ListaAlunoActivity;
import com.example.ac2_mobileapi.R;
import com.example.ac2_mobileapi.api.AlunoApi;
import com.example.ac2_mobileapi.holder.AlunoHolder;
import com.example.ac2_mobileapi.services.AlunoService;
import com.example.ac2_mobileapi.utils.Aluno;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoHolder> {
    private List<Aluno> alunos = new ArrayList<>();
    Context context;

    public AlunoAdapter(List<Aluno> alunos, Context context) {
        this.alunos = alunos;
        this.context = context;
    }

    @NonNull
    @Override
    public AlunoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlunoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aluno,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoHolder holder, int position) {
        holder.nomeAluno.setText(alunos.get(position).getNome() + " - " + alunos.get(position).getRa());
        holder.btnExcluir.setOnClickListener(v -> {
            deleteItem(position);
        });
        holder.btnEditar.setOnClickListener(v -> {
            editItem(position);
        });
    }

    public void deleteItem(int position) {
        int id = alunos.get(position).getId();

        AlunoService alunoService = AlunoApi.getAlunoService();
        Call<Void> call = alunoService.delete(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    alunos.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,alunos.size());
                    Toast.makeText(context, "Excluído com sucesso", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context, "Registro não pode ser excluído " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Registro não pode ser excluído: OnFailure", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void editItem(int position) {
        int id = alunos.get(position).getId();
        Intent i = new Intent(context, FormCadastroAluno.class);
        i.putExtra("id",id);

        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return alunos != null ? alunos.size() : 0;
    }
}
