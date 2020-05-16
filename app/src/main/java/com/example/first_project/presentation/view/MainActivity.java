package com.example.first_project.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.first_project.Constants;
import com.example.first_project.ListAdapter;
import com.example.first_project.R;
import com.example.first_project.data.PokeApi;
import com.example.first_project.presentation.model.Pokemon;
import com.example.first_project.presentation.model.RestPokemonResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://pokeapi.co";

    private SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Gson gson;


    @Override

            protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Pokedex", Context.MODE_PRIVATE);

        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<Pokemon> pokemonList = getDataFromCache();

        if(pokemonList != null) {
            showList(pokemonList);

        } else {

            makeApiCall();
        }
    }

    private List<Pokemon> getDataFromCache() {

        String jsonPokemon = sharedPreferences.getString(Constants.KEY_POKEMON_LIST, null);
        if (jsonPokemon == null) {
            return null;
        } else {
            Type listType = new TypeToken<List<Pokemon>>(){}.getType();
            return gson.fromJson(jsonPokemon, listType);
        }
    }

    private void showList(final List<Pokemon> pokemonList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(pokemonList);
        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        pokemonList.remove(viewHolder.getAdapterPosition());
                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);



    }


    private void makeApiCall() {



                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                PokeApi pokeApi = retrofit.create(PokeApi.class);

                Call<RestPokemonResponse> call = pokeApi.getPokemonResponse();
                call.enqueue(new Callback<RestPokemonResponse>() {
                    @Override
                    public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response) {
                        if(response.isSuccessful() && response.body() != null) {
                            List<Pokemon> pokemonList = response.body().getResults();
                            saveList(pokemonList);
                            showList(pokemonList);
                        } else {
                            showError();
                        }
                    }

                    private void saveList(List<Pokemon> pokemonList) {
                        String jsonString = gson.toJson(pokemonList);
                        sharedPreferences
                                .edit()
                                .putString(Constants.KEY_POKEMON_LIST, jsonString)
                                .apply();
                        Toast.makeText(getApplicationContext(), "List Saved", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<RestPokemonResponse> call, Throwable t) {
                        showError();

                    }


                });


    }

    private void showError() {

        Toast.makeText(getApplicationContext(), "API ERROR", Toast.LENGTH_SHORT).show();
    }
}