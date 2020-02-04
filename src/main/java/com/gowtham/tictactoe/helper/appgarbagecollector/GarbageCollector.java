package com.gowtham.tictactoe.helper.appgarbagecollector;

import com.gowtham.tictactoe.model.Game;
import com.gowtham.tictactoe.model.GameRoom;
import com.gowtham.tictactoe.model.Player;
import com.gowtham.tictactoe.state.AppState;

import java.util.Map;
import java.util.UUID;

public class GarbageCollector {

    public static Game removeGameFromState(UUID gameId) {
        Map<UUID, Game> gameMap = AppState.getInstance().getGameMap();
        return gameMap.remove(gameId.toString());
    }

    public static GameRoom removeGameRoomFromState(UUID gameRoomId) {
        Map<UUID, GameRoom> gameRoomMap = AppState.getInstance().getGameRoomMap();
        return gameRoomMap.remove(gameRoomId.toString());
    }

    public static Player removePlayerFromState(UUID playerId) {
        Map<UUID, Player> playerMap = AppState.getInstance().getPlayerMap();
        return playerMap.remove(playerId.toString());
    }

}
