package com.example.first_project.data;

import com.example.first_project.presentation.model.RestPokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeApi {

    @GET("poke_api.json")
    Call<RestPokemonResponse> getPokemonResponse();
}
