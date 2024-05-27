package com.example.ac2_mobileapi.api;

import com.example.ac2_mobileapi.services.ViaCepService;
import com.example.ac2_mobileapi.utils.ViaCep;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViaCepApi {
    private static final String BASE_URL = "https://viacep.com.br/ws/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

    public static ViaCepService getCep() {
        return getClient().create(ViaCepService.class);
    }
}
