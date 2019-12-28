package com.gowtham.tictactoe.model;

import com.gowtham.tictactoe.config.PlayerSymbol;

import java.util.UUID;

public class Player {
    private UUID id;
    private String name;
    private PlayerSymbol symbol;

    public Player(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return id;
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
