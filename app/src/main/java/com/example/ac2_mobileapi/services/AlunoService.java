package com.example.ac2_mobileapi.services;

import com.example.ac2_mobileapi.utils.Aluno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AlunoService {
    @GET("aluno")
    Call<List<Aluno>> findAll();

    @POST("aluno")
    Call<Aluno> store(@Body Aluno aluno);

    @DELETE("aluno/{id}")
    Call<Void> delete(@Path("id") int alunoId);

    @GET("aluno/{id}")
    Call<Aluno> findById(@Path("id") int alunoId);

    @PUT("aluno/{id}")
    Call<Aluno> update(@Path("id") int alunoId, @Body Aluno aluno);
}
