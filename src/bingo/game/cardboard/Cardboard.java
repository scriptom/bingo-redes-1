package bingo.game.cardboard;

import bingo.game.checker.BingoChecker;

import java.util.*;

public class Cardboard {
    /**
     * Lista de cuadros en el carton
     */
    private LinkedHashMap<String, BingoValue> squares;

    /**
     * Posibles bingos para el carton
     */
    private BingoChecker bingoChecker;

    /**
     * Posiciones posibles del carton
     */
    private String[] positions = {"B1", "B2", "B3", "B4", "B5", 
                                  "I1", "I2", "I3", "I4", "I5", 
                                  "N1", "N2", "N3", "N4", "N5", 
                                  "G1", "G2", "G3", "G4", "G5", 
                                  "O1", "O2", "O3", "O4", "O5"};

    public Cardboard(BingoChecker checker) {
        //squares = new LinkedHashMap<>();
        squares = new LinkedHashMap<>();
        this.bingoChecker = checker;
        //checked = new HashMap<>();
        populate();
        checkIfPresent("N3");
    }

    /**
     * Asignar un valor de cada cuadro de el carton
     */
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
    }

    /**
     *
     * @return Lista con todos los cuadros del carton
     */
    public LinkedHashMap<String, BingoValue> getSquares(){
        return squares;
    }

    /**
     * Imprimir carton en consola
     * @return String con el carton
     */
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

    /**
     *
     * @return Si existe bingo en el carton o no
     */
    public boolean checkBingo(){
        return this.bingoChecker.bingo();
    }

    /**
     * Marca un cuaadro en caso de que exista la posicion
     * @param position posicion del cuadro a marcar
     * @return true si existe, false en caso contrario
     */
    public boolean checkIfPresent(String position) {
        if (position == null || position == "") {
            return false;
        }
        BingoValue value = squares.get(position);
        if (value != null) {
            if (!value.isChecked()) {
                value.check();
                this.bingoChecker.removePosition(position);
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve el numero para un posicion dada
     * @param position posicion del cuadro del que se quiere saber el numero
     * @return numero del cuadro
     */
    public int getNumber (String position){
        return this.squares.get(position).getNumber();
    }
}
