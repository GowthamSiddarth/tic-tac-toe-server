package com.gowtham.tictactoe.model;

import com.gowtham.tictactoe.state.AppState;


import java.util.UUID;

public class GameRoom {
    private Player firstPlayer, secondPlayer;

    public GameRoom(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Game addGame(Game game) {
        return AppState.getInstance().getGameMap().put(UUID.randomUUID(), game);
    }
}
