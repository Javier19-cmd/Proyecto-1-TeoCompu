import java.util.*;

public class Transiciones {

    // Variables globales de la clase Transiciones.
    Estado de, a; // de es el estado de inicio y a es el estado de fin.
    String simbolo; // simbolo de la transicion.

    public Transiciones(String c) {
        this.de = new Estado(Thompson.estados);
        this.a = new Estado(Thompson.estados);
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

    public String getSimbolo() { // Método para obtener el símbolo de la transición.
        return this.simbolo;
    }

    public String getDeS() {
        return this.de.toString();
    }

    public String getAS() {
        return this.a.toString();
    }

    public void replaceA(Estado viejoa, Estado a) {
        // Buscando el estado viejo en la lista de estados siguientes del estado
        // siguiente.
        for (int i = 0; i < viejoa.getEstadoSiguiente().size(); i++) {
            if (viejoa.getEstadoSiguiente().get(i).equals(viejoa)) {
                System.out.println(viejoa.getEstadoSiguiente().get(i));
                viejoa.getEstadoSiguiente().remove(i);
                viejoa.getEstadoSiguiente().add(i, a);
            }
        }
    }

    public void replaceDe(Estado viejode, Estado de) {
        // Buscando el estado viejo en la lista de estados siguientes del estado
        // siguiente.
        for (int i = 0; i < viejode.getEstadoAnterior().size(); i++) {
            if (viejode.getEstadoAnterior().get(i).equals(viejode)) {
                viejode.getEstadoAnterior().remove(i);
                viejode.getEstadoAnterior().add(i, de);
            }
        }
    }

    public void replaceElement(Estado de, Estado a, String simbolo) {
        this.de = de;
        this.a = a;
        this.simbolo = simbolo;
    }

    public String vis() { // Método para poder ver las transiciones.
        return this.de.toString() + " -- " + this.simbolo + " --> " + this.a.toString();
    }
}
