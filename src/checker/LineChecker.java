package checker;

import java.util.ArrayList;

/*
 *
 * @author Daniel Luis
 */
public class LineChecker extends BingoChecker{
    
    public LineChecker(){
//        String[] horizontal_1 = {"B1", "I1", "N1", "G1", "O1"};
//        String[] horizontal_2 = {"B2", "I2", "N2", "G2", "O2"};
//        String[] horizontal_3 = {"B3", "I3", "N3", "G3", "O3"};
//        String[] horizontal_4 = {"B4", "I4", "N4", "G4", "O4"};
//        String[] horizontal_5 = {"B5", "I5", "N5", "G5", "O5"};
//        String[] vertical_1 = {"B1", "B2", "B3", "B4", "B5"};
//        String[] vertical_2 = {"I1", "I2", "I3", "I4", "I5"};
//        String[] vertical_3 = {"N1", "N2", "N3", "N4", "N5"};
//        String[] vertical_4 = {"G1", "G2", "G3", "G4", "G5"};
//        String[] vertical_5 = {"O1", "O2", "O3", "O4", "O5"};
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
        this.plays = {horizontal_1, horizontal_2, horizontal_3, horizontal_4, horizontal_5, vertical_1, vertical_2, vertical_3, vertical_4, vertical_5};
    }  
    
}
