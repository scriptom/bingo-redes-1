package bingo.game.checker;

import java.util.ArrayList;

/*
 *
 * @author Daniel Luis
 */
public class LineChecker extends BingoChecker{
    
    public LineChecker(){
        ArrayList<String> horizontal_1 = new ArrayList<>();
        horizontal_1.add("B1");
        horizontal_1.add("I1");
        horizontal_1.add("N1");
        horizontal_1.add("G1");
        horizontal_1.add("O1");
        ArrayList<String> horizontal_2 = new ArrayList<>();
        horizontal_2.add("B2");
        horizontal_2.add("I2");
        horizontal_2.add("N2");
        horizontal_2.add("G2");
        horizontal_2.add("O2");
        ArrayList<String> horizontal_3 = new ArrayList<>();
        horizontal_3.add("B3");
        horizontal_3.add("I3");
        horizontal_3.add("N3");
        horizontal_3.add("G3");
        horizontal_3.add("O3");
        ArrayList<String> horizontal_4 = new ArrayList<>();
        horizontal_4.add("B4");
        horizontal_4.add("I4");
        horizontal_4.add("N4");
        horizontal_4.add("G4");
        horizontal_4.add("O4");
        ArrayList<String> horizontal_5 = new ArrayList<>();
        horizontal_5.add("B5");
        horizontal_5.add("I5");
        horizontal_5.add("N5");
        horizontal_5.add("G5");
        horizontal_5.add("O5");
        ArrayList<String> vertical_1 = new ArrayList<>();
        vertical_1.add("B1");
        vertical_1.add("B2");
        vertical_1.add("B3");
        vertical_1.add("B4");
        vertical_1.add("B5");
        ArrayList<String> vertical_2 = new ArrayList<>();
        vertical_2.add("I1");
        vertical_2.add("I2");
        vertical_2.add("I3");
        vertical_2.add("I4");
        vertical_2.add("I5");
        ArrayList<String> vertical_3 = new ArrayList<>();
        vertical_3.add("N1");
        vertical_3.add("N2");
        vertical_3.add("N3");
        vertical_3.add("N4");
        vertical_3.add("N5");
        ArrayList<String> vertical_4 = new ArrayList<>();
        vertical_4.add("G1");
        vertical_4.add("G2");
        vertical_4.add("G3");
        vertical_4.add("G4");
        vertical_4.add("G5");
        ArrayList<String> vertical_5 = new ArrayList<>();
        vertical_5.add("O1");
        vertical_5.add("O2");
        vertical_5.add("O3");
        vertical_5.add("O4");
        vertical_5.add("O5");

        this.plays = new ArrayList<>();
        plays.add(horizontal_1);
        plays.add(horizontal_2);
        plays.add(horizontal_3);
        plays.add(horizontal_4);
        plays.add(horizontal_5);
        plays.add(vertical_1);
        plays.add(vertical_2);
        plays.add(vertical_3);
        plays.add(vertical_4);
        plays.add(vertical_5);
    }  
    
}
