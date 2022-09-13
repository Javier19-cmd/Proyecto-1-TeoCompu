import java.util.*;

public class AFDConverter {

    // Creando la lista de cerraduras del estado inicial.
    List<Estado> cerraduraInicial = new ArrayList<Estado>(); // Lista de cerraduras del estado inicial.

    // Método para empezar a procesar los datos.
    public void Proceso(List<Transiciones> transiciones, Estado inicial) {
        // Recorriendo la lista para ver las transiciones que se tienen.

        for (int i = 0; i < transiciones.size(); i++) {
            System.out.println(transiciones.get(i).toString());

            // Si la transición &, entonces la transición en la lista.
            if (transiciones.get(i).getSimbolo().equals("&") && transiciones.get(i).getDe().equals(inicial)) {
                // Agregando el estado de inicio a la lista de cerraduras del estado inicial.
                // cerraduraInicial.add(transiciones.get(i).getDe());
                // Agregando el estado final a la lista de cerraduras del estado inicial.
                cerraduraInicial.add(transiciones.get(i).getA());
            }

        }
        // Imprimiendo la lista de cerraduras del estado inicial.
        System.out.println("Cerradura inicial: " + cerraduraInicial);
    }
}
