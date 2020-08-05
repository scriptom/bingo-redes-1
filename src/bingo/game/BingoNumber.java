package bingo.game;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BingoNumber {
    public static List<Integer> numbers = IntStream.rangeClosed(1, 75).boxed().collect(Collectors.toList());;
    public static int numberOfValues = 74;

    public static int getNumberToPlay() {
        int position = (int) ((Math.random() * (numberOfValues - 1)) + 1);
        numberOfValues-=1;
        return numbers.remove(position);
    };

    public static void resetNumbers() {
        numberOfValues = 74;
        numbers = IntStream.rangeClosed(1, 75).boxed().collect(Collectors.toList());
    }
}
