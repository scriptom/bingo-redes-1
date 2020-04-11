package bingo.game;

import bingo.game.cardboard.Cardboard;

public class Bingo {
    public static void main(String[] args) {
        Cardboard cardboard = Cardboard.create();
        BingoValue toCheck = BingoValue.createRandomForLetter('B');
        cardboard.checkIfPresent(toCheck);
        System.out.println("toCheck = " + toCheck + "\ncardboard = \n" + cardboard.render());
    }
}
