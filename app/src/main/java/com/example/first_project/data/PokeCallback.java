package com.example.first_project.data;

import com.example.first_project.presentation.model.Pokemon;

import java.util.List;

public interface PokeCallback {

    public void onSuccess(List<Pokemon> response);
    public void onFailed();
}
