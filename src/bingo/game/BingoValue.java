package bingo.game;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class BingoValue {
    private char letter;
    private boolean check;
    private int number;
    private static final HashMap<Character, Integer> LETTERS_OFFSETS = new HashMap<>();

    static {
        LETTERS_OFFSETS.put('B', 1);
        LETTERS_OFFSETS.put('I', 16);
        LETTERS_OFFSETS.put('N', 31);
        LETTERS_OFFSETS.put('G', 46);
        LETTERS_OFFSETS.put('O', 61);
    }

    private BingoValue(char letter, int number) {
        this.letter = letter;
        this.number = number;
        this.check = false;
    }

    public boolean isChecked(){
        return this.check;
    }

    public void check(){
        this.check = true;
    }

    public void uncheck(){
        this.check = false;
    }

    public char getLetter() {
        return letter;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.valueOf(letter) + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BingoValue)) return false;

        BingoValue that = (BingoValue) o;

        if (letter != that.letter) return false;
        return number == that.number;
    }

    public static BingoValue createRandomForLetter(char c) {
        char letter = Character.toUpperCase(c);
        if (!LETTERS_OFFSETS.containsKey(letter)) return null;
        Random random = new Random(new Date().getTime());
        Integer offset = LETTERS_OFFSETS.get(letter);
        int number = random.nextInt(15 ) + offset;

        return new BingoValue(letter, number);
    }
}
