package com.gowtham.tictactoe.model;

import com.gowtham.tictactoe.constants.PlayerSymbol;

import java.util.UUID;

public class Player {
    private String name;
    private PlayerSymbol symbol;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public PlayerSymbol getPlayerSymbol() {
        return symbol;
    }

    public void setSymbol(PlayerSymbol symbol) {
        this.symbol = symbol;
    }
}
