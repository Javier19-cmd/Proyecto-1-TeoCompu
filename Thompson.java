import java.util.*;

public class Thompson {

    static String post(String postfix) {

        // System.out.println("Valor postfix: " + postfix);
        // Defininiendo arraylist para el alfabeto.
        ArrayList<String> expresion = new ArrayList<String>();

        // Defininendo una pila para el alfabeto.
        Stack<String> alfabeto = new Stack<String>();

        // Definiendo una pila para las operaciones de la expresión.
        Stack<String> operaciones = new Stack<String>();

        // Definiendo el símbolo para las transiciones.
        char simbolo = '&';

        // Definiendo el estado inicial del AFN.
        int estado_inicial = 0;

        // Definiendo el stack para los estados de aceptación.
        Stack<Integer> estados_aceptacion = new Stack<Integer>();

        // Definiendo una pila para los estados de la expresión.
        Stack<Integer> estados = new Stack<Integer>();

        // Instanciando la clase de transiciones.
        Transiciones transicion = new Transiciones();

        // Definiendo los estados de las transiciones. Esto es una clase aparte.
        // ArrayList<Transiciones> transiciones = new ArrayList<Transiciones>();

        for (int i = 0; i < postfix.length(); i++) {
            // Pusheando los caracteres de la expresion regular en el arraylist.
            expresion.add(String.valueOf(postfix.charAt(i)));

            /*
             * Si el caracter es diferente de | o * o +, se agrega a la pila.
             */
            switch (postfix.charAt(i)) { // Quitando el or. Esto se pushea al stack de operaciones.
                case '|':
                    operaciones.push(String.valueOf(postfix.charAt(i)));
                    break;
                case '*': // Quitando el kleene. Esto se pushea al stack de operaciones.
                    operaciones.push(String.valueOf(postfix.charAt(i)));
                    break;
                case '+': // Quitando la cerradura positiva. Esto se pushea al stack de operaciones.
                    operaciones.push(String.valueOf(postfix.charAt(i)));
                    break;
                default: // Agregando el caracter de la expresión a la pila de la expresión regular.
                    alfabeto.push(String.valueOf(postfix.charAt(i)));
                    break;
            }

        }

        // System.out.println("Expresión regular: " + alfabeto);

        // Recorriendo la pila de la expresión regular.
        for (int x = 0; x < alfabeto.size(); x++) {
            // System.out.println("Caracter: " + pila.get(x));
            // Pusheando los estados de la expresión regular a un arraylist, iniciando en 0.
            estados.add(x);
        }

        // System.out.println("Estados: " + estados);

        // Pushear toda la expresión y analizar con pop las expresiones.

        // System.out.println("Expresion regular dentro de un stack: " + expresion);
        // System.out.println("Pila: " + alfabeto);
        // System.out.println("Operaciones: " + operaciones);
        // System.out.println("Estados: " + estados);
        // System.out.println("");

        // Recorriendo e imprimiendo la pila de estados y la pila del alfabeto de la
        // expresión regular.
        for (int i = 0; i < estados.size(); i++) {
            System.out.println("Estado: " + estados.get(i));
            System.out.println("Caracter: " + alfabeto.get(i));

            // Variable que guarda cada estado inicial y final.
            int estado_inicial1 = estados.get(i);

            // Variable que guarda cada letra.
            String letra = alfabeto.get(i);

            // Fabricando las transiciones. Aún falta meter el epsilon.
            transicion.Transicion(estado_inicial1, estado_inicial1 + 1, letra.charAt(0));
            System.out.println("");

            // System.out.println("Estado: " + i);

            // estados.removeElement(i);

            // System.out.println("Estados: " + estados);

        }

        // // Llenar el ArrayList de transiciones.
        // while (pila.size() > 0) {
        // System.out.println("Simbolo: " + pila.pop());
        // System.out.println("Estado: " + estados.pop());
        // System.out.println("");
        // // System.out.println("Estado de aceptación: " + estados_aceptacion.get(e));
        // // transicion.Transicion(estados.get(e), estados_aceptacion.get(e), simbolo);
        // }

        return postfix;
    }
}
