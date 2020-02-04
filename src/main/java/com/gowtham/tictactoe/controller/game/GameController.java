package com.gowtham.tictactoe.controller.game;

import com.gowtham.tictactoe.constants.GameStatus;
import com.gowtham.tictactoe.constants.PlayerSymbol;
import com.gowtham.tictactoe.helper.appgarbagecollector.GarbageCollector;
import com.gowtham.tictactoe.helper.playersymbol.PlayerSymbolHelper;
import com.gowtham.tictactoe.helper.responsewrapper.MessageResponse;
import com.gowtham.tictactoe.helper.responsewrapper.ObjectResponse;
import com.gowtham.tictactoe.model.Game;
import com.gowtham.tictactoe.model.GameRoom;
import com.gowtham.tictactoe.model.Move;
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

    @PostMapping(value = "/start-new-game", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
        if (null == otherPlayer) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse(false, "Waiting for second player to join"));
        } else if (null != otherPlayer.getPlayerSymbol()) {
            currPlayer.setPlayerSymbol(PlayerSymbolHelper.getComplimentarySymbol(otherPlayer.getPlayerSymbol()));
        } else {
            PlayerSymbol currPlayerSymbol = PlayerSymbol.values()[new Random().nextInt(PlayerSymbol.values().length)];
            currPlayer.setPlayerSymbol(currPlayerSymbol);
        }

        Game game;
        UUID gameId;
        if (null == otherPlayer.getGameId()) {
            gameId = UUID.randomUUID();
            game = new Game(gameRoomId);

            otherPlayer.setGameId(gameId);
            currPlayer.setGameId(gameId);

            AppState.getInstance().getGameMap().put(gameId, game);
        } else {
            gameId = otherPlayer.getGameId();
            game = AppState.getInstance().getGameMap().get(gameId);
        }

        if (null == game.getNextTurn()) {
            Player nextTurn = new Player[]{gameRoom.getFirstPlayer(), gameRoom.getSecondPlayer()}[new Random().nextInt(2)];
            game.setNextTurn(nextTurn);
        }

        Map<String, String> innerRespObj = new HashMap<>();
        innerRespObj.put("game_id", gameId.toString());
        innerRespObj.put("player_symbol", currPlayer.getPlayerSymbol().toString());
        innerRespObj.put("my_turn", Boolean.toString(currPlayer == game.getNextTurn()));
        return ResponseEntity.ok().body(ObjectResponse.jsonify(true, innerRespObj));
    }

    @PostMapping(value = "/make-a-move", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> makeAMove(@RequestParam Map<String, String> requestBody) {
        UUID playerId = UUID.fromString(requestBody.get("playerId"));
        UUID gameRoomId = UUID.fromString(requestBody.get("gameRoomId"));
        UUID gameId = UUID.fromString(requestBody.get("gameId"));
        int row = Integer.parseInt(requestBody.get("row"));
        int col = Integer.parseInt(requestBody.get("col"));

        Player currPlayer = AppState.getInstance().getPlayerMap().get(playerId);
        GameRoom gameRoom = AppState.getInstance().getGameRoomMap().get(gameRoomId);
        Game game = AppState.getInstance().getGameMap().get(gameId);
        Move move = new Move(currPlayer, row, col);

        if (game.getMoves().contains(move)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(false, "Game Board Location Already Used!"));
        }

        Player grid[][] = game.getGrid();
        grid[row][col] = currPlayer;
        game.addMove(move);

        game.computeGameStatus();
        GameStatus gameStatus = game.getGameStatus();

        Map<String, String> innerRespObj = new HashMap<>();
        innerRespObj.put("game_status", gameStatus.toString());
        innerRespObj.put("my_turn", Boolean.toString(false));
        if (GameStatus.DETERMINED == gameStatus) {
            innerRespObj.put("winner", currPlayer.getPlayerSymbol().toString());
        }

        Player otherPlayer = currPlayer == gameRoom.getFirstPlayer() ? gameRoom.getSecondPlayer() : gameRoom.getFirstPlayer();
        game.setNextTurn(otherPlayer);

        return ResponseEntity.ok().body(ObjectResponse.jsonify(true, innerRespObj));
    }

    @PostMapping(value = "/is-my-turn", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> isMyTurn(@RequestParam Map<String, String> requestBody) {
        UUID playerId = UUID.fromString(requestBody.get("playerId"));
        UUID gameRoomId = UUID.fromString(requestBody.get("gameRoomId"));
        UUID gameId = UUID.fromString(requestBody.get("gameId"));

        Player currPlayer = AppState.getInstance().getPlayerMap().get(playerId);
        GameRoom gameRoom = AppState.getInstance().getGameRoomMap().get(gameRoomId);
        Game game = AppState.getInstance().getGameMap().get(gameId);

        Player otherPlayer = currPlayer == gameRoom.getFirstPlayer() ? gameRoom.getSecondPlayer() : gameRoom.getFirstPlayer();

        if (null == game.getGameStatus()) {
            game.computeGameStatus();
        }

        GameStatus gameStatus = game.getGameStatus();

        Map<String, String> innerRespObj = new HashMap<>();
        innerRespObj.put("game_status", gameStatus.toString());
        innerRespObj.put("my_turn", Boolean.toString(currPlayer == game.getNextTurn() && gameStatus.equals(GameStatus.RUNNING)));

        if (GameStatus.DETERMINED == gameStatus) {
            innerRespObj.put("winner", otherPlayer.getPlayerSymbol().toString());
        }

        if (!game.getMoves().isEmpty()) {
            Move lastMove = game.getMoves().get(game.getMoves().size() - 1);
            innerRespObj.put("last_move_symbol", lastMove.getPlayer().getPlayerSymbol().toString());
            innerRespObj.put("last_move_row", Integer.toString(lastMove.getRow()));
            innerRespObj.put("last_move_col", Integer.toString(lastMove.getCol()));
        }

        return ResponseEntity.ok().body(ObjectResponse.jsonify(true, innerRespObj));
    }

    @PostMapping(value = "/quit-game", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> quitGame(@RequestParam Map<String, String> requestBody) {
        UUID playerId = UUID.fromString(requestBody.get("playerId"));
        UUID gameRoomId = UUID.fromString(requestBody.get("gameRoomId"));
        UUID gameId = UUID.fromString(requestBody.get("gameId"));

        Player currPlayer = AppState.getInstance().getPlayerMap().get(playerId);
        GameRoom gameRoom = AppState.getInstance().getGameRoomMap().get(gameRoomId);

        if (gameRoom.getFirstPlayer().equals(currPlayer)) {
            gameRoom.setFirstPlayer(null);
        } else if (gameRoom.getSecondPlayer().equals(currPlayer)) {
            gameRoom.setSecondPlayer(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(false, "Player and GameRoom mismatch occurred"));
        }

        Player removedPlayer = GarbageCollector.removePlayerFromState(playerId);
        if (null == removedPlayer) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(false, "Player not found"));
        }

        Game removedGame = GarbageCollector.removeGameFromState(gameId);
        if (null == removedGame) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(false, "Game not found"));
        }

        return ResponseEntity.ok().body(ObjectResponse.jsonify(true,  removedPlayer.getName() + " has quit the game"));
    }
}
