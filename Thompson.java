import java.util.*;

public class Thompson {

    static String post(String postfix) {

        // System.out.println("Valor postfix: " + postfix);
        // Defininiendo arraylist para la expresión original.
        Stack<String> expresion_resultante = new Stack<String>();

        // Definiendo Stack para la expresión postfix invertida.
        Stack<String> expresion_postfix = new Stack<String>();

        // Defininendo una pila para el alfabeto.
        ArrayList<String> alfabeto = new ArrayList<String>();

        // Definiendo una pila para las operaciones de la expresión.
        Stack<String> operaciones = new Stack<String>();

        // Definiendo el símbolo para las transiciones.
        String simbolo = "&";

        // Definiendo el estado inicial del AFN.
        int estado_inicial = 0;

        // Definiendo el stack para los estados de aceptación.
        Stack<Integer> estados_aceptacion = new Stack<Integer>();

        // Definiendo una pila para los estados de la expresión.
        Stack<Integer> estados = new Stack<Integer>();

        // Arreglo para determinar la precedencia de operaciones.
        String[][] precedencia = { { "*", "+", ".", "|" }, { "100", "80", "60", "40" } };

        // Definiendo los estados de las transiciones. Esto es una clase aparte.
        // ArrayList<Transiciones> transiciones = new ArrayList<Transiciones>();

        // Invirtiendo la expresión regular.
        StringBuilder strb = new StringBuilder(postfix);

        postfix = strb.reverse().toString(); // Invierto la expresión regular.

        // System.out.println("Expresión regular invertida: " + postfix);

        // Imprimiendo la matriz de precedencia.
        for (int i = 0; i < precedencia.length; i++) {
            for (int j = 0; j < precedencia[i].length; j++) {
                System.out.print(precedencia[i][j] + " ");
            }
            System.out.println();
        }

        // Insertando la expresión postfix invertida al Stack.
        for (

                int x = 0; x < postfix.length(); x++) {
            expresion_postfix.push(String.valueOf(postfix.charAt(x))); // Insertando la expresión postfix invertida al
                                                                       // Stack.
        }

        System.out.println("Expresión regular postfix invertida en el Stack: " + expresion_postfix);

        // Identificando el alfabeto de la expresión regular.
        for (int i = 0; i < expresion_postfix.size(); i++) {
            if (expresion_postfix.get(i).equals("*") || expresion_postfix.get(i).equals(".")
                    || expresion_postfix.get(i).equals("|")) {
                continue;
            } else {
                // Insertando una vez cada caracter del alfabeto.
                if (!alfabeto.contains(expresion_postfix.get(i))) {
                    alfabeto.add(expresion_postfix.get(i));
                }
            }
        }

        // System.out.println("Alfabeto: " + alfabeto);

        int i = 0; // Variable para recorrer el stack.

        while (i < expresion_postfix.size()) {
            // System.out.println("Valor de la expresión postfix invertida: " +
            // expresion_postfix.get(i));

            // Identificando los caracteres de la expresión postfix invertida.
            switch (expresion_postfix.get(i)) {
                case "|":
                    // Sacando los estados de la derecha e izquierda de la operación OR.
                    System.out.println("Operación OR " + expresion_postfix.get(i));

                    // Sacando el primer estado de la pila de estados.
                    String estado1 = String.valueOf(expresion_postfix.get(i + 1));
                    String estado2 = String.valueOf(expresion_postfix.get(i + 2));

                    System.out.println("Estado 1: " + estado1);
                    System.out.println("Estado 2: " + estado2);

                    break;
                case ".":
                    System.out.println("Operación CONCATENACION " + expresion_postfix.get(i));

                    // Sacando el primer estado de la pila de estados.
                    String estado3 = String.valueOf(expresion_postfix.get(i + 1));
                    String estado4 = String.valueOf(expresion_postfix.get(i + 2));

                    System.out.println("Estado 1: " + estado3);
                    System.out.println("Estado 2: " + estado4);
                    break;
                case "*":
                    System.out.println("Operación KLEENE " + expresion_postfix.get(i));

                    // Sacando el primer estado de la pila de estados.
                    String estado5 = String.valueOf(expresion_postfix.get(i + 1));

                    System.out.println("Estado 1: " + estado5);
                    break;
                case "+":
                    System.out.println("Operación POSITIVA " + expresion_postfix.get(i));
                    break;
                default:
                    // Creando un estado.
                    System.out.println("Estado: " + expresion_postfix.get(i));
                    break;
            }

            i++;
        }

        // Agregando la transición del estado de aceptación al estado de acept

        // Verificando la popeada de la expresión postfix invertida.
        // while (!expresion_postfix.isEmpty()) {
        // // System.out.println("Valor de la expresión postfix invertida: " +
        // // expresion_postfix.pop());

        // // Sacando el primer estado de la pila de estados.

        // // Identificando los caracteres de la expresión postfix invertida.
        // switch (expresion_postfix.peek()) {
        // case "|":
        // System.out.println("Operación OR " + expresion_postfix.peek());

        // // String estado1 = String.valueOf(expresion_postfix.pop());
        // // String estado2 = String.valueOf(expresion_postfix.pop());

        // // System.out.println("Estado 1: " + estado1 + "Estado 2 " + estado2);

        // break;
        // case "*":
        // System.out.println("Operación KLEENE " + expresion_postfix.peek());
        // break;
        // case ".": // Concatenación
        // System.out.println("Operación CONCATENACION " + expresion_postfix.peek());
        // break;
        // case "+":
        // System.out.println("Operación CERRADURA POSITIVA " +
        // expresion_postfix.peek());
        // break;
        // default:
        // System.out.println("Caracter: " + expresion_postfix.peek());
        // break;
        // }

        // }

        // Recorriendo la expresión regular.

        // for (int i = 0; i < postfix.length(); i++) {
        // // Pusheando los caracteres de la expresion regular en el arraylist.
        // expresion_orig.add(String.valueOf(postfix.charAt(i)));

        // /*
        // * Si el caracter es diferente de | o * o +, se agrega a la pila.
        // */

        // switch (postfix.charAt(i)) { // Quitando el or. Esto se pushea al stack de
        // operaciones.
        // case '|':
        // operaciones.push(String.valueOf(postfix.charAt(i)));
        // break;
        // case '*': // Quitando el kleene. Esto se pushea al stack de operaciones.
        // operaciones.push(String.valueOf(postfix.charAt(i)));
        // break;
        // case '+': // Quitando la cerradura positiva. Esto se pushea al stack de
        // operaciones
        // operaciones.push(String.valueOf(postfix.charAt(i)));
        // break;
        // default: // Agregando el caracter de la expresión a la pila de la expresión
        // regular
        // alfabeto.push(String.valueOf(postfix.charAt(i)));
        // break;
        // }
        // }

        // // System.out.println("Expresión regular: " + alfabeto);

        // // Recorriendo la pila de la expresión regular.
        // for (int x = 0; x < alfabeto.size(); x++) {
        // // System.out.println("Caracter: " + pila.get(x));
        // // Pusheando los estados de la expresión regular a un arraylist, iniciando en
        // 0.
        // estados.add(x);
        // }

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

    public static String OR(int inicio, int fin, String simbolo) {

        // Instanciando la clase de transiciones.
        Transiciones transicion = new Transiciones();

        // Creando el estado de inicio.
        transicion.Transicion(inicio, fin, simbolo);

        return ""; // Return vacío por el momento.

    }
}
