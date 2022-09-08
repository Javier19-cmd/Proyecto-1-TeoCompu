import java.util.*;

public class AFN {

    ArrayList<Integer> estados = new ArrayList<Integer>(); // Arraylist para los estados que se tendrán en el AFN.

    // Generando arraylist para las transiciones.
    // Transiciones transicion = new Transiciones(); // Instancia de la clase
    // Transiciones.
    int estado_inicial, estado_final; // Estados inicial y final del AFN

    public String construccion_AFN(String operacion) {
        // ArrayList<Transiciones> transiciones = new ArrayList<Transiciones>(); //
        // Arraylist para las transiciones.

        Stack<String> pila = new Stack<String>(); // Pila para los estados.

        System.out.println("Operación en la clase AFN: " + operacion);

        // Pusheando la operación a la pila.
        pila.push(operacion);

        System.out.println("Pila: " + pila); // Debuggeando la pila.

        // Sacando los elementos de la pila.
        String resultado = pila.pop();

        return resultado; // Retornando la pila.
    }
}
