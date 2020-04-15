package bingo.game.victories;

import bingo.game.cardboard.Cardboard;

public class LinearVictory implements Victory {
    @Override
    public boolean hasBingo(Cardboard cardboard) {
        return new HorizontalVictory().hasBingo(cardboard) || new DiagonalVictory().hasBingo(cardboard);
    }
}
