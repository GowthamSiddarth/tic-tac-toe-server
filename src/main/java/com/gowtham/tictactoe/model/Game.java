package com.gowtham.tictactoe.model;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {
    @Value("${grid.size}")
    private int gridSize;

    private Player grid[][];
    private UUID gameRoomId;
    private List<Move> moves;

    public Game(UUID gameRoomId) {
        this.gameRoomId = gameRoomId;

        moves = new ArrayList<>();
        grid = new Player[gridSize][gridSize];
    }

    public UUID getGameRoomId() {
        return gameRoomId;
    }

    public boolean addMove(Move move) {
        return moves.add(move);
    }
}
