import java.util.*;

public class Thompson {

    static String post(String postfix) {

        // System.out.println("Valor postfix: " + postfix);
        // Defininiendo arraylist para la expresión original.
        ArrayList<String> expresion_orig = new ArrayList<String>();

        // Definiendo Stack para la expresión postfix invertida.
        Stack<String> expresion_postfix = new Stack<String>();

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

        // Invirtiendo la expresión regular.
        StringBuilder strb = new StringBuilder(postfix);

        postfix = strb.reverse().toString(); // Invierto la expresión regular.

        System.out.println("Expresión regular invertida: " + postfix);

        // Insertando la expresión postfix invertida al Stack.
        for (int x = 0; x < postfix.length(); x++) {
            expresion_postfix.push(String.valueOf(postfix.charAt(x))); // Insertando la expresión postfix invertida al
                                                                       // Stack.
        }

        System.out.println("Expresión regular postfix invertida en el Stack: " + expresion_postfix);

        // Verificando la popeada de la expresión postfix invertida.
        while (!expresion_postfix.isEmpty()) {
            // System.out.println("Valor de la expresión postfix invertida: " +
            // expresion_postfix.pop());

            // Identificando el tipo de operación que se va a realizar.
            if (expresion_postfix.peek().equals("|")) { // Operación OR.
                System.out.println("Valor de la expresión postfix invertida: " + expresion_postfix.pop());
                System.out.println("Operación OR");
            } else if (expresion_postfix.peek().equals("*")) { // Operación KLEENE.
                System.out.println("Valor de la expresión postfix invertida: " + expresion_postfix.pop());
                System.out.println("Operación KLEENE");
            } else if (expresion_postfix.peek().equals("+")) { // Operación CERRADURA POSITIVA.
                System.out.println("Valor de la expresión postfix invertida: " + expresion_postfix.pop());
                System.out.println("Operación CERRADURA POSITIVA");
            } else { // Operación CONCATENACIÓN.
                System.out.println("Valor de la expresión postfix invertida: " + expresion_postfix.pop());
                System.out.println("Operación CONCATENACIÓN");
            }
            // switch (expresion_postfix.pop()) {
            // case "|": // Operación OR.
            // // System.out.println("Operación OR");
            // break;
            // case "*": // Operación KLEENE.
            // System.out.println("Operación KLEENE");
            // System.out.println(expresion_postfix.pop());
            // break;
            // case "+": // Operación CERRADURA POSITIVA.
            // System.out.println("Operación CERRADURA POSITIVA");
            // break;
            // }
        }

        // Recorriendo la expresión regular.

        for (int i = 0; i < postfix.length(); i++) {
            // Pusheando los caracteres de la expresion regular en el arraylist.
            expresion_orig.add(String.valueOf(postfix.charAt(i)));

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
                case '+': // Quitando la cerradura positiva. Esto se pushea al stack de operaciones
                    operaciones.push(String.valueOf(postfix.charAt(i)));
                    break;
                default: // Agregando el caracter de la expresión a la pila de la expresión regular
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

        // Pushear toda la expresión con las operaciones y analizar con pop las
        // expresiones. Puede ser útil tener en el stack la expresión ya analizada para
        // poder ir juntando las
        // operaciones.

        // System.out.println("Expresion regular dentro de un stack: " + expresion);
        // System.out.println("Pila: " + alfabeto);
        // System.out.println("Operaciones: " + operaciones);
        // System.out.println("Estados: " + estados);
        // System.out.println("");

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
