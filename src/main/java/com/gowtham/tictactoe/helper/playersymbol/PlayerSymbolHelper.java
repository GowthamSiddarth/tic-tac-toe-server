package com.gowtham.tictactoe.helper.playersymbol;

import com.gowtham.tictactoe.constants.PlayerSymbol;

public class PlayerSymbolHelper {

    public static PlayerSymbol getComplimentarySymbol(PlayerSymbol playerSymbol) {
        return PlayerSymbol.CROSS == playerSymbol ? PlayerSymbol.CIRCLE : PlayerSymbol.CROSS;
    }
}
