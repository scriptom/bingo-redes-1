package checker;

import java.util.ArrayList;

/**
 *
 * @author Daniel Luis
 */
public class FullChecker extends BingoChecker {
    
    public FullChecker(){
        ArrayList<String> full = new ArrayList<>();
        full.add("B1");
        full.add("B2");
        full.add("B3");
        full.add("B4");
        full.add("B5");
        full.add("I1");
        full.add("I2");
        full.add("I3");
        full.add("I4");
        full.add("I5");
        full.add("N1");
        full.add("N2");
        full.add("N3");
        full.add("N4");
        full.add("N5");
        full.add("G1");
        full.add("G2");
        full.add("G3");
        full.add("G4");
        full.add("G5");
        full.add("O1");
        full.add("O2");
        full.add("O3");
        full.add("O4");
        full.add("O5");
        
        this.plays = new ArrayList<>();
        plays.add(full);
    }
    
}
