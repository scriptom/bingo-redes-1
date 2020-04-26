package bingo.game.checker;

import java.util.ArrayList;

/**
 *
 * @author Daniel Luis
 */
public abstract class BingoChecker {
    /**
     * Indicador de si hay bingo o no
     */
    protected boolean hasBingo = false;

    /**
     * Lista con las jugadas posibles para un tipo de bingo
     */
    protected ArrayList<ArrayList<String>> plays;

    /**
     * Quita de la lista de posibles bingos la posicion introducida
     * @param position Posicion de la casilla marcada
     */
    public void removePosition(String position){
        for(ArrayList<String> play : plays){
            if(play.contains(position)){
                play.remove(position);
            }
        }
        validateBingo();
    }

    /**
     * Valida si hay alguno de los posiblis bingos vacios, en tal caso coloca hasBingo es true
     */
    protected void validateBingo(){
        for(ArrayList<String> play : plays){
            if(play.isEmpty()){
                this.hasBingo = true;
                return;
            }
        }
        this.hasBingo = false;
    }

    /**
     *
     * @return Si existe bingo en la jugada
     */
    public boolean bingo(){
        return this. hasBingo;
    }
    
}
