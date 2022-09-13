import java.util.*;

public class AFDConverter {

    // Creando la lista de cerraduras del estado inicial.
    ArrayList<Estado> cerraduraInicial = new ArrayList<Estado>(); // Lista de cerraduras del estado inicial.
    private Estado afd;

    // Constructor del AFDConverter

    public AFDConverter() {
        afd = new Estado();
    }

    // Método para empezar a procesar los datos.
    public void Proceso(List<Transiciones> transiciones, Estado inicial, List<Estado> estadosFinales,
            ArrayList<String> alfabeto) {
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

            // Validando si la transición actual y la siguiente tienen el mismo símbolo.

            if (transiciones.get(i).getTransicionSiguiente().getSimboloTransicion().equals("&")) {
                if (transiciones.get(i).getTransicionAnterior().getSimboloTransicion().equals("&")) {
                    System.out.println("Transición: " + transiciones.get(i));
                    cerraduraInicial.add(transiciones.get(i).getA());
                }

            }

            // Imprimiendo la lista de cerraduras del estado inicial.
            System.out.println("Cerradura inicial: " + cerraduraInicial);
        }

    }
}
