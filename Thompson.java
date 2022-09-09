import java.util.*;

public class Thompson {

    // Definiendo el símbolo para las transiciones.
    private String simbolo = "&";

    // Definiendo el estado inicial del AFN.
    private int estado_inicial = 0;

    // Definiendo el estado final del AFN.
    private int estado_final = 0;

    public void post(String postfix) {

        // System.out.println("Valor postfix: " + postfix);
        // Defininiendo arraylist para la expresión original.
        Stack<String> expresion_resultante = new Stack<String>();

        // Definiendo Stack para la expresión postfix invertida.
        Stack<String> expresion_postfix = new Stack<String>();

        // Defininendo una pila para el alfabeto.
        ArrayList<String> alfabeto = new ArrayList<String>();

        // Definiendo una pila para las operaciones de la expresión.
        Stack<String> operaciones = new Stack<String>();

        // Definiendo el stack para los estados de aceptación.
        Stack<Integer> estados_aceptacion = new Stack<Integer>();

        // Definiendo una pila para los estados de la expresión.
        Stack<Integer> estados = new Stack<Integer>();

        // Definiendo un Arraylist temporal para la expresión invertida.
        ArrayList<String> temporal = new ArrayList<String>();

        // Arreglo para determinar la precedencia de operaciones.
        String[][] precedencia = { { "*", "+", ".", "|" }, { "100", "80", "60", "40" } };

        // Definiendo los estados de las transiciones. Esto es una clase aparte.
        // ArrayList<Transiciones> transiciones = new ArrayList<Transiciones>();

        // Invirtiendo la expresión regular.
        StringBuilder strb = new StringBuilder(postfix);

        postfix = strb.reverse().toString(); // Invierto la expresión regular.

        String operacion = "";

        // Variable para sacar primer elemento a operar.
        String elemento1 = "";

        // Variable para sacar segundo elemento a operar.
        String elemento2 = "";

        // System.out.println("Expresión regular invertida: " + postfix);

        // Imprimiendo la matriz de precedencia.
        for (int i = 0; i < precedencia.length; i++) {
            for (int j = 0; j < precedencia[i].length; j++) {
                System.out.print(precedencia[i][j] + " ");
            }
            System.out.println();
        }

        // Insertando la expresión postfix invertida al Stack.
        for (int x = 0; x < postfix.length(); x++) {
            expresion_postfix.push(String.valueOf(postfix.charAt(x))); // Insertando la expresión postfix invertida al
                                                                       // Stack.
        }

        System.out.println("Expresión regular postfix invertida en el Stack: " + expresion_postfix);

        // Insertando la expresión postfix invertida al ArrayList temporal.
        for (int x = 0; x < postfix.length(); x++) {
            temporal.add(String.valueOf(postfix.charAt(x))); // Insertando la expresión postfix invertida al ArrayList
                                                             // temporal.
        }

        System.out.println("Expresión regular postfix en el ArrayList temporal: " + temporal.toString());

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

        // Recorriendo el Stack para identificar las operaciones.

        while (!expresion_postfix.isEmpty()) {

            // System.out.println("Valor de la expresión postfix invertida: " +
            // expresion_postfix.get(i)).

            // Recorriendo el ArrayList temporal hacia atrás.
            for (int i = temporal.size() - 1; i >= 0; i--) {
                // System.out.println("Valor de la expresión postfix invertida: " +
                // temporal.get(i));

                // Identificando si el elemento es un operador.
                if (temporal.get(i).equals("*")) {
                    System.out.println("Operador: " + expresion_postfix.pop());

                    // Insertando el operador al Stack de operaciones.
                    // operaciones.push(temporal.get(i));

                    // System.out.println("Operaciones: " + operaciones);

                    // Identificando si el elemento es un operando.
                } else if (temporal.get(i).equals(".")) {

                    System.out.println("Operador: " + expresion_postfix.pop());

                } else if (temporal.get(i).equals("|")) {

                    // Sacando el primer elemento a operar.
                    elemento1 = expresion_postfix.pop();
                    elemento2 = expresion_postfix.pop();

                    // Sacando el operador.
                    operacion = expresion_postfix.pop();

                    System.out.println("Operando: " + elemento1);

                    System.out.println("Operando: " + elemento2);

                    System.out.println("Operador: " + operacion);

                    estado_final = estado_inicial + 1;
                }
            }
        }
    }

    // Método para hacer la operación de concatenación.
    private void concatenacion(String elemento1, String elemento2) {
        // TODO Auto-generated method stub

    }
}
