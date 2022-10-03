import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class StateAFD {
    
    private int ID;
    private Set<Integer> name;
    private HashMap<String, StateAFD> move;
    
    private boolean IsAcceptable;
    private boolean IsMarked;
    
    public StateAFD(int ID){
        this.ID = ID;
        move = new HashMap<>();
        name = new HashSet<>();
        IsAcceptable = false;
        IsMarked = false;
    }
    
    public void addMove(String symbol, StateAFD s){
        move.put(symbol, s);
    }
    
    public void addToName(int number){
        name.add(number);
    }
    public void addAllToName(Set<Integer> number){
        name.addAll(number);
    }
    
    public void setIsMarked(boolean bool){
        IsMarked = bool;
    }
    
    public boolean getIsMarked(){
        return IsMarked;
    }
    
    public Set<Integer> getName(){
        return name;
    }

    public void setAccept() {
        IsAcceptable = true;
    }
    
    public boolean getIsAcceptable(){
        return  IsAcceptable;
    }
    
    public StateAFD getNextStateBySymbol(String str){
        return this.move.get(str);
    }
    
    public HashMap<String, StateAFD> getAllMoves(){
        return move;
    }
    
}