package bingo.game.cardboard;

import bingo.game.BingoValue;

public class Square{
    private BingoValue bingoValue;
    private boolean checked;

    public Square(BingoValue bingoValue) {
        this.bingoValue = bingoValue;
        checked = false;
    }

    public BingoValue getBingoValue() {
        return bingoValue;
    }

    public void check() {
        checked = true;
    }

    public boolean isChecked() {
        return checked;
    }

    @Override
    public String toString() {
        return bingoValue.toString();
    }
    
    public String render() {
        StringBuilder sb = new StringBuilder();
        if (checked) {
            // Set text color to bright green
            sb.append((char)27).append("[92m");
        }
        sb.append(bingoValue.getNumber());
        if (checked) {
            // Reset color attributes
            sb.append((char) 27).append("[0m");
        }
        return sb.toString();
    }
}
