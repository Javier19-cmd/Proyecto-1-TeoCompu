import java.util.*;

public class AFDConversion {
	private ArrayList<TreeStates> estados; //Lista de estados
	private Set<Character> alfabeto; //El alfabeto a usar, por ejemplo a,b
	private Set<Integer> primPosRaiz, nextPos[];
	private Hashtable<Character,Integer> minimizedD[]; //Hashtable de los estados a guardar y a minimizar
    private Hashtable<Character, Set<Integer>> pos;
    private int extremos = 0; //Contador
    private int cestados = 0; //Contador
    
    @SuppressWarnings("unchecked")
	public AFDConversion(ArbolSintactico as, String regex) {
    	this.pos = as.getPos();
    	this.nextPos = as.getNextPos();
    	this.primPosRaiz = as.getRoot().getPrimPos();
    	this.extremos = as.getExtremos();
    	this.minimizedD = new Hashtable[this.extremos + 1];
    	for(int i = 0; i<=this.extremos; i++) {
    		minimizedD[i] = new Hashtable<>();
    	}
    	
    }
    public Set<Character> getAlfabeto(){
        return this.alfabeto;
    }
    public Set<Integer> getEstados(int simbolo){ //Getter de los estados
        return estados.get(simbolo - 1).getPosiciones();
    }
    public int getExtremos(){
        return this.extremos;
    }

	public int getCestados() {
		return cestados;
	}

	public void setCestados(int cestados) {
		this.cestados = cestados;
	}
	public Hashtable<Character, Integer>[] minimizedD(){
		return this.minimizedD;
	}
	public boolean esFinal(int label){
        return this.estados.get(label - 1).isFinalState();
    }
	private boolean Caracter(char simbolo){ //Verificar que el caracter es letra o digito o # regresa true
        return Character.isLetterOrDigit(simbolo) || simbolo == '#';
    }
	 public void crearAFD(){
	      /**
	       * Creacion de AFD usando el arbol sintactico. El codigo
	       * de esta parte se elimino antes de subirlo debido a que no era
	       * compatible (daba error) por lo que se queria buscar otra logica
	       * para crear un AFD.
	       */
	 }
	    
	 private boolean deepSearch(String str, int i, int estado){
		 /**
		  * Lo mismo en esta funcion. Se planea usar el Deep search algorithm
		  * pero aunque al principio funcionaba, ahora daba error.
		  */
		 return false;
	 }
	    
	 public boolean Replace(String str){
		 str = str.replace("&", "");
	     return deepSearch(str, 0, 1);
	  }
}
