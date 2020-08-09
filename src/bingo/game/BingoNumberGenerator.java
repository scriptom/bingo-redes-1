package bingo.game;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BingoNumberGenerator {
    private List<Integer> numbers = IntStream.rangeClosed(1, 75).boxed().collect(Collectors.toList());;
    private int numberOfValues = 74;

    public int getNumberToPlay() {
        int position = (int) ((Math.random() * (numberOfValues - 1)) + 1);
        numberOfValues-=1;
        return numbers.remove(position);
    };

    public void resetNumbers() {
        numberOfValues = 74;
        numbers = IntStream.rangeClosed(1, 75).boxed().collect(Collectors.toList());
    }
}
