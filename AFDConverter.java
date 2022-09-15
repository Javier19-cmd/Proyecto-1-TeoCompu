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

    // Creando una lista para guardar los resultados de la cerradura.
    private HashMap<List<Estado>, Boolean> cerraduraResult = new HashMap<List<Estado>, Boolean>();

    // Creando una lista para guardar los estados del AFD.
    private ArrayList<Estado> estadosAFD = new ArrayList<Estado>();

    // Creando ArrayList para los estados de aceptación del ArrayList.
    private ArrayList<Estado> estados_aceptacion = new ArrayList<Estado>();

    // Creando ArrayList para el alfabeto del AFD.
    private ArrayList<String> alfabetoAFD = new ArrayList<String>();

    // Creando ArrayList para guardar los resultados.
    ArrayList<ArrayList<Estado>> listaTemporal = new ArrayList<ArrayList<Estado>>();

    // Creando variable para llevar el conteo de los estados del AFD.
    public static int contadorEstados = 0; // Contador de estados del AFD.

    // Constructor del AFDConverter

    public AFDConverter() {

    }

    // Método para empezar a procesar los datos.
    public void Proceso(List<Transiciones> transiciones, Estado inicial, List<Estado> estadosFinales,
            ArrayList<String> alfabeto, Stack<Estado> estados, Estado aceptacion) {

        // Creando estado inicial del AFD.
        Estado s = new Estado();

        Estado a = s.AFDE(contadorEstados); // Se le asigna el id al estado.

        // Guardando el estado inicial del AFD.
        estadosAFD.add(a);

        Queue<ArrayList<Estado>> cola = new LinkedList<ArrayList<Estado>>(); // Creando cola para guardar los estados
                                                                             // del AFD.

        cerraduraResult.put(cerradura(inicial), false); // Guardando la cerradura del estado inicial. Esto será el
                                                        // estado inicial del AFD. El ArrayList se declara como false,
                                                        // dado que aún no se ha analizado.

        // Imprimiendo la cerradura del estado inicial.
        System.out.println("Cerradura del estado inicial: " + cerraduraResult.toString());

        // Ahora lo que se tiene que hacer es obtener el siguiente estado del AFD es
        // obtener el conjunto de estados alcanzados en el AFN por medio de "a" partir
        // del estado inicial del AFD. Esto se alcanzará con eclosure(move(D,a)).
        // Donde D es el estado inicial del AFD y a es el símbolo del alfabeto.

        // Guardando el alfabeto del AFD.
        alfabetoAFD = alfabeto;
        for (int i = 0; i < alfabetoAFD.size(); i++) {
            System.out.println("Símbolo del AFD: " + alfabetoAFD.get(i));
        }

        // Imprimiendo el HashMap.
        System.out.println("Hashmap: " + cerraduraResult.toString());

        // Mientras haya estados en false, se recorre el HashMap.
        Iterator<Entry<List<Estado>, Boolean>> it = cerraduraResult.entrySet().iterator();

        while (it.hasNext()) {

            Map.Entry<List<Estado>, Boolean> pair = (Map.Entry<List<Estado>, Boolean>) it.next(); // Se obtiene el
                                                                                                  // siguiente elemento.

            if (pair.getValue() == false) {
                System.out.println("Estado: " + pair.getKey() + " = " + pair.getValue());

                // Se obtiene el estado que se va a procesar.
                ArrayList<Estado> estado = (ArrayList<Estado>) pair.getKey();

                System.out.println("Estado: " + estado.toString());

                // Se obtiene el move que se hace desde cada estado del ArrayList con cada
                // símbolo del alfabeto.
                for (int i = 0; i < alfabetoAFD.size(); i++) {
                    System.out.println("Símbolo: " + alfabetoAFD.get(i));
                    ArrayList<Estado> move = mover(estado, alfabetoAFD.get(i));
                    System.out.println("Move: " + move.toString());

                    // Por cada elemento del move, se obtiene la cerradura.
                    for (int j = 0; j < move.size(); j++) {
                        System.out.println("Estado: " + move.get(j).toString());
                        ArrayList<Estado> cerradura = cerradura(move.get(j));
                        System.out.println("Cerradura: " + cerradura.toString());

                        // Se guarda el resultado de la cerradura en el HashMap.
                        cerraduraResult.put(cerradura, false);
                    }
                }
            }

            // Se cambia el valor del estado a true.
            pair.setValue(true);

        }

        // Imprimiendo el HashMap.
        System.out.println("Hashmap: " + cerraduraResult.toString());

    }

    // Creando método para calcular la cerradura de un estado.
    public ArrayList<Estado> cerradura(Estado estado) {

        System.out.println();
        System.out.println("Estado al cual se le aplicará la cerradura: " + estado.toString());
        System.out.println();

        Queue<ArrayList<Estado>> cola2 = new LinkedList<ArrayList<Estado>>(); // Creando cola para guardar los estados
                                                                              // del AFD.

        // Creando una pila para guardar los estados que se van a procesar.
        Stack<Estado> pila = new Stack<Estado>();

        Transiciones tr = new Transiciones();

        // Guardando localmente el estado que se va a procesar.
        Estado estadoActual = estado;

        Estado s = new Estado(); // Creando instancia de la clase Estado.

        // Se obtienen las transiciones que se realizan desde el estado actual.
        tr.getTransicionesEstado(estadoActual);

        System.out.println("Transiciones del estado actual: " + tr.getTransicionesEstado(estadoActual));

        // Se crea una lista para guardar las transiciones del estado actual.
        ArrayList<Estado> resultado = new ArrayList<Estado>();

        // Agrergando el estado actual a la pila de estados.
        pila.push(estadoActual);

        // Se recorre la pila de estados.
        while (!pila.isEmpty()) {

            s = pila.pop(); // Sacando un estado de la pila.
            // System.out.println("Estado: " + s);

            // Recorriendo la lista de transiciones del estado actual.
            for (Transiciones ts : tr.getTransicionesEstado(s)) {
                if (ts.getSimbolo().equals("&") && !resultado.contains(ts.getA())) {
                    resultado.add(ts.getA()); /// Validando que el estado no se haya agregado anteriormente.
                    pila.push(ts.getA()); // Agregando el estado a la pila.
                }

            }

        }

        resultado.add(estadoActual); // Se agrega el estado actual a la lista de resultados.

        // mover(resultado, alfabetoAFD); // Se calcula el movimiento con el estado
        // actual.

        // Se envía la lista de resultados al método mover.

        System.out.println("Resultado de la cerradura: " + resultado.toString());

        // // Calculando el movimiento con los estados de la lista de resultados.
        // for (String simbolo : alfabetoAFD) {
        // ArrayList<Estado> resultado_move = new ArrayList<Estado>();
        // resultado_move = mover(resultado, simbolo);
        // // System.out.println("Mover con " + simbolo + ": " +
        // // resultado_move.toString());
        // if (!resultado_move.isEmpty()) {
        // cola2.add(resultado_move);
        // }
        // }

        return resultado;

    }

    // Creando método para calcular el movimiento de un estado.
    private ArrayList<Estado> mover(List<Estado> estados, String simbolo) {
        System.out.println("-----------------------------------------------");

        System.out.println("Símbolo con el que se va a mover: " + simbolo);

        // Creando una lista para guardar los resultados del movimiento.
        ArrayList<Estado> resultado = new ArrayList<Estado>();

        // Creando una lista para guardar las transiciones del estado actual.
        ArrayList<Transiciones> transiciones = new ArrayList<Transiciones>();

        Transiciones ts = new Transiciones();

        // Recorriendo la lista de estados.
        for (Estado estado : estados) {
            // System.out.println("Estado move: " + estado);

            for (Transiciones t : ts.getTransicionesEstado(estado)) {
                if (t.getSimbolo().equals(simbolo)) {
                    // System.out.println("Transición: " + t);
                    // System.out.println("Símbolo: " + t.getSimbolo());
                    resultado.add(t.getA());
                }
            }
        }

        // Recorriendo la lista de resultados y mandando a llamar el método cerradura.
        // for (Estado estado : resultado) {
        // cerraduraResult = cerradura(estado);
        // }

        System.out.println("Resultado del movimiento: " + resultado.toString() + " con el símbolo " + simbolo);

        return resultado;
    }
}
