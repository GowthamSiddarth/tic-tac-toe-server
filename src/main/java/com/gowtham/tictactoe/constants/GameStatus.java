package com.gowtham.tictactoe.constants;

public enum GameStatus {
    RUNNING("running"),
    DETERMINED("determined"),
    UNDETERMINED("undetermined");

    private String gameStatus;

    GameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getGameStatus() {
        return gameStatus;
    }
}
