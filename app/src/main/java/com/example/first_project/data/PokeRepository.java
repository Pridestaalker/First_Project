package com.example.first_project.data;

import android.content.SharedPreferences;

import com.example.first_project.Constants;
import com.example.first_project.presentation.model.Pokemon;
import com.example.first_project.presentation.model.RestPokemonResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokeRepository {
    private final Gson gson;
    private PokeApi pokeApi;
    private SharedPreferences sharedPreferences;

    public PokeRepository(PokeApi pokeApi, SharedPreferences sharedPreferences, Gson gson) {
        this.pokeApi = pokeApi;
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }


public void getpokemonResponse(final PokeCallback callback) {
    List<Pokemon> list = getDataFromCache();
    if(list != null) {
    }else {
        pokeApi.getPokemonResponse().enqueue(new Callback<RestPokemonResponse>() {
            @Override
            public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    callback.onSuccess(response.body().getResults());
                } else {
                    callback.onFailed();
                }
            }

            @Override
            public void onFailure(Call<RestPokemonResponse> call, Throwable t) {
                callback.onFailed();

            }
        });

    }
    }

    private List<Pokemon> getDataFromCache() {

        String jsonPokemon = sharedPreferences.getString(Constants.KEY_POKEMON_LIST,null);
        if (jsonPokemon == null) {
            return null;
        } else {
            Type listType = new TypeToken<List<Pokemon>>(){}.getType();
            return gson.fromJson(jsonPokemon, listType);
        }
    }


}