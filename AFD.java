/*
 * Clase de construcción para lo de subconjuntos AFD.
 */

import java.util.*;

public class AFD {
    StatesAFD de, a;
    String simbolo;

    public AFD (){
        
    }

    public AFD(StatesAFD de, StatesAFD a, String simbolo) {
        this.a = a;
        this.de = de;
        this.simbolo = simbolo;

        this.de.agregarEstadoSiguiente(a);
        this.a.agregarEstadoAnterior(de);
    } // Juntando estados del AFD.

    public String toString() {
        return this.de + "-" + this.simbolo + "->" + this.a;
    }

    public StatesAFD getDe(){
        return this.de;
    }

    public StatesAFD getA(){
        return this.a;
    }

    public String getSimbolo(){
        return this.simbolo;
    }

    public ArrayList <AFD> getTransicionesEstado(HashSet <StatesAFD> estado){
        ArrayList <AFD> transiciones = new ArrayList<AFD>();
        for (int i=0; i<AFDConverter.resultado_trans.size(); i++){
            if (AFDConverter.resultado_trans.get(i).getDe().equals(estado)) {
                transiciones.add(AFDConverter.resultado_trans.get(i));
            }
        }
        return transiciones;
    }

    

}