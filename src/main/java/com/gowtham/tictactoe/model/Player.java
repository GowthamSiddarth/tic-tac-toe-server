package com.gowtham.tictactoe.model;

import com.gowtham.tictactoe.constants.PlayerSymbol;

import java.util.UUID;

public class Player {
    private String name;
    private PlayerSymbol symbol;
    private UUID gameId;
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

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameRoomId(UUID gameRoomId) {
        this.gameRoomId = gameRoomId;
    }

    public UUID getGameRoomId() {
        return gameRoomId;
    }

    public boolean isInGameRoom() {
        return null != gameRoomId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (null == obj || getClass() != obj.getClass()) {
            return false;
        } else {
            Player otherPlayer = (Player) obj;
            return name.equals(otherPlayer.getName());
        }
    }
}
