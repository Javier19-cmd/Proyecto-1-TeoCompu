import java.util.*;

public class TreeStates {
	private int simbolo; //El ID o label unico del estado del arbol
	private Set<Integer> posiciones;
	private boolean hl;
	private boolean finalState; //Verifica si el estado es uno de aceptacion
	
	public TreeStates(int simbolo) {
		this.setSimbolo(simbolo);
		this.setPosiciones(new HashSet<>());
	}

	public int getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(int simbolo) {
		this.simbolo = simbolo;
	}

	public Set<Integer> getPosiciones() {
		return posiciones;
	}

	public void setPosiciones(Set<Integer> posiciones) {
		this.posiciones = posiciones;
	}

	public boolean isHl() {
		return hl;
	}

	public void setHl(boolean hl) {
		this.hl = hl;
	}

	public boolean isFinalState() {
		return finalState;
	}
	//Si posiciones contiene la posicion INT enviada desde afuera, entonces es un estado final.
	public void setFinalState(int posicion) {
		if(posiciones.contains(posicion)) {
			this.finalState = true;
		}else {
			this.finalState = false;
		};
	}
}
