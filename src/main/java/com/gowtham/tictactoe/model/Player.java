package com.gowtham.tictactoe.model;

import com.gowtham.tictactoe.config.PlayerSymbol;

import java.util.UUID;

public class Player {
    private UUID id;
    private String name;
    private PlayerSymbol symbol;

    public Player(UUID id, String name, PlayerSymbol symbol) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
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
}