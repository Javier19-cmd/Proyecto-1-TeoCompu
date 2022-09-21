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

    ArrayList<String> resultado_trans = new ArrayList<String>(); // Lista para guardar el resultado de las transiciones.

    HashSet<StatesAFD> estados_aceptacion = new HashSet<StatesAFD>(); // Hashset para guardar los estados de aceptación.

    ArrayList<String> alfabetoAFD = new ArrayList<String>(); // ArrayList para el alfabeto.

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

        // Guardando el alfabeto en un ArrayList global.
        alfabetoAFD = alfabeto;

        // creados a continuación.
        ArrayList<HashSet<Estado>> temporal = new ArrayList<HashSet<Estado>>();

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

                }

            }
            // System.out.println((i++) + " " + "totalStates" + totalStates);
        }

        // Por cada subconjunto del resultado, se crea un nuevo estado en el AFD.
        for (int i = 0; i < resultado.size(); i++) {
            // System.out.println("Subconjunto " + i + ": " +
            // resultado.toArray()[i].toString());

            // Creando el nuevo estado.
            StatesAFD nuevoEstado = new StatesAFD(i);

            // Agregando el nuevo estado a la lista de estados del AFD.
            estadosAFD.add(nuevoEstado);

            // System.out.println("Estado: " + nuevoEstado);
        }

        // Calculando el move de cada subconjunto, con cada símbolo del alfabeto.
        for (int i = 0; i < alfabetoAFD.size(); i++) {
            for (int j = 0; j < resultado.size(); j++) {
                // System.out.println("Resultado: " + resultado.toArray()[j]);

                // System.out.println("Tipo del resultado: " +
                // resultado.toArray()[j].getClass());

                // Obteniendo el subconjunto actual.
                HashSet<Estado> subconjuntoActual = (HashSet<Estado>) resultado.toArray()[j];

                // System.out.println(
                // "Resultado de move: " + mover(subconjuntoActual, alfabetoAFD.get(i)) + " con
                // el símbolo "
                // + alfabetoAFD.get(i));

                // Calculando el eClosure de cada estado alcanzado.
                HashSet<Estado> eClosure = new HashSet<Estado>();

                for (Estado e : mover(subconjuntoActual, alfabetoAFD.get(i))) { // Guardando el eClosure de cada estado
                                                                                // alcanzado.
                    // System.out.println("RESULTADO ALCANZADO CON e: " + e.toString());
                    // System.out.println(cerradura(moveResult.poll()));
                    eClosure.addAll(cerradura(e));

                }

                // // Obteniendo el subconjunto al que se llega con el símbolo actual.
                // int subconjuntoLlegada = ((Array<HashSet<Estado>>)
                // resultado).indexOf(eClosure);

                List<HashSet<Estado>> list = new ArrayList<HashSet<Estado>>(resultado);

                // Imprimiendo a que subconjunto se llega con el símbolo actual.
                // System.out.println(
                // "Subconjunto: " + j + " con el símbolo " + alfabetoAFD.get(i) + " llega a: "
                // + eClosure + " "
                // + list.get(list.indexOf(eClosure)));

                AFD afd = new AFD(estadosAFD.get(j), estadosAFD.get(list.indexOf(eClosure)),
                        alfabetoAFD.get(i));

                // System.out.println("Transición: " + afd.toString());

                // Verificando si el resultado de la transición tiene un estado final.
                for (Estado e : eClosure) {
                    if (e.equals(aceptacion)) {
                        // Obteniendo el estado que tenga el estado de aceptación.

                        // System.out.println("Contenedor del estado de aceptación: " +
                        // estadosAFD.get(list.indexOf(eClosure)));

                        estados_aceptacion.add(estadosAFD.get(list.indexOf(eClosure))); // Guardando los estados de
                                                                                        // aceptación.
                    }
                }

                resultado_trans.add(afd.toString()); // Guardando la transición.

            }

        }

        // Área de debuggeo.

        // Imprimiendo cada resultado de la transición.
        for (int i = 0; i < resultado_trans.size(); i++) {
            System.out.println(resultado_trans.get(i));
        }

        System.out.println("Estados de aceptación: " + estados_aceptacion.toString());

        System.out.println("Alfabeto del AFD: " + alfabetoAFD);

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
