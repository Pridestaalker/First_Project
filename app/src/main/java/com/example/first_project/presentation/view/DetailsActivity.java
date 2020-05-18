package com.example.first_project.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.first_project.Injection;
import com.example.first_project.R;
import com.example.first_project.presentation.controller.MainController;
import com.example.first_project.presentation.model.Pokemon;

import org.w3c.dom.Text;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {


        private TextView textDetails;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        textDetails = findViewById(R.id.details_txt);
        Intent intent = getIntent();
        String pokemonJson = intent.getStringExtra("PokemonKey");
        Pokemon pokemon =  Injection.getGson().fromJson(pokemonJson, Pokemon.class);
        showDetails(pokemon);


    }

    private void showDetails(Pokemon pokemon) {
        textDetails.setText(pokemon.getName());

    }

}
