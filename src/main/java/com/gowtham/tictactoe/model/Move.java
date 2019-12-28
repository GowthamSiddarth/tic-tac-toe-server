package com.gowtham.tictactoe.model;

public class Move {
    private Player player;
    private int row, col;

    public Move(Player player, int row, int col) {
        this.player = player;
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public Player getPlayer() {
        return player;
    }

    public int getRow() {
        return row;
    }
}
