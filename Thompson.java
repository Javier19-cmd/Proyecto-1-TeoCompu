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

        // Definiendo un Arraylist temporal para la expresión invertida.
        ArrayList<String> temporal = new ArrayList<String>();

        // Arreglo para determinar la precedencia de operaciones.
        String[][] precedencia = { { "*", "+", ".", "|" }, { "100", "80", "60", "40" } };

        // Definiendo los estados de las transiciones. Esto es una clase aparte.
        // ArrayList<Transiciones> transiciones = new ArrayList<Transiciones>();

        // Invirtiendo la expresión regular.
        StringBuilder strb = new StringBuilder(postfix);

        postfix = strb.reverse().toString(); // Invierto la expresión regular.

        String recorrido = "";

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

        // Recorriendo el ArrayList temporal hacia atrás.
        for (int i = temporal.size() - 1; i >= 0; i--) {
            // System.out.println("Valor de la expresión postfix invertida: " +
            // temporal.get(i));

            // Identificando si el elemento es un operador.
            if (temporal.get(i).equals("*") || temporal.get(i).equals(".") || temporal.get(i).equals("|")) {
                System.out.println("Operador: " + temporal.get(i));

                // Insertando el operador al Stack de operaciones.
                // operaciones.push(temporal.get(i));

                // System.out.println("Operaciones: " + operaciones);

                // Identificando si el elemento es un operando.
            } else {
                System.out.println("Operando: " + temporal.get(i));

                // Insertando el operando al Stack de operaciones.
                // operaciones.push(temporal.get(i));

                // System.out.println("Operaciones: " + operaciones);
            }
        }

        // Recorriendo el Stack para identificar las operaciones.

        while (!expresion_postfix.isEmpty()) {
            // System.out.println("Valor de la expresión postfix invertida: " +
            // expresion_postfix.get(i)).
            elemento1 = expresion_postfix.pop();
            System.out.println(elemento1);

        }
        return postfix;
    }

    // Definiendo el método para la creación de estados individuales.
    public static String OperacionDefault(int inicio, String simbolo) {
        Transiciones transicion = new Transiciones(); // Instanciando la clase de
                                                      // transiciones.

        AFN afn = new AFN(); // Instanciando la clase de AFN.

        int fin = inicio + 1; // Creando el estado de aceptación.
        // Creando la transición.
        String operacion = transicion.Transicion(inicio, fin, simbolo);

        String resultado = afn.construccion_AFN(operacion); // Construyendo el AFN.

        // System.out.println("Resultado en la clase de Thompson y en el método de
        // OperacionDefault: " + resultado);
        return resultado;
    }

    public static String OR(int inicio, int fin, String simbolo) {

        // Instanciando la clase de transiciones.
        Transiciones transicion = new Transiciones();

        // Creando el estado de inicio.
        transicion.Transicion(inicio, fin, simbolo);

        return ""; // Return vacío por el momento.

    }

    public static String Concatenacion(int inicio, int fin, String simbolo1, String simbolo2) {
        Transiciones transicion = new Transiciones(); // Instanciando la clase de
                                                      // transiciones.
        // Creando el estado de inicio.

        return "";
    }

    // Operación de Kleene. Esta operación recibe el inicio y la expresión.
    public static String opKleene(int inicio, String expresion, String simbolo) {

        Transiciones trans = new Transiciones(); // Instanciando la clase de transiciones.

        System.out.println("Recepción: ");
        System.out.println("Expresión: " + expresion);
        System.out.println("Simbolo: " + simbolo);

        // Armando el barco de la expresión.
        return expresion;
    }
}
