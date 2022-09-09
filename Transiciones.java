import java.util.*;

public class Transiciones {

    // Variables globales de la clase Transiciones.
    Estado de, a; // de es el estado de inicio y a es el estado de fin.
    String simbolo; // simbolo de la transicion.

    public Transiciones(String c) {
        // this.de = de;
        // this.a = a;
        this.simbolo = c;

        // Enlazando los estados para armar el AFN.
        this.de.agregarEstadoSiguiente(a);
        this.a.agregarEstadoAnterior(de);

    } // Constructor de la clase Transicion. Esto servirá para almacenar las
      // transiciones de los estados.

    // Conctanteación de transiciones del AFN.
    public Transiciones(Estado de, Estado a, String simbolo) {
        this.de = de;
        this.a = a;
        this.simbolo = simbolo;

        // Enlazando los estados para armar el AFN.
        this.de.agregarEstadoSiguiente(a);
        this.a.agregarEstadoAnterior(de);

    }

    public Estado getDe() { // Método para obtener el estado de inicio.
        return this.de;
    }

    public Estado getA() { // Método para obtener el estado final.
        return this.a;
    }

    public String getSimbolo() { // Método para obtener
        return this.simbolo;
    }

    public String vis() { // Método para poder ver las transiciones.
        return this.de.getId() + " -> " + this.a.getId() + " [label=\"" + this.simbolo + "\"]";
    }
}
