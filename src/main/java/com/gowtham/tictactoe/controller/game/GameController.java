package com.gowtham.tictactoe.controller.game;

import com.gowtham.tictactoe.constants.PlayerSymbol;
import com.gowtham.tictactoe.helper.playersymbol.PlayerSymbolHelper;
import com.gowtham.tictactoe.helper.responsewrapper.MessageResponse;
import com.gowtham.tictactoe.helper.responsewrapper.ObjectResponse;
import com.gowtham.tictactoe.model.Game;
import com.gowtham.tictactoe.model.GameRoom;
import com.gowtham.tictactoe.model.Player;
import com.gowtham.tictactoe.state.AppState;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/v0")
public class GameController {

    @PostMapping(value = "/start-game", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> startGame(@RequestParam Map<String, String> requestBody) {
        UUID playerId = UUID.fromString(requestBody.get("playerId"));
        UUID gameRoomId = UUID.fromString(requestBody.get("gameRoomId"));

        Player currPlayer = AppState.getInstance().getPlayerMap().get(playerId);
        if (null == currPlayer) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(false, "Player not found"));
        }

        GameRoom gameRoom = AppState.getInstance().getGameRoomMap().get(gameRoomId);
        if (null == gameRoom) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(false, "Game Room not found"));
        }

        if (currPlayer != gameRoom.getFirstPlayer() && currPlayer != gameRoom.getSecondPlayer()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(false, "Player mismatch with Game Room"));
        }

        Player otherPlayer = currPlayer == gameRoom.getFirstPlayer() ? gameRoom.getSecondPlayer() : gameRoom.getFirstPlayer();
        if (null != otherPlayer.getPlayerSymbol()) {
            currPlayer.setPlayerSymbol(PlayerSymbolHelper.getComplimentarySymbol(otherPlayer.getPlayerSymbol()));
        } else {
            PlayerSymbol currPlayerSymbol = PlayerSymbol.values()[new Random().nextInt(PlayerSymbol.values().length)];
            currPlayer.setPlayerSymbol(currPlayerSymbol);
        }

        UUID gameId = null == otherPlayer.getGameId() ? UUID.randomUUID() : otherPlayer.getGameId();
        currPlayer.setGameId(gameId);
        AppState.getInstance().getGameMap().put(gameId, new Game(gameRoomId));

        Map<String, String> innerRespObj = new HashMap<>();
        innerRespObj.put("game_id", gameId.toString());
        innerRespObj.put("player_symbol", currPlayer.getPlayerSymbol().toString());
        return ResponseEntity.ok().body(ObjectResponse.jsonify(true, innerRespObj));
    }
}
