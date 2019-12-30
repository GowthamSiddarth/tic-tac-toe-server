package com.gowtham.tictactoe.controller.gameroom;

import com.gowtham.tictactoe.helper.responsewrapper.MessageResponse;
import com.gowtham.tictactoe.helper.responsewrapper.ObjectResponse;
import com.gowtham.tictactoe.model.GameRoom;
import com.gowtham.tictactoe.model.Player;
import com.gowtham.tictactoe.state.AppState;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/v0")
public class GameRoomController {

    @PostMapping(value = "/create-game-room", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> createGameRoom(@RequestParam Map<String, String> requestBody) {
        UUID playerId = UUID.fromString(requestBody.get("playerId"));
        Player firstPlayer = AppState.getInstance().getPlayerMap().get(playerId);

        if (null == firstPlayer) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(false, "Player not found"));
        } else if (firstPlayer.isInGameRoom()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(false, "Player already in a GameRoom"));
        }

        UUID gameRoomId = UUID.randomUUID();
        AppState.getInstance().getGameRoomMap().put(gameRoomId, new GameRoom(firstPlayer));
        firstPlayer.setGameRoomId(gameRoomId);

        Map<String, Object> respObj = new HashMap<>();
        respObj.put("game_room_id", gameRoomId);
        return ResponseEntity.ok().body(ObjectResponse.jsonify(true, respObj));
    }
}
