package com.example.ac2_mobileapi.holder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ac2_mobileapi.R;

public class AlunoHolder extends RecyclerView.ViewHolder {
    public TextView nomeAluno;
    public ImageButton btnExcluir;
    public ImageButton btnEditar;

    public AlunoHolder(View itemView) {
        super(itemView);

        nomeAluno = (TextView) itemView.findViewById(R.id.nomeAluno);
        btnEditar = (ImageButton) itemView.findViewById(R.id.btnEditar);
        btnExcluir = (ImageButton) itemView.findViewById(R.id.btnExcluir);
    }
}
