package com.gowtham.tictactoe.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Match {
    private Player firstPlayer, secondPlayer;
    private UUID roomId, matchId;
    private List<Move> moves;

    public Match(Player firstPlayer, Player secondPlayer, UUID roomId) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.roomId = roomId;

        moves = new ArrayList<>();
        matchId = UUID.randomUUID();
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
