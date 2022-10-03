import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class regextoafdStates{
    
    private int ID;
    private Set<Integer> name;
    private HashMap<String, regextoafdStates> move;
    
    private boolean IsAcceptable;
    private boolean IsMarked;
    
    public regextoafdStates(int ID){
        this.ID = ID;
        move = new HashMap<>();
        name = new HashSet<>();
        IsAcceptable = false;
        IsMarked = false;
    }
    
    public void addMove(String symbol, regextoafdStates s){
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
    
    public regextoafdStates getNextStateBySymbol(String str){
        return this.move.get(str);
    }
    
    public HashMap<String, regextoafdStates> getAllMoves(){
        return move;
    }
    
}
