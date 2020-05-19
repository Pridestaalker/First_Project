package com.example.first_project.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {


        private TextView textDetailsN;
        private TextView textDetailsH;
        private TextView textDetailsW;
        private TextView textDetailsT;
        private ImageView imageDetailsP;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        textDetailsN = findViewById(R.id.name_txt);
        textDetailsH = findViewById(R.id.height_txt);
        textDetailsT = findViewById(R.id.type_txt);
        textDetailsW = findViewById(R.id.weight_txt);
        imageDetailsP = findViewById(R.id.pokeimg_detail);

        Intent intent = getIntent();
        String pokemonJson = intent.getStringExtra("PokemonKey");
        Pokemon pokemon =  Injection.getGson().fromJson(pokemonJson, Pokemon.class);
        showDetails(pokemon);


    }

    private void showDetails(Pokemon pokemon) {
        textDetailsN.setText(pokemon.getName());
        textDetailsH.setText("Height: "+pokemon.getHeight());
        textDetailsT.setText("Type: "+pokemon.getTypes());
        textDetailsW.setText("Weight: "+pokemon.getWeight());
        Picasso.get().load(pokemon.getImage()).into(imageDetailsP);





    }

}
