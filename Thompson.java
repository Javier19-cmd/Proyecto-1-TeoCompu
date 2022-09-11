import java.util.*;

public class Thompson {

    // Definiendo el símbolo para las transiciones.
    private String simbolo = "&";

    // Definiendo la variable que va a llevar la contabilidad de los estados.
    public static int estados = 0;

    // Definiendo un Arraylist para las transiciones que se hicieron en el AFN.
    private ArrayList<Transiciones> transiciones = new ArrayList<Transiciones>();

    // Definiendo Stack para la expresión postfix invertida.
    private Stack<String> expresion_postfix = new Stack<String>();

    // Defininendo una pila para el alfabeto.
    private ArrayList<String> alfabeto = new ArrayList<String>();

    // Definiendo una pila para las operaciones de la expresión.
    private Stack<String> operaciones = new Stack<String>();

    // Defininiendo arraylist para la expresión original.
    private Stack<Estado> estados_iniciales = new Stack<Estado>();

    // Definiendo el stack para los estados de aceptación.
    private Stack<Estado> estados_aceptacion = new Stack<Estado>();

    // Definiendo una pila para los estados de la expresión.
    private Stack<Integer> estadoss = new Stack<Integer>();

    // Definiendo un Arraylist temporal para la expresión invertida.
    ArrayList<String> temporal = new ArrayList<String>();

    public void post(String postfix) {

        // System.out.println("Valor postfix: " + postfix);

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

        String recorrido = "";

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
        // Agregando sort al alfabeto.
        Collections.sort(alfabeto);

        // System.out.println("Alfabeto: " + alfabeto);

        // Recorriendo el Stack para identificar las operaciones.

        while (!expresion_postfix.isEmpty()) {

            // System.out.println("Valor de la expresión postfix invertida: " +
            // expresion_postfix.get(i)).

            // Recorriendo el ArrayList temporal hacia atrás.
            for (int i = temporal.size() - 1; i >= 0; i--) {
                // System.out.println("Valor de la expresión postfix invertida: " +

                recorrido = temporal.get(i);

                switch (recorrido) {
                    case "*":
                        System.out.println("Operación CERRADURA DE KLEENE.");
                        operacion = expresion_postfix.pop(); // Sacando la operación del stack.
                        operaciones.push(operacion);
                        // Se optiene el estado inicial y de aceptación de la expresión que se tenga
                        // armada.
                        Estado estado1 = estados_iniciales.pop();
                        Estado estado2 = estados_aceptacion.pop();

                        // Se manda a hacer la expresión en su método.
                        cerraduraKleene(estado1, estado2);
                        break;
                    case ".":
                        System.out.println("Operación CONCATENACIÓN.");
                        operacion = expresion_postfix.pop(); // Sacando la operación del stack.
                        operaciones.push(operacion);

                        Estado final_2 = estados_aceptacion.pop(); // Estado final del autómata derecho.
                        Estado fin = estados_aceptacion.pop(); // Estado final del autómata izquierdo.
                        Estado inicioo = estados_iniciales.pop(); // Estado inicial del autómata derecho.

                        System.out.println("Estado final del autómata izquierdo: " + fin);
                        System.out.println("Estado inicial del autómata derecho: " + inicioo);
                        System.out.println("Estado final del autómata derecho: " + final_2);
                        System.out.println();

                        concatenacion(fin, inicioo, final_2);
                        break;
                    case "|":
                        System.out.println("Operación UNIÓN.");
                        operacion = expresion_postfix.pop(); // Sacando la operación del stack.
                        operaciones.push(operacion); // Guardando la operación en el stack de operaciones.

                        // Se agarra el estado inicial y de aceptación de la expresión de arriba del
                        // barco.
                        Estado inicio_arriba = estados_iniciales.pop();
                        Estado aceptacion_arriba = estados_aceptacion.pop();

                        // Se agarra el estado inicial y de aceptación de la expresión de abajo del
                        // barco.
                        Estado inicio_abajo = estados_iniciales.pop();
                        Estado aceptacion_abajo = estados_aceptacion.pop();

                        // Se manda a hacer la expresión en su método.
                        union(inicio_arriba, aceptacion_arriba, inicio_abajo, aceptacion_abajo);
                        break;
                    default:
                        System.out.println("Operando: " + recorrido);
                        elemento1 = expresion_postfix.pop();
                        defaultop(elemento1);
                        break;
                }

            }
        }

        // Imprimiendo lista de transiciones.

    }

    // Método para hacer la operación default, que es crear un estado para un
    // caracter en específico.

    public void defaultop(String elemento) {
        // Teniendo el elemento, se crea un estado inicial y uno final.
        Transiciones transicion = new Transiciones(elemento);

        // Agregando la o las transiciones que se hayan hecho con el elemento.
        transiciones.add(transicion);

        // Convirtiendo a estado el inicio y el fin de la transición.
        Estado inicio = transicion.getDe();
        Estado fin = transicion.getA();

        // Agregando los estados al sus Stacks correspondientes.
        estados_iniciales.push(inicio);
        estados_aceptacion.push(fin);
    }

    // Se reciben los estado inicial y de aceptación, para así ya armar el
    // automata. (O sea el barco).
    public void cerraduraKleene(Estado elemento1, Estado elemento2) {
        // Creando nuevos estados de entrada y salida para el autómata.
        Estado nuevo_inicio = new Estado(estados); // Se pasa como parámetro el estado para que no se repitan los
                                                   // estados anteriores.
        Estado nuevo_fin = new Estado(estados); // Se pasa como parámetro el estado para que no se repitan los estados
                                                // siguientes.

        // Creando transiciones con los nuevos estados y con nuestro kleene.
        Transiciones transicion1 = new Transiciones(elemento2, elemento1, simbolo); // Transición del estado de
                                                                                    // aceptación al
                                                                                    // estado inicial del automata que
                                                                                    // se
                                                                                    // tenga kleenado.
        Transiciones transicion2 = new Transiciones(nuevo_inicio, nuevo_fin, simbolo); // Transición del nuevo estado
                                                                                       // inicial al nuevo estado de
                                                                                       // aceptación.
        Transiciones transicion3 = new Transiciones(nuevo_inicio, elemento1, simbolo); // Transción desde el nuevo
                                                                                       // estado de inicio
                                                                                       // al estado inicial del automata
                                                                                       // kleenado.
        Transiciones tranisicion4 = new Transiciones(elemento2, nuevo_fin, simbolo);// Transición desde el estado de
                                                                                    // aceptación del automata
                                                                                    // kleenado al estado inicial del
                                                                                    // automata kleenado.
        // Guardando las transiciones que se van haciendo.
        transiciones.add(transicion1);
        transiciones.add(transicion2);
        transiciones.add(transicion3);
        transiciones.add(tranisicion4);

        // Agregando los nuevos estados al Stack de estados iniciales y de aceptación.
        estados_iniciales.push(nuevo_inicio);
        estados_aceptacion.push(nuevo_fin);

    }

    // Método para hacer la operación de unión.
    private void union(Estado inicio_arriba, Estado aceptacion_arriba, Estado inicio_abajo, Estado aceptacion_abajo) {
        // Se crea un nuevo estado inicial y uno de aceptación.
        Estado nuevo_inicio = new Estado(estados);
        Estado nuevo_fin = new Estado(estados);

        // Creando las nuevas transiciones para el automata.

        // Transiciones para la parte de arriba.
        Transiciones transicion1_arriba = new Transiciones(nuevo_inicio, inicio_arriba, simbolo);
        Transiciones transicion2_arriba = new Transiciones(aceptacion_arriba, nuevo_fin, simbolo);

        // Transiciones para la parte de abajo.
        Transiciones transicion1_abajo = new Transiciones(nuevo_inicio, inicio_abajo, simbolo);
        Transiciones transicion2_abajo = new Transiciones(aceptacion_abajo, nuevo_fin, simbolo);

        // Agregando las transiciones a la lista de transiciones.
        // Transiciones de arriba.
        transiciones.add(transicion1_arriba);
        transiciones.add(transicion2_arriba);

        // Transiciones de abajo.
        transiciones.add(transicion1_abajo);
        transiciones.add(transicion2_abajo);

        // Agregando los nuevos estados al Stack de estados iniciales y de aceptación.
        estados_iniciales.push(nuevo_inicio);
        estados_aceptacion.push(nuevo_fin);
    }

    // Método para hacer la operación de concatenación.
    private void concatenacion(Estado elemento1, Estado elemento2, Estado final2) {
        // Imprimiendo el estado de aceptación del primer automata.
        System.out.println("Estado de aceptación del primer automata: " + elemento1);

        // Imprimeiendo el estado inicial del segundo automata.
        System.out.println("Estado inicial del segundo automata: " + elemento2);

        // Seteando el nuevo estado de aceptación.
        estados_aceptacion.push(final2);
    }

}
