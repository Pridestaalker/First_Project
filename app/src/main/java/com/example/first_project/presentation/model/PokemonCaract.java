package com.example.first_project.presentation.model;

import java.util.List;

public class PokemonCaract {

    private String name;
    private Integer height;
    private Integer weight;
    private List<PokemonTypes> types;


    public String getName() {
        return name;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }

    public List<PokemonTypes> getTypes() {
        return types;
    }
}
