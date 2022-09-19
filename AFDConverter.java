import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

public class AFDConverter {

    // Creando la lista de cerraduras del estado inicial.
    private ArrayList<Estado> cerraduraInicial = new ArrayList<Estado>(); // Lista de cerraduras del estado inicial.
    private Estado afd;

    // Creando una copia de la lista de estados del AFN.
    private ArrayList<Estado> estadosAFN = new ArrayList<Estado>();

    // Creando stack para el resultado de la cerradura.
    private Stack<Estado> cerradura = new Stack<Estado>();

    // Creando listas para guardar los resultados de la cerradura.
    private HashSet<Estado> cerraduraResult = new HashSet<Estado>();

    Queue<HashSet<Estado>> totalStates = new LinkedList<HashSet<Estado>>();

    // Creando una lista para guardar los estados del AFD.
    private ArrayList<Estado> estadosAFD = new ArrayList<Estado>();

    // Creando ArrayList para los estados de aceptación del ArrayList.
    private ArrayList<Estado> estados_aceptacion = new ArrayList<Estado>();

    // Creando ArrayList para el alfabeto del AFD.
    private ArrayList<String> alfabetoAFD = new ArrayList<String>();

    // Creando variable para llevar el conteo de los estados del AFD.
    public static int contadorEstados = 0; // Contador de estados del AFD.

    // Matriz para el resultado de eClosure.

    List<Estado> eClosure_List = new CopyOnWriteArrayList<Estado>();

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

        for (int i = 0; i < alfabeto.size(); i++) {
            alfabetoAFD.add(alfabeto.get(i));
        }

        // Haciendo eClosure del estado inicial del AFN.
        cerraduraResult = cerradura(inicial);

        // Imprimiendo la cerradura del primer estado.
        System.out.println("Cerradura del primer estado: " + cerraduraResult.toString());

        totalStates.add(cerraduraResult); // Agregando la cerradura del primer estado a la lista de estados.

        // Creando ArrayList temporal para guardar los resultados de los subconjuntos
        // creados a continuación.
        ArrayList<HashSet<Estado>> temporal = new ArrayList<HashSet<Estado>>();

        while (!totalStates.isEmpty()) {

            // Trabajando con el actual subconjunto.
            HashSet<Estado> actuals = totalStates.poll();

            System.out.println("Estado actual: " + actuals);

            // Recorriendo el subconjunto con cada símbolo del alfabeto.
            for (String simbolo : alfabetoAFD) {

                // Creando un HashSet para guardar los resultados de los estados alcanzados por
                // medio del símbolo.
                HashSet<Estado> alcanzados = new HashSet<Estado>();

                System.out.println("Símbolo: " + simbolo);

                // Realizando move con el subconjunto actual y el símbolo.
                HashSet<Estado> moveResult = mover(actuals, simbolo);

                HashSet<Estado> eClosure = new HashSet<Estado>();

                for (Estado e : moveResult) { // Guardando el eClosure de cada estado alcanzado.
                    eClosure.addAll(cerradura(e));
                }

                if (!temporal.contains(eClosure)) { // Si el subconjunto no está en la lista temporal, se agrega.
                    totalStates.add(eClosure);
                    temporal.add(eClosure);
                }
            }
        }

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

                // System.out.println("Transición: " + ts.getDe() + " " + ts.getSimbolo() + " "
                // +
                // ts.getA());

                // Calculando la cerradura del estado inicial.
                if (ts.getSimbolo().equals("&") && !resultado.contains(ts.getA())) {

                    // System.out.println("Estado: " + ts.getA());
                    pila.push(ts.getA());
                    resultado.add(ts.getA());
                    resultado.add(s); // Agregando el estado que se analizó.

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
