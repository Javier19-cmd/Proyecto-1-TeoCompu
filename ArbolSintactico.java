/*
 * Referencia de como armar un arbol sintactico:
 * https://github.com/Jvillegasd/RegexToDFA/blob/master/Arbol%20sintactico/src/arbol/sintactico/ArbolSintactico.java
 */

import java.util.*;

public class ArbolSintactico {
	private String regex;
	private Nodo root;
	private Set<Integer> nextPos[];
	private Hashtable<Character, Set<Integer>> pos = null;
	private int extremos;
	private int index;
	 
	@SuppressWarnings("unchecked")
	public ArbolSintactico(String regex) {
		 this.regex = "(" + regex + ")#";
		 this.pos = new Hashtable<>();
		 this.root = new Nodo('.');
		 this.extremos = 0;
		 this.index = 0; 
		 for(int i = 0; i < regex.length(); i++){
	            if(Caracteres(regex.charAt(i))&& regex.charAt(i) != '&'){
	                if(pos.get(regex.charAt(i)) == null) pos.put(regex.charAt(i), new HashSet<>());
	                pos.get(regex.charAt(i)).add(++extremos);
	            }
	     }
	     nextPos = new Set[extremos+1];
	     for (int i = 0; i < extremos; i++) {
	    	 nextPos[i] = new HashSet<>();
	     }
	}
	// Operadores: |, ., +, *, (, )
	private boolean Operadores(char charAt) {
		return charAt == '|' || charAt == '.' || charAt == '+' || charAt == '*'
				|| charAt == '(' || charAt == ')';
	}
	private boolean Caracteres(char charAt) {
		return Character.isLetterOrDigit(charAt) || charAt == '&' || charAt == '#';
	}
	public Nodo getRoot() {
		return this.root;
	}
	public void setRoot(Nodo root) {
		this.root = root;
	}
	public int getIndex() {
		return this.index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getExtremos(){
        return this.extremos;
    }
	public Hashtable<Character, Set<Integer>>  getPos(){
	        return this.pos;
	}
	public void showNextPos() {
        for (int i = 0; i < nextPos.length; i++) {
            Object o[] = nextPos[i].toArray();
        }
    }
    public Set<Integer>[] getNextPos() {
        return this.nextPos;
    } 
    
    public void generateTree(){
        regex post = new regex();
        String r = "";
        r = r + "#";
        @SuppressWarnings("static-access")
		String post_value = post.evaluar(r);
        postfixToTree(post_value);
    }
	private void postfixToTree(String post_value) {
		Stack<Nodo> n = new Stack<>();
        Nodo act, next, prev;
        int j = 1;
        for(int i = 0; i < post_value.length(); i++){
            char simbolo = post_value.charAt(i);
            if(!Character.isLetterOrDigit(simbolo)|| simbolo == '&' || simbolo == '#'){
            	act = new Nodo(simbolo);
                if(simbolo != '&') {
                	act.setPosicion(j++);
                }
                n.push(act);
            }else{
            	act = new Nodo(simbolo);
                if(simbolo == '*' || simbolo == '+' || simbolo == '?'){
                	prev = n.pop();
                	act.setPrevNodo(prev);
                }else{
                	next = n.pop();
                	prev = n.pop();
                	act.setNextNodo(next);
                	act.setPrevNodo(prev);
                }
                n.push(act);
            }
        }
        root = n.pop();
		
	}
    private void generatePrevAndNextPos(Nodo nodo) {
    	if (nodo == null) {
            return;
        }
    	 if(Caracteres(nodo.getSimbolo()) && nodo.getSimbolo() != '&'){
    		 nodo.setNullable(false);
    		 nodo.addPrimPos(nodo.getPosicion());
    		 nodo.addUltPos(nodo.getPosicion());
         } else if(Operadores(nodo.getSimbolo())){
             boolean null1, null2;
             switch(nodo.getSimbolo()){
                 case '|':
                	 union(nodo);
                     break;
                 case '.':
                	 concatenation(nodo);
                     break;
                 case '*':
                	 star(nodo);
                	 break;
                 case '+':
                	 cruz(nodo);
                     break;
             }
         }
    }
    private void union(Nodo nodo) {
    	boolean null1, null2;
    	null1 = nodo.getPrevNodo().isNullable();
        null2 = nodo.getNextNodo().isNullable();
        nodo.setNullable(null1 | null2);
        generateUnion(nodo, true);
        generateUnion(nodo, false);
    }
    private void concatenation(Nodo nodo) {
    	boolean null1, null2;
    	null1 = nodo.getPrevNodo().isNullable();
   	 	null2 = nodo.getNextNodo().isNullable();
        nodo.setNullable(null1 & null2);
        if(null1) {
        	generateUnion(nodo, true);
        }else {
        	nodo.setPrimPos(nodo.getPrevNodo().getPrimPos());
        }
        if(null2) {
        	generateUnion(nodo, false);
        }else {
        	nodo.setUltPos(nodo.getNextNodo().getUltPos());
        }
    }
    private void star(Nodo nodo) {
    	boolean null1, null2;
    	null1 = nodo.getPrevNodo().isNullable();
   	 	null2 = nodo.getNextNodo().isNullable();
        nodo.setNullable(null1 & null2);
        /**
         * se deberia poner el nodo next como nodo, y el prev como null
         * generateUnion(nodo, false);
         * Falta logica
         */
        nodo.setPrimPos(nodo.getPrevNodo().getPrimPos());
   	 	nodo.setUltPos(nodo.getPrevNodo().getUltPos());
        if(nodo.getSimbolo() == '+') {
        	nodo.setNullable(false);
        }
    }
    private void cruz(Nodo nodo) {
    	nodo.setPrimPos(nodo.getPrevNodo().getPrimPos());
   	 	nodo.setUltPos(nodo.getPrevNodo().getUltPos());
        if(nodo.getSimbolo() == '+') {
        	nodo.setNullable(false);
        }
    }
    private void generateUnion(Nodo nodo, boolean b) {
		// TODO Auto-generated method stub
		if(b){
            Nodo prev = nodo.getPrevNodo();
            Nodo next = nodo.getNextNodo();
            if(prev != null){
                for(int posicion : prev.getPrimPos()) nodo.addPrimPos(posicion);
            }
            if(next != null){
                for(int posicion : next.getPrimPos()) nodo.addPrimPos(posicion);
            }
        }else{
            Nodo prev = nodo.getPrevNodo();
            Nodo next = nodo.getNextNodo();
            if(prev != null){
                for(int posicion : prev.getUltPos()) nodo.addUltPos(posicion);
            }
            if(next != null){
                for(int posicion : next.getUltPos()) nodo.addUltPos(posicion);
            }
        }	
	}
	private void generateNextPos(Nodo nodo) {
		Set<Integer> ultpos1 = nodo.getPrevNodo().getUltPos();
        switch(nodo.getSimbolo()){
            case '.':
                Set<Integer> primpos2 = nodo.getNextNodo().getPrimPos();
                for(Integer i : ultpos1) nextPos[i].addAll(primpos2);
                break;
            case '*':
            case '+':
                Set<Integer> primpos1 = nodo.getPrevNodo().getPrimPos();
                for(Integer i : ultpos1) nextPos[i].addAll(primpos1);
                break;
        }
	}
	private void generateCalc(Nodo nodo){
        if(nodo == null) return;
        Nodo prev = nodo.getPrevNodo();
        Nodo next = nodo.getNextNodo();
        generateCalc(prev);
        generateCalc(next);
        nodo.setIndex(++index);
        generatePrevAndNextPos(nodo);
        if(Operadores(nodo.getSimbolo())) generateNextPos(nodo);
        if(nodo.esHoja()) nodo.setNumHojas(1);
        else{
            if(prev != null) nodo.setNumHojas(prev.getNumHojas());
            if(next != null) nodo.setNumHojas(next.getNumHojas());
        }
    }
    
    public void generateCalc(){
        this.generateCalc(this.root);
    }
}
