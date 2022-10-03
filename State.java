import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class State {
    
    private int ID;
    private Set<Integer> nombre;
    private HashMap<String, State> move;
    
    private boolean IsAcceptable;
    private boolean IsMarked;
    
    public State(int ID){
        this.ID = ID;
        move = new HashMap<>();
        nombre = new HashSet<>();
        IsAcceptable = false;
        IsMarked = false;
    }
    
    public void move(String symbol, State s){
        move.put(symbol, s);
    }
    
    public void asignarNombre(int number){
        nombre.add(number);
    }
    public void asignarTodoNombre(Set<Integer> number){
        nombre.addAll(number);
    }
    
    public void setIsMarked(boolean bool){ //Para marcar los estados de aceptación
        IsMarked = bool;
    }
    
    public boolean getIsMarked(){ //Para marcar los estados de aceptación
        return IsMarked;
    }
    
    public Set<Integer> getName(){
        return nombre;
    }

    public void setAccept() { // Para setear los estados de aceptación
        IsAcceptable = true;
    }
    
    public boolean getIsAcceptable(){ // Obtener los estados de aceptación
        return  IsAcceptable;
    }
    
    public State siguienteEstadoConSimbolo(String str){
        return this.move.get(str);
    }
    
    public HashMap<String, State> getAllMoves(){
        return move;
    }
    
}
