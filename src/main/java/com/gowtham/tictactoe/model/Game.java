package com.gowtham.tictactoe.model;

import com.gowtham.tictactoe.constants.GameStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {
    private final int gridSize = 3;

    private Player grid[][], nextTurn;
    private UUID gameRoomId;
    private List<Move> moves;

    private GameStatus gameStatus;

    public Game(UUID gameRoomId) {
        this.gameRoomId = gameRoomId;

        moves = new ArrayList<>();
        grid = new Player[gridSize][gridSize];
    }

    public UUID getGameRoomId() {
        return gameRoomId;
    }

    public void computeGameStatus() {
        if (moves.isEmpty()) {
            gameStatus = GameStatus.RUNNING;
            return;
        }

        Player lastMovePlayer = moves.get(moves.size() - 1).getPlayer();
        int count, filled = 0;
        for (int row = 0; row < grid.length; ++row) {
            count = 0;
            for (int col = 0; col < grid.length; ++col) {
                if (lastMovePlayer == grid[row][col]) {
                    count++;
                }

                if (null != grid[row][col]) {
                    filled++;
                }
            }

            if (grid.length == count) {
                gameStatus = GameStatus.DETERMINED;
                return;
            }
        }

        for (int col = 0; col < grid.length; ++col) {
            count = 0;
            for (int row = 0; row < grid.length; ++row) {
                if (lastMovePlayer == grid[row][col]) {
                    count++;
                }
            }

            if (grid.length == count) {
                gameStatus = GameStatus.DETERMINED;
                return;
            }
        }

        for (int row = 0, col = 0; row < grid.length; ++row, ++col) {
            count = 0;
            if (lastMovePlayer == grid[row][col]) {
                count++;
            }

            if (grid.length == count) {
                gameStatus = GameStatus.DETERMINED;
                return;
            }
        }

        for (int row = grid.length - 1, col = grid.length - 1; row >= 0; --row, --col) {
            count = 0;
            if (lastMovePlayer == grid[row][col]) {
                count++;
            }

            if (grid.length == count) {
                gameStatus = GameStatus.DETERMINED;
                return;
            }
        }

        gameStatus = filled == (grid.length * grid.length) ? GameStatus.UNDETERMINED : GameStatus.RUNNING;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public boolean addMove(Move move) {
        return moves.add(move);
    }

    public List<Move> getMoves() {
        return moves;
    }

    public Player[][] getGrid() {
        return grid;
    }

    public void setNextTurn(Player nextTurn) {
        this.nextTurn = nextTurn;
    }

    public Player getNextTurn() {
        return nextTurn;
    }
}
