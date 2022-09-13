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

    // Constructor del AFDConverter

    public AFDConverter() {
        afd = new Estado();
    }

    // Método para empezar a procesar los datos.
    public void Proceso(List<Transiciones> transiciones, Estado inicial, List<Estado> estadosFinales,
            ArrayList<String> alfabeto, Stack<Estado> estados) {
        // Recorriendo la lista para ver las transiciones que se tienen.

        for (int i = 0; i < transiciones.size(); i++) {
            System.out.println(transiciones.get(i).toString());

            // Calculando todas las transiciones del estado inicial que se pueden hacer con
            // &.
            // if (transiciones.get(i).getSimbolo().equals("&")) {
            // cerraduraInicial.add(transiciones.get(i).getA());
            // }

            // Validando si entre la transición actual y la siguiente hay una transición con
            // &.

            // Character simboloActual = transiciones.get(i).getSimbolo().toCharArray()[0];

            // if (transiciones.get(i).getSimboloTransicion().equals("&")
            // && !alfabeto.contains(simboloActual.toString())
            // && transiciones.get(i).getTransicionSiguiente().getSimboloTransicion()
            // .equals("&")
            // && transiciones.get(i).getTransicionAnterior().getSimbolo()
            // .contains("&")) {
            // {
            // System.out.println("Transición: " + transiciones.get(i));

            // }

            Character simboloActual = transiciones.get(i).getSimbolo().toCharArray()[0];

            // Volviendo el simbolo actual a String.
            String simboloActualString = simboloActual.toString();

            // // Validando si la transición actual y la siguiente tienen el mismo símbolo.

            // if
            // (transiciones.get(i).getTransicionAnterior().getSimboloTransicion().equals("&")
            // &&
            // transiciones.get(i).getTransicionSiguiente().getSimboloTransicion().equals("&"))
            // {
            // // Validando que el símbolo actual no sea un símbolo del alfabeto.
            // if (!transiciones.get(i).getTransicionSiguiente().getSimboloTransicion()
            // .contains(alfabeto.toString())
            // && !transiciones.get(i).getTransicionAnterior().getSimboloTransicion()
            // .contains(alfabeto.toString())) {
            // cerraduraInicial.add(transiciones.get(i).getDe());
            // }

            // }

            // Impriendo la cerradura del estado inicial.
            // System.out.println("Cerradura del estado inicial: " + cerraduraInicial);
        }

        cerradura(inicial);
    }

    // Creando método para calcular la cerradura de un estado.
    private ArrayList<Estado> cerradura(Estado estado) {

        // Creando una pila para guardar los estados que se van a procesar.
        Stack<Estado> pila = new Stack<Estado>();

        Transiciones tr = new Transiciones();

        // Guardando localmente el estado que se va a procesar.
        Estado estadoActual = estado;

        Estado s = new Estado();

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

            // Recorriendo la lista de transiciones del estado actual.
            for (Transiciones ts : tr.getTransicionesEstado(estadoActual)) {
                if (ts.getSimbolo().equals("&") && !resultado.contains(ts.getA())) { /// Validando si la transición
                                                                                     /// actual es con &.
                    System.out.println("Transición con &: " + ts);
                    resultado.add(ts.getA());
                    pila.push(ts.getA());
                }
            }
        }

        resultado.add(estadoActual); // Se agrega el estado actual a la lista de resultados.

        System.out.println("Resultado de la cerradura: " + resultado.toString());

        return resultado;

    }
}
