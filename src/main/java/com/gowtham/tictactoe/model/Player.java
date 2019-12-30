package com.gowtham.tictactoe.model;

import com.gowtham.tictactoe.constants.PlayerSymbol;

import java.util.UUID;

public class Player {
    private String name;
    private PlayerSymbol symbol;
    private UUID gameRoomId;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public PlayerSymbol getPlayerSymbol() {
        return symbol;
    }

    public void setPlayerSymbol(PlayerSymbol symbol) {
        this.symbol = symbol;
    }

    public void setGameRoomId(UUID gameRoomId) {
        this.gameRoomId = gameRoomId;
    }

    public UUID getGameRoomId() {
        return gameRoomId;
    }
}
