import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.plaf.nimbus.State;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class AFDConverter {

    // Creando listas para guardar los resultados de la cerradura.
    private HashSet<Estado> cerraduraResult = new HashSet<Estado>();

    Queue<HashSet<Estado>> totalStates = new LinkedList<HashSet<Estado>>();

    // Creando variable para llevar el conteo de los estados del AFD.
    public static int contadorEstados = 0; // Contador de estados del AFD.

    Stack<StatesAFD> estados_iniciales = new Stack<>();

    Stack<StatesAFD> estados_finales = new Stack<>();

    HashSet<HashSet<Estado>> resultado = new HashSet<HashSet<Estado>>();

    // Creando lista para guardar los estados del AFD.
    ArrayList<StatesAFD> estadosAFD = new ArrayList<StatesAFD>();

    // Matriz para el resultado de eClosure.

    List<Estado> eClosure_List = new CopyOnWriteArrayList<Estado>();

    HashSet<AFD> resultado_trans = new HashSet<AFD>();

    // Constructor del AFDConverter

    public AFDConverter() {

    }

    // Método para empezar a procesar los datos.
    public void Proceso(List<Transiciones> transiciones, Estado inicial, List<Estado> estadosFinales,
            ArrayList<String> alfabeto, Stack<Estado> estados, Estado aceptacion) {

        // Ahora lo que se tiene que hacer es obtener el siguiente estado del AFD es
        // obtener el conjunto de estados alcanzados en el AFN por medio de "a" partir
        // del estado inicial del AFD. Esto se alcanzará con eclosure(move(D,a)).
        // Donde D es el estado inicial del AFD y a es el símbolo del alfabeto.

        // Obteniendo el alfabeto del AFN.
        ArrayList<String> alfabetoAFD = new ArrayList<String>();

        // // Creamos el estado inicial de nuestro AFD
        // StatesAFD AFD = new StatesAFD();
        // StatesAFD inicialAFD = new StatesAFD(0);
        // estados_iniciales.push(inicialAFD);

        for (int i = 0; i < alfabeto.size(); i++) {
            alfabetoAFD.add(alfabeto.get(i));
        }

        // Haciendo eClosure del estado inicial del AFN.
        cerraduraResult = cerradura(inicial);

        // Imprimiendo la cerradura del primer estado.
        // System.out.println("Cerradura del primer estado: " +
        // cerraduraResult.toString());

        totalStates.add(cerraduraResult); // Agregando la cerradura del primer estado a la lista de estados.
        // System.out.println("totalState con el primer resultado: " +
        // totalStates.toString());
        // System.out.println("totalState con poll: " + totalStates.poll().toString());
        // Creando ArrayList temporal para guardar los resultados de los subconjuntos
        resultado.add(cerraduraResult);
        // System.out.println("Resultado con cerraduraResult: " + resultado.toString());

        // creados a continuación.
        ArrayList<HashSet<Estado>> temporal = new ArrayList<HashSet<Estado>>();
        int index = 0;
        while (!totalStates.isEmpty()) {

            // System.out.println("totalStates Values" + totalStates.toString());
            // System.out.println("TAMAÑO DE totalStates: " + totalStates.size());

            // Trabajando con el actual subconjunto.
            HashSet<Estado> actuals = totalStates.poll();

            // System.out.println("Estado de totalStates: " + actuals);

            resultado.add(actuals);

            // System.out.println("Estado actual: " + actuals);

            // Recorriendo el subconjunto con cada símbolo del alfabeto.
            for (String simbolo : alfabetoAFD) {

                // System.out.println("Símbolo: " + simbolo);
                // System.out.println("MOVE" + mover(actuals, simbolo));
                // Realizando move con el subconjunto actual y el símbolo.
                HashSet<Estado> moveResult = mover(actuals, simbolo);

                // System.out.println("Recorremos los siguientes estados " + actuals + " con el
                // siguiente simbolo " + simbolo + " el siguiente move: " +
                // moveResult.toString());

                HashSet<Estado> eClosure = new HashSet<Estado>();

                for (Estado e : moveResult) { // Guardando el eClosure de cada estado alcanzado.
                    // System.out.println("RESULTADO ALCANZADO CON e: " + e.toString());
                    // System.out.println(cerradura(moveResult.poll()));
                    eClosure.addAll(cerradura(e));

                }

                if (!temporal.contains(eClosure)) { // Si el subconjunto no está en la lista temporal, se agrega.
                    // System.out.println("Entre al if de temporal");
                    totalStates.add(eClosure);
                    temporal.add(eClosure);

                    // Creando el estado para el subconjunto.
                    StatesAFD nuevoEstado = new StatesAFD(temporal.indexOf(eClosure) + 1);

                    estadosAFD.add(nuevoEstado);

                    // System.out.println("Estado nuevo: " + nuevoEstado.toString());

                    // Obteniendo el estado actual y el estado anterior.
                    StatesAFD estadoAnterior = estadosAFD.get(index);

                    System.out.println("Estado actual: " + nuevoEstado);
                    System.out.println("Estado anterior: " + estadoAnterior);

                    AFD afd = new AFD(estadoAnterior, nuevoEstado, simbolo);

                    resultado_trans.add(afd);

                    // System.out.println("Transición: " + afd.toString());

                    // Instanciando la clase de AFD.

                    // // Calculando estados nuevos del AFD.
                    // StatesAFD nuevoEstado = new StatesAFD(temporal.indexOf(eClosure) + 1);

                    // // Agregando el estado nuevo a la pila de estados del AFD.
                    // estados_iniciales.push(nuevoEstado);

                    // // Sacando el último estado del AFD.
                    // StatesAFD estadoActual = estados_iniciales.pop();

                    // transiciones(estadoActual, simbolo); // Calculando las transiciones del AFD.

                    // Haciendo la transición del estado actual al nuevo estado.
                    // AFD afd = new AFD(ultimoEstado, nuevoEstado, simbolo);

                    // System.out.println("Transición: " + afd);

                } else {
                    // Si el subconjunto ya existe, entonces solo se crea la transición.
                    // System.out.println("Entre al else de temporal");
                    // System.out.println("Estado anterior: " + estadosAFD.get(index));
                    // System.out.println("Estado actual: " +
                    // estadosAFD.get(temporal.indexOf(eClosure)));
                    // System.out.println("Símbolo: " + simbolo);

                    AFD afd = new AFD(estadosAFD.get(index - 1), estadosAFD.get(temporal.indexOf(eClosure)), simbolo);

                    resultado_trans.add(afd);

                    // System.out.println("Transición: " + afd.toString());
                }

            }
            // System.out.println((i++) + " " + "totalStates" + totalStates);
            index++; // Aumentando el index.
        }

        for (int l = 0; l < resultado.size(); l++) {
            System.out.println("Subconjunto " + l + ": " + resultado.toArray()[l].toString());

            // System.out.println("Estado: " + nuevoEstado);
        }

        System.out.println("Estados: " + estadosAFD);

        // Creando iterador para recorrer las transiciones.
        Iterator itr = resultado_trans.iterator();

        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

        // for (int i = 0; i < alfabetoAFD.size(); i++) {
        // for (int j = 0; j < temporal.size(); j++) {
        // System.out.println("Transición: " + alfabetoAFD.get(i) + " " +
        // temporal.get(j).toString());
        // System.out.println("Transición: " + alfabetoAFD.get(i) + " " +
        // temporal.get(j).toString());

        // }
        // }

    }

    // Creando método para armar las transiciones cuando se crea un nuevo estado.
    public void transiciones(StatesAFD estadoActual, String simbolo) {

        // Se crea un nuevo estado.
        StatesAFD nuevoEstado = new StatesAFD(contadorEstados);

        // Creando la transición.
        AFD afd = new AFD(estadoActual, nuevoEstado, simbolo);

        // Agregando el nuevo estado a la pila de estados finales.
        estados_finales.push(nuevoEstado);

        // Agregando el estado actual a la pila de estados iniciales.
        estados_iniciales.push(estadoActual);

        System.out.println("Transición: " + afd.toString());

    }

    // Creando método para armar las transiciones cuando no hay un nuevo estado.
    public void transiciones(StatesAFD estadoActual, StatesAFD nuevoEstado, String simbolo) {

        // Creando la transición.
        AFD afd = new AFD(estadoActual, nuevoEstado, simbolo);

        // Agregando el nuevo estado a la pila de estados finales.
        estados_finales.push(nuevoEstado);

        // Agregando el estado actual a la pila de estados iniciales.
        estados_iniciales.push(estadoActual);

        System.out.println("Transición: " + afd.toString());

    }

    // Creando método para calcular la cerradura de un estado.
    public HashSet<Estado> cerradura(Estado estado) {

        HashSet<Estado> resultado = new HashSet<Estado>(); // Creando un HashSet para guardar los resultados y que no
                                                           // hayan estados repetidos.

        Stack<Estado> pila = new Stack<Estado>();

        Estado s = estado; // Guardando localmente el estado inicial.

        pila.push(estado); // Guardando el estado inicial en la pila.

        while (!pila.isEmpty()) {

            s = pila.pop(); // Sacando el estado a analizar.

            // Obteniendo todas las transiciones que tiene el estado inicial.
            Transiciones tr = new Transiciones();

            for (Transiciones ts : tr.getTransicionesEstado(s)) {

                // System.out.println("TRANSICIONES OBTENIDAS CON ts: " + ts.toString());

                // System.out.println("Transición: " + ts.getDe() + " " + ts.getSimbolo() + " "
                // +
                // ts.getA());

                // Calculando la cerradura del estado inicial.
                if (ts.getSimbolo().equals("&") && !resultado.contains(ts.getA())) {

                    // System.out.println("Estado: " + ts.getA());
                    pila.push(ts.getA());
                    resultado.add(ts.getA());
                    resultado.add(s); // Agregando el estado que se analizó.

                } else if (!ts.getSimbolo().equals("&")) {
                    // System.out.println("LLEGUÉ ALV!");
                    // System.out.println("CONJUNTO AGREGADO: " + s.toString());
                    resultado.add(s);
                } else {
                    resultado.add(s);
                }

            }
        }

        return resultado; // Regresando el resultado de la cerradura.
    }

    // Creando método para calcular el movimiento de un estado.
    private HashSet<Estado> mover(HashSet<Estado> estados, String string) {
        HashSet<Estado> resultado = new HashSet<Estado>(); // Creando HashSet para guardar los resultados de los estados
                                                           // alcanzados.
        Iterator<Estado> it = estados.iterator(); // Creando iterador para recorrer los estados del HashSet.

        while (it.hasNext()) {
            for (Transiciones ts : new Transiciones().getTransicionesEstado(it.next())) { // Recorriendo las
                                                                                          // transiciones
                                                                                          // del estado.
                Estado s = ts.getA(); // Obteniendo el estado al que se llega con la transición.
                String simbolo = ts.getSimbolo(); // Obteniendo el símbolo de la transición.

                if (simbolo.equals(string)) {
                    resultado.add(s);
                }
            }
        }

        return resultado;
    }

}
