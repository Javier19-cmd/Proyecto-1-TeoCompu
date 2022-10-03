import java.util.Set;
public class RecorridoRegextoAFD {
    
    private final regextoafdStates q0;
    private regextoafdStates curr;
    private char c;
    private final Set<String> input;
    
    public RecorridoRegextoAFD(regextoafdStates q0, Set<String> input){
        this.q0 = q0;
        this.curr = this.q0;
        this.input = input;
    }
    
    public boolean setCharacter(char c){
        if (!input.contains(c+"")){
            return false;
        }
        this.c = c;
        return true;
    }
    
    public boolean traverse(){
        curr = curr.getNextStateBySymbol(""+c);
        return curr.getIsAcceptable();
    }
}