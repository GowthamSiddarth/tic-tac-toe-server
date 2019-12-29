package com.gowtham.tictactoe.model;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {
    @Value("${grid.size}")
    private int gridSize;

    private Player grid[][];
    private Player firstPlayer, secondPlayer;
    private UUID roomId;
    private List<Move> moves;

    public Game(Player firstPlayer, Player secondPlayer, UUID roomId) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.roomId = roomId;

        moves = new ArrayList<>();
        grid = new Player[gridSize][gridSize];
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public boolean addMove(Move move) {
        return moves.add(move);
    }
}
