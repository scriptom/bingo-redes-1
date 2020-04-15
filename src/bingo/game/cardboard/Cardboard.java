package bingo.game.cardboard;

import bingo.game.Bingo;
import bingo.game.BingoValue;
import bingo.game.victories.Victory;
import checker.BingoChecker;

import java.util.*;

public class Cardboard {
    private HashMap<String, BingoValue> squares;
    private BingoChecker bingoChecker;
    //private Map<Character, BingoValue[]> squares;
    //private Map<Character, List<Integer>> checked;
    private String[] positions = {"B1", "B2", "B3", "B4", "B5", 
                                  "I1", "I2", "I3", "I4", "I5", 
                                  "N1", "N2", "N3", "N4", "N5", 
                                  "G1", "G2", "G3", "G4", "G5", 
                                  "O1", "O2", "O3", "O4", "O5"};

    public Cardboard(BingoChecker checker) {
        //squares = new LinkedHashMap<>();
        squares = new HashMap<>();
        this.bingoChecker = checker;
        //checked = new HashMap<>();
        populate();
        squares.get("N3").check();;
    }

    private void populate() {
        List<BingoValue> blacklist = new ArrayList<>();
        for (String p : positions){
            while(true){
                BingoValue value = BingoValue.createRandomForLetter(p.charAt(0));
                if (!blacklist.contains(value)) {
                    blacklist.add(value);
                    this.squares.put(p, value);
                    break;
                }
            }
        }
        // for (char letter : new char[]{'B', 'I', 'N', 'G', 'O'}) {
        //     checked.put(letter, new ArrayList<>(5));
        //     BingoValue[] squares = new BingoValue[5];
        //     List<BingoValue> blacklist = new ArrayList<>();
        //     for (int uniques = 0; uniques < 5; ) {
        //         BingoValue value = BingoValue.createRandomForLetter(letter);
        //         if (!blacklist.contains(value)) {
        //             blacklist.add(value);
        //             squares[uniques] = value;
        //             uniques++;
        //         }
        //     }
        //     this.squares.put(letter, squares);
        // }
    }

    public HashMap<String, BingoValue> getSquares(){
        return squares;
    }
    
    public String render(){
        String print = "";
        for(int i=1; i<=5; i++){
            System.out.println("");
            if(squares.get("B"+i).isChecked()){
                print += "\033[31m"+squares.get("B"+i).getNumber()+" ";
            }else{
                print += "\033[37m"+squares.get("B"+i).getNumber()+" ";
            }
            if(squares.get("I"+i).isChecked()){
                print += "\033[31m"+squares.get("I"+i).getNumber()+" ";
            }else{
                print += "\033[37m"+squares.get("I"+i).getNumber()+" ";
            }
            if(squares.get("N"+i).isChecked()){
                print += "\033[31m"+squares.get("N"+i).getNumber()+" ";
            }else{
                print += "\033[37m"+squares.get("N"+i).getNumber()+" ";
            }
            if(squares.get("G"+i).isChecked()){
                print += "\033[31m "+squares.get("G"+i).getNumber()+" ";
            }else{
                print += "\033[37m"+squares.get("G"+i).getNumber()+" ";
            }
            if(squares.get("O"+i).isChecked()){
                print += "\033[31m "+squares.get("O"+i).getNumber()+" ";
            }else{
                print += "\033[37m"+squares.get("O"+i).getNumber()+" ";
            }
            print += "\n";
        }
        return print;
    }
    
    public boolean checkBingo(String position){
        this.bingoChecker.removePosition(position);
        return this.bingoChecker.bingo();
    }


    // public Map<Character, BingoValue[]> getSquares() {
    //     return squares;
    // }

    /*
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
    */

    public boolean checkIfPresent(String position) {
        if (position == null || position == "") {
            return false;
        }
        BingoValue value = squares.get(position);
        if(value != null){
            if(!value.isChecked()){
                value.check();
                return true;
            }
        }
        return false;
//        int col;

        // Obtenemos el indice de la casilla (si existe)
        // int index = indexOf(value);
        // if (index != -1) {
        //     // De ser asi, agregamos el indice de la casilla en una lista de seleccionados
        //     checked.get(value.getLetter()).add(value.getNumber());
        // }
//        for (int row = 0; row < 5; row++) {
//            Square square = this.squares[row][col];
//            if (square != null && square.getBingoValue().equals(value) && !square.isChecked()) {
//                square.check();
//                break;
//            }
//        }
    }
    
    /*
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
    */
}
