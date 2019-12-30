package com.gowtham.tictactoe.constants;

public enum PlayerSymbol {
    CROSS("X"),
    CIRCLE("O");

    private String playerSymbol;

    PlayerSymbol(String playerSymbol) {
        this.playerSymbol = playerSymbol;
    }

    public String getPlayerSymbol() {
        return playerSymbol;
    }
}
