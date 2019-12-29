package com.gowtham.tictactoe.model;

import com.gowtham.tictactoe.state.AppState;


import java.util.UUID;

public class GameRoom {
    private Player firstPlayer, secondPlayer;
    private String password;

    public GameRoom(Player firstPlayer) {
        this.firstPlayer = firstPlayer;

        password = UUID.randomUUID().toString();
        password = password.substring(0, password.indexOf("-"));
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Game addGame(Game game) {
        return AppState.getInstance().getGameMap().put(UUID.randomUUID(), game);
    }
}
