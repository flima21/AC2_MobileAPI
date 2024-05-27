package com.example.ac2_mobileapi.services;

import com.example.ac2_mobileapi.utils.ViaCep;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepService {
    @GET("{cep}/json")
    Call<ViaCep> get(@Path("cep") String cep);
}
