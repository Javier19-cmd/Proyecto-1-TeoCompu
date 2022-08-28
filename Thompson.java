import java.util.*;

public class Thompson {

    static String post(String postfix) {

        // System.out.println("Valor postfix: " + postfix);
        // Defininiendo arraylist para el alfabeto.
        ArrayList<String> expresion = new ArrayList<String>();

        // Defininendo una pila para el alfabeto.
        Stack<String> pila = new Stack<String>();

        // Definiendo una pila para las operaciones de la expresión.
        Stack<String> operaciones = new Stack<String>();

        // Definiendo el símbolo para las transiciones.
        char simbolo = '&';

        // Definiendo el estado inicial del AFN.
        int estado_inicial = 0;

        // Definiendo el stack para los estados de aceptación.
        Stack<Integer> estados_aceptacion = new Stack<Integer>();

        // Definiendo una pila para los estados de la expresión.
        ArrayList<Integer> estados = new ArrayList<Integer>();

        // Instanciando la clase de transiciones.
        Transiciones transicion = new Transiciones();

        // Definiendo los estados de las transiciones. Esto es una clase aparte.
        ArrayList<Transiciones> transiciones = new ArrayList<Transiciones>();

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
                    pila.push(String.valueOf(postfix.charAt(i)));
                    break;
            }

        }

        // Recorriendo la pila de la expresión regular.
        for (int x = 0; x < pila.size(); x++) {
            // System.out.println("Caracter: " + pila.get(x));
            // Pusheando los estados de la expresión regular a un arraylist, iniciando en 0.
            estados.add(x);
        }

        System.out.println("Estados: " + estados);

        // System.out.println("Expresion regular dentro de un stack: " + expresion);
        // System.out.println("Pila: " + pila);
        // System.out.println("Operaciones: " + operaciones);
        // System.out.println("Estados: " + estados);

        return postfix;
    }
}
