import java.util.*;

public class TreeStates {
	private int simbolo;
	private Set<Integer> posiciones;
	private boolean hl;
	private boolean finalState;
	
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

	public void setFinalState(int posicion) {
		if(posiciones.contains(posicion)) {
			this.finalState = true;
		}else {
			this.finalState = false;
		};
	}
}
