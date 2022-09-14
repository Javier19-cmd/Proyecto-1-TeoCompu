import java.util.*;

public class AFDConverter {

    // Creando la lista de cerraduras del estado inicial.
    private ArrayList<Estado> cerraduraInicial = new ArrayList<Estado>(); // Lista de cerraduras del estado inicial.
    private Estado afd;

    // Creando una copia de la lista de estados del AFN.
    private ArrayList<Estado> estadosAFN = new ArrayList<Estado>();

    // Creando stack para el resultado de la cerradura.
    private Stack<Estado> cerradura = new Stack<Estado>();

    // Creando una lista para guardar los resultados de la cerradura.
    private ArrayList<Estado> cerraduraResult = new ArrayList<Estado>();

    // Creando una lista para guardar los estados del AFD.
    private ArrayList<Estado> estadosAFD = new ArrayList<Estado>();

    // Creando ArrayList para los estados de aceptación del ArrayList.
    private ArrayList<Estado> estados_aceptacion = new ArrayList<Estado>();

    // Creando ArrayList para el alfabeto del AFD.
    private ArrayList<String> alfabetoAFD = new ArrayList<String>();

    // Creando variable para llevar el conteo de los estados del AFD.
    public static int contadorEstados = 0; // Contador de estados del AFD.

    // Constructor del AFDConverter

    public AFDConverter() {
        afd = new Estado();
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
        // Llenando la lista de

        cerraduraInicial = cerradura(inicial); // Guardando la cerradura del estado inicial.

        // Imprimiendo la cerradura del estado inicial.
        System.out.println("Cerradura del estado inicial: " + cerraduraInicial.toString());

        // Si con el primer e-closure se llegó a un estado final, se agrega a la lista
        // de estados de aceptación.
        // for (Estado accept : estadosFinales) {
        // if (cerraduraInicial.contains(accept)) {
        // estados_aceptacion.add(a);
        // }
        // }

        // // Imprimiendo los estados de aceptación.
        // System.out.println("Estados de aceptación: " +
        // estados_aceptacion.toString());

        // Guardando el alfabeto del AFD.
        alfabetoAFD = alfabeto;

        // Calculando el movimiento con la cerradura inicial.
        for (String simbolo : alfabetoAFD) {
            ArrayList<Estado> resultado_move = new ArrayList<Estado>();
            resultado_move = mover(cerraduraInicial, simbolo);
            if (!resultado_move.isEmpty()) {
                cola.add(resultado_move);
            }
        }

    }

    // Creando método para calcular la cerradura de un estado.
    private ArrayList<Estado> cerradura(Estado estado) {

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
        return resultado;

    }

    // Creando método para calcular el movimiento de un estado.
    private ArrayList<Estado> mover(ArrayList<Estado> estados, String simbolo) {

        // Creando una lista para guardar los resultados del movimiento.
        ArrayList<Estado> resultado = new ArrayList<Estado>();

        // Creando una lista para guardar las transiciones del estado actual.
        ArrayList<Transiciones> transiciones = new ArrayList<Transiciones>();

        Transiciones ts = new Transiciones();

        // Recorriendo la lista de estados.
        for (Estado estado : estados) {
            System.out.println("Estado move: " + estado);

            for (Transiciones t : ts.getTransicionesEstado(estado)) {
                if (t.getSimbolo().equals(simbolo) && !resultado.contains(t.getA())) {
                    resultado.add(t.getA());
                }
            }
        }

        // Recorriendo la lista de resultados y mandando a llamar el método cerradura.
        for (Estado estado : resultado) {
            cerraduraResult = cerradura(estado);
        }

        System.out.println("Resultado del movimiento: " + resultado.toString());

        return resultado;
    }
}
