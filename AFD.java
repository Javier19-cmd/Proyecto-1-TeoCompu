/*
 * Clase de construcción para lo de subconjuntos AFD.
 */

import java.util.*;

public class AFD {
    StatesAFD de, a;
    String simbolo;

    public AFD() {

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

    // Transiciones de un estado.
    public StatesAFD getDe() {
        return this.de;
    }

    public StatesAFD getA() {
        return this.a;
    }

    public String getSimbolo() {
        return this.simbolo;
    }

    public ArrayList<AFD> getTransicionesEstado(HashSet<StatesAFD> estado) { // Método para obtener las transiciones de
                                                                             // un estado.
        ArrayList<AFD> transiciones = new ArrayList<AFD>();

        // Imprimiendo el estado que se está analizando.
        // System.out.println("Estado: " + estado);

        // Recorriendo el arreglo de las transiciones.
        for (int i = 0; i < AFDConverter.resultado_trans.size(); i++) {
            // System.out.println("Transición: " + AFDConverter.resultado_trans.get(i));
            // Sacando cada estado de estado.
            for (StatesAFD s : estado) {
                // Verificando que s esté en resultado_trans.
                if (AFDConverter.resultado_trans.get(i).getDe().equals(s)) {
                    transiciones.add(AFDConverter.resultado_trans.get(i));
                }
            }
        }

        // System.out.println("Resultado final: " + transiciones);
        return transiciones;
    }

}