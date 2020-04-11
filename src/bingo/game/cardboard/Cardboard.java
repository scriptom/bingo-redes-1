package bingo.game.cardboard;

import bingo.contracts.Renderable;
import bingo.game.BingoValue;

import java.util.ArrayList;
import java.util.List;

public class Cardboard implements Renderable {
    private Square[][] squares;

    private Cardboard(Square[][] squares) {
        this.squares = squares;
    }

    public static Cardboard create() {
        return new Cardboard(generateSquares());
    }

    private static Square[][] generateSquares() {
        Square[][] squares = new Square[5][5];
        List<BingoValue> generated = new ArrayList<>();
        char[] letters = new char[] {'B', 'I', 'N', 'G', 'O'};
        for (int i = 0; i < letters.length; i++) {
            char letter = letters[i];
            for (int j = 0; j < 5; ) {
                if (i == 2 && j == 2) {
                    squares[j++][i] = null;
                } else {
                    BingoValue bingoValue = BingoValue.createRandomForLetter(letter);
                    if (!generated.contains(bingoValue)) {
                        squares[j][i] = new Square(bingoValue);
                        generated.add(bingoValue);
                        j++;
                    }
                }
            }
        }
        return squares;
    }

    public Square[][] getSquares() {
        return squares;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("B I N G O").append('\n');
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                sb.append(squares[i][j]).append(' ');
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    public String render() {
        StringBuilder sb = new StringBuilder("B I N G O").append('\n');
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (squares[i][j] != null) {
                    sb.append(squares[i][j].render());
                } else {
                    sb.append((String) null);
                }
                sb.append(' ');
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    public void checkSquare(int row, int col) {
        squares[row][col].check();
    }

    public void checkIfPresent(BingoValue value) {
        if (value == null) {
            return;
        }

        int col;
        // TODO: Get rid of switch if possible. Maybe using a hashmap?
        switch (value.getLetter()) {
            case 'B': col = 0; break;
            case 'I': col = 1; break;
            case 'N': col = 2; break;
            case 'G': col = 3; break;
            case 'O': col = 4; break;
            default: return;
        }

        for (int i = 0; i < 5; i++) {
            Square square = squares[i][col];
            if (square != null && square.getBingoValue().equals(value)) {
                square.check();
                break;
            }
        }
    }
}
