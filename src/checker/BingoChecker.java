package checker;

import java.util.ArrayList;

/**
 *
 * @author Daniel Luis
 */
public abstract class BingoChecker {
    protected boolean hasBingo = false;
    protected ArrayList<String>[] plays;
    
    public void removePosition(String position){
        for(ArrayList<String> play : plays){
            if(play.contains(position)){
                play.remove(position);
            }
        }
        validateBingo();
    }
    
    protected void validateBingo(){
        for(ArrayList<String> play : plays){
            if(play.isEmpty()){
                this.hasBingo = true;
                return;
            }
        }
        this.hasBingo = false;
    }
    
    public boolean bingo(){
        return this. hasBingo;
    }
    
}
