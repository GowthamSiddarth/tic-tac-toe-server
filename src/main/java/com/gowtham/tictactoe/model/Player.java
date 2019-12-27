package com.gowtham.tictactoe.model;

import com.gowtham.tictactoe.config.PlayerSymbol;

public class Player {
    private String name;
    private PlayerSymbol playerSymbol;

    public Player(String name, PlayerSymbol playerSymbol) {
        this.name = name;
        this.playerSymbol = playerSymbol;
    }

    public String getName() {
        return name;
    }

    public PlayerSymbol getPlayerSymbol() {
        return playerSymbol;
    }
}
