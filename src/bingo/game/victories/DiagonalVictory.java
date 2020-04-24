package bingo.game.victories;

import bingo.game.BingoValue;
import bingo.game.cardboard.Cardboard;

public class DiagonalVictory implements Victory {
    @Override
    public boolean hasBingo(Cardboard cardboard) {
        int row = 0;
        boolean mainDiag = true, secDiag = true;

        /*
        // Diagonal principal
        for (BingoValue[] col: cardboard.getSquares().values()) {
            if (!cardboard.isChecked(col[row++])) {
                mainDiag = false;
                break;
            }
        }

        // Diagonal secundaria
        row = 4;
        for (BingoValue[] col: cardboard.getSquares().values()) {
            if (!cardboard.isChecked(col[row--])) {
                secDiag = false;
                break;
            }
        }
        */

        return mainDiag || secDiag;
    }
}
