package com.gowtham.tictactoe.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameRoom {
    private Player firstPlayer, secondPlayer;
    private List<Match> matches;
    private UUID roomId;
    private String password;

    public GameRoom(Player firstPlayer) {
        this.firstPlayer = firstPlayer;

        roomId = UUID.randomUUID();
        password = roomId.toString().substring(0, roomId.toString().indexOf("-"));
        matches = new ArrayList<>();
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public boolean addMatch(Match match) {
        return matches.add(match);
    }
}
