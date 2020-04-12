package bingo.game;

import bingo.game.cardboard.Cardboard;
import bingo.game.victories.DiagonalVictory;
import bingo.game.victories.HorizontalVictory;
import bingo.game.victories.LinearVictory;
import bingo.game.victories.Victory;

import java.util.Arrays;

public class Bingo {
    public static void main(String[] args) {
        Cardboard cardboard = new Cardboard();
        for (BingoValue row: cardboard.valueRow(0)) cardboard.checkIfPresent(row);
//        int row = 6;
//        for (BingoValue[] col: cardboard.getSquares().values()) cardboard.checkIfPresent(col[row--]);
        Victory victory = new LinearVictory();
        System.out.println("\ncardboard = \n" + cardboard);
        System.out.println("hasBingo = " + victory.hasBingo(cardboard));
    }
}
