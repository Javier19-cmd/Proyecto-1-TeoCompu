import java.util.*;

public class AFDConversion {
	private Set<Character> alfabeto; //El alfabeto a usar, por ejemplo a,b
	private Set<Integer> primPosRaiz, nextPos[];
	private Hashtable<Character,Integer> minimizedD[]; //Hashtable de los estados a guardar y a minimizar
    private Hashtable<Character, Set<Integer>> pos;
    private int extremos = 0; //Contador
    private int cestados = 0; //Contador
    

    public Set<Character> getAlfabeto(){
        return this.alfabeto;
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
