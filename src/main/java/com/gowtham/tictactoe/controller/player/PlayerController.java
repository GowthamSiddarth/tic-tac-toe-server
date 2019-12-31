package com.gowtham.tictactoe.controller.player;

import com.gowtham.tictactoe.helper.responsewrapper.ObjectResponse;
import com.gowtham.tictactoe.model.Player;
import com.gowtham.tictactoe.state.AppState;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/v0")
public class PlayerController {

    @GetMapping(value = "/create-player/{playerName}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> createPlayer(@PathVariable String playerName) {
        UUID playerId = UUID.randomUUID();
        AppState.getInstance().getPlayerMap().put(playerId, new Player(playerName));

        Map<String, Object> respObj = new HashMap<>();
        respObj.put("player_id", playerId);
        return ResponseEntity.ok().body(ObjectResponse.jsonify(true, respObj));
    }
}
