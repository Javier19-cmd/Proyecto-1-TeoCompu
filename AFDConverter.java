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
    private Stack<ArrayList<Estado>> cerraduraResult = new Stack<ArrayList<Estado>>();
    private Stack<ArrayList<Estado>> totalStates = new Stack<ArrayList<Estado>>();

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

    // Matriz para el resultado de eClosure.

    List<Estado> eClosure_List = new CopyOnWriteArrayList<Estado>();

    // Constructor del AFDConverter

    public AFDConverter() {

    }

    // Método para empezar a procesar los datos.
    public void Proceso(List<Transiciones> transiciones, ArrayList<Estado> inicial, List<Estado> estadosFinales,
            ArrayList<String> alfabeto, Stack<Estado> estados, Estado aceptacion) {

        // Creando estado inicial del AFD.
        Estado s = new Estado();

        Estado a = s.AFDE(contadorEstados); // Se le asigna el id al estado.

        // Guardando el estado inicial del AFD.
        estadosAFD.add(a);

        Queue<ArrayList<Estado>> cola = new LinkedList<ArrayList<Estado>>(); // Creando cola para guardar los estados
                                                                             // del AFD.

        cerraduraResult.push(cerradura(inicial)); // Guardando la cerradura del estado inicial. Esto será el
                                                  // estado inicial del AFD. El ArrayList se declara como false,
                                                  // dado que aún no se ha analizado.
        totalStates.push(cerradura(inicial));

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

        // Imprimiendo el Stack.
        System.out.println("Stack: " + cerraduraResult.toString());

        while (!cerraduraResult.isEmpty()) {

            // System.out.println("Estatos totales: " + totalStates.toString());

            // Se obtiene el estado que se va a procesar.
            ArrayList<Estado> estado = cerraduraResult.pop();

            // Matriz para el resultado del move.
            ArrayList<ArrayList<Estado>> move = new ArrayList<ArrayList<Estado>>();

            System.out.println("Estado: " + estado.toString());

            // Se aplican los métodos move y e-closure al estado con cada uno de los
            // símbolos del alfabeto.
            for (int i = 0; i < alfabetoAFD.size(); i++) {

                // Se obtiene el resultado de move.
                move.add(mover(estado, alfabetoAFD.get(i)));

                // Se imprime el resultado de move.
                System.out.println("Move: " + move.toString());

                // Se obtiene el resultado de e-closure de cada estado de move.
                // Recorriendo el ArrayList de move para obtener los estados de move y aplicar
                // e-closure.
                for (int j = 0; j < move.size(); j++) {

                    System.out.println("Move: " + move.get(j).toString());

                    // Se obtiene el resultado de e-closure.
                    cerradura(move.get(j));

                    System.out.println("eClosure: " + cerradura(move.get(j)));

                    // Agregando el resultado de e-closure al HashMap. Si el estado ya existe en el
                    // HashMap, no se agrega.
                    if (totalStates.contains(cerradura(move.get(j)))) {

                    } else {
                        cerraduraResult.push(cerradura(move.get(j)));
                        totalStates.push(cerradura(move.get(j)));
                    }

                }

            }

        }
        // Imprimiendo totalStates.
        System.out.println("TotalStates: " + totalStates.toString());

        // e-closure del estado inicial.
        // Move del resultado de e-closure del estado inicial.
        // e-closure del resultado de move del estado inicial.
        // El e-closure se tiene que guardar en el HashMap como false.
        // No tienen que haber estados repetidos en el HashMap.
        // Se repite el proceso hasta que no haya más estados en false.

    }

    // Creando método para calcular la cerradura de un estado.
    public ArrayList<Estado> cerradura(ArrayList<Estado> estado) {

        // Se crea una lista para guardar las transiciones del estado actual.
        ArrayList<Estado> resultado = new ArrayList<Estado>();

        // System.out.println();
        // System.out.println("Estado al cual se le aplicará la cerradura: " +
        // estado.toString());
        // System.out.println();

        Queue<ArrayList<Estado>> cola2 = new LinkedList<ArrayList<Estado>>(); // Creando cola para guardar los estados
                                                                              // del AFD.

        // Creando una pila para guardar los estados que se van a procesar.
        Stack<Estado> pila = new Stack<Estado>();

        Transiciones tr = new Transiciones();

        // Guardando el contenido de la lista de estados en la pila.
        for (int i = 0; i < estado.size(); i++) {
            pila.push(estado.get(i));
            resultado.add(estado.get(i));
        }

        Estado s = new Estado(); // Creando instancia de la clase Estado.

        // Se obtienen las transiciones que se realizan desde el estado actual.
        // tr.getTransicionesEstado(estadoActual);

        // System.out.println("Transiciones del estado actual: " +
        // tr.getTransicionesEstado(estadoActual));

        // Agrergando el estado actual a la pila de estados.
        // pila.push(estadoActual);

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

        // resultado.add(estadoActual); // Se agrega el estado actual a la lista de
        // resultados.

        // mover(resultado, alfabetoAFD); // Se calcula el movimiento con el estado
        // actual.

        // Se envía la lista de resultados al método mover.

        // System.out.println("Resultado de la cerradura: " + resultado.toString());

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
