package bingo.game.cardboard;

import bingo.contracts.Renderable;
import bingo.game.Bingo;
import bingo.game.BingoValue;

import java.util.*;

public class Cardboard {
    private Map<Character, BingoValue[]> squares;
    private Map<Character, List<Integer>> checked;

    public Cardboard() {
        squares = new LinkedHashMap<>();
        checked = new HashMap<>();
        populate();
    }

    private void populate() {
        for (char letter : new char[]{'B', 'I', 'N', 'G', 'O'}) {
            checked.put(letter, new ArrayList<>(5));
            BingoValue[] squares = new BingoValue[5];
            List<BingoValue> blacklist = new ArrayList<>();
            for (int uniques = 0; uniques < 5; ) {
                BingoValue value = BingoValue.createRandomForLetter(letter);
                if (!blacklist.contains(value)) {
                    blacklist.add(value);
                    squares[uniques] = value;
                    uniques++;
                }
            }
            this.squares.put(letter, squares);
        }
    }


    public Map<Character, BingoValue[]> getSquares() {
        return squares;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("B I N G O").append('\n');
        for (int i = 0; i < 5; i++) {
            for (BingoValue value: valueRow(i)) {
                boolean checked = isChecked(value);
                if (checked) {
                    sb.append((char)27).append("[92m");
                }
                sb.append(value.getNumber()).append(' ');
                if (checked) {
                    sb.append((char)27).append("[0m");
                }
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    public void checkIfPresent(BingoValue value) {
        if (value == null) {
            return;
        }

//        int col;

        // Obtenemos el indice de la casilla (si existe)
        int index = indexOf(value);
        if (index != -1) {
            // De ser asi, agregamos el indice de la casilla en una lista de seleccionados
            checked.get(value.getLetter()).add(value.getNumber());
        }
//        for (int row = 0; row < 5; row++) {
//            Square square = this.squares[row][col];
//            if (square != null && square.getBingoValue().equals(value) && !square.isChecked()) {
//                square.check();
//                break;
//            }
//        }
    }

    @SuppressWarnings("WeakerAccess")
    public int indexOf(BingoValue value) {
        BingoValue[] values = squares.get(value.getLetter());
        for (int i = 0; i < 5; i++) {
            if (values[i] != null && values[i].equals(value)) {
                return i;
            }
        }

        return -1;
    }

    public boolean isChecked(BingoValue value) {
        return checked.get(value.getLetter()).contains(value.getNumber());
    }

    public BingoValue[] valueRow(int index) {
        BingoValue[] row = new BingoValue[5];
        int i = 0;
        for (char letter: squares.keySet()) {
            row[i++] = squares.get(letter)[index];
        }

        return row;
    }

    public BingoValue[] valueCol(char letter) {
        return squares.get(letter);
    }
}
