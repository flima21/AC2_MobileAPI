package com.example.ac2_mobileapi.api;

import com.example.ac2_mobileapi.services.AlunoService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlunoApi {
    private static final String BASE_URL = "https://6654b3191c6af63f46790019.mockapi.io/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

    public static AlunoService getAlunoService() {
        return getClient().create(AlunoService.class);
    }
}
