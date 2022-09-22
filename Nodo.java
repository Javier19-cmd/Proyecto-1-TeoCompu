import java.util.HashSet;
import java.util.Set;

public class Nodo {
	public String simbolo;
	private Nodo prev;
	private Nodo par;
	private Nodo next;
	private Set<Integer> primera_pos, ultima_pos;
	private int pos;
	private boolean nullable;
	private int h;
	private int index;
	
	public Nodo(String simbolo) {
		this.simbolo = simbolo;
		prev = null;
		par = null;
		next = null;
		this.primera_pos = new HashSet<>();
		this.ultima_pos = new HashSet<>();
		h = 0;
		index = 0;
	}
	
	public String getSimbolo() {
		return simbolo;
	}
	
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	
	public Nodo getNextNodo() {
		return this.next;
	}
	
	public void setNextNodo(Nodo next) {
		this.next = next;
	}
	
	public Nodo getPrevNodo() {
		return this.prev;
	}
	
	public void setPrevNodo(Nodo prev) {
		this.prev = prev;
	}
	
	public Nodo getParNodo() {
		return this.par;
	}
	
	public void setParNodo(Nodo par) {
		this.par = par;
	}
	
	public int getPosicion() {
        return pos;
    }

    public void setPosicion(int pos) {
        this.pos = pos;
    }
    
    public void addPrimPos(int num) {
    	this.primera_pos.add(num);
    }
    
    public void addUltPos(int num) {
    	this.ultima_pos.add(num);
    }
    
    public Set<Integer> getPrimPos(){
        return this.primera_pos;
    }
    
    public void setPrimPos(Set<Integer> primera_pos){
        this.primera_pos = primera_pos;
    }
    
    public Set<Integer> getUltPos(){
        return this.ultima_pos;
    }
    
    public void setUltPos(Set<Integer> ultima_pos){
        this.ultima_pos = ultima_pos;
    }
    
    public boolean esAnulable() {
        return nullable;
    }
    
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
    public int getNumHijos() {
        return h;
    }

    public void setNumHijos(int h) {
        this.h += h;
    }
    
    public boolean esHoja(){
        return next == null && prev == null;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
    
