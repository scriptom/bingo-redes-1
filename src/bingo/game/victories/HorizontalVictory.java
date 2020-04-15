package game.victories;

import bingo.game.BingoValue;
import game.cardboard.Cardboard;

public class HorizontalVictory implements Victory {
    @Override
    public boolean hasBingo(Cardboard cardboard) {
        // TODO: Aqui se podria aplicar la vecindad y devolver el resultado final.
        //      Esto es solo una prueba rapida
        for (int i = 0; i < 5; i++) {
            int checksNeeded = 5;
            for (BingoValue value: cardboard.valueRow(i)) {
                if (cardboard.isChecked(value)) {
                    --checksNeeded;
                }
            }
            if (checksNeeded == 0) {
                return true;
            }
        }

        return false;
    }
}
