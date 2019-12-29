package com.gowtham.tictactoe.state;


import com.gowtham.tictactoe.model.Game;
import com.gowtham.tictactoe.model.GameRoom;
import com.gowtham.tictactoe.model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AppState {

    private static AppState instance;

    private Map<UUID, Player> playerMap;
    private Map<UUID, Game> gameMap;
    private Map<UUID, GameRoom> gameRoomMap;

    private AppState() {
        playerMap = new HashMap<>();
        gameMap = new HashMap<>();
        gameRoomMap = new HashMap<>();
    }

    public Map<UUID, Player> getPlayerMap() {
        return playerMap;
    }

    public Map<UUID, Game> getGameMap() {
        return gameMap;
    }

    public Map<UUID, GameRoom> getGameRoomMap() {
        return gameRoomMap;
    }

    public static AppState getInstance() {
        if (null == instance) {
            instance = new AppState();
        }

        return instance;
    }
}
