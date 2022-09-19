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

        cerraduraResult.push(cerradura(inicial)); // Guardando la cerradura del estado inicial. Esto será el
                                                  // estado inicial del AFD. El ArrayList se declara como false,
                                                  // dado que aún no se ha analizado.

        totalStates.push(cerradura(inicial)); // El resultado se agrega a los estados totales.

        // Imprimiendo la cerradura del estado inicial.
        System.out.println("Cerradura del estado inicial: " + cerraduraResult.toString());

        

        // Imprimiendo el Stack.
        System.out.println("Stack: " + cerraduraResult.toString());

        while (!cerraduraResult.isEmpty()) {

            // System.out.println("Estatos totales: " + totalStates.toString());

            // Se obtiene el estado que se va a procesar.
            ArrayList<Estado> estado = cerraduraResult.pop();

            // Matriz para el resultado del move.
            ArrayList<ArrayList<Estado>> move = new ArrayList<ArrayList<Estado>>();

            ArrayList<ArrayList<Estado>> cerradura = new ArrayList<ArrayList<Estado>>();

            //ArrayList<Estado> temporal = new ArrayList<Estado>();

            // System.out.println("Estado: " + estado.toString());

            // Guardando el alfabeto del AFD.
            alfabetoAFD = alfabeto;

            // Ahora lo que se tiene que hacer es obtener el siguiente estado del AFD es
            // obtener el conjunto de estados alcanzados en el AFN por medio de "a" partir
            // del estado inicial del AFD. Esto se alcanzará con eclosure(move(D,a)).
            // Donde D es el estado inicial del AFD y a es el símbolo del alfabeto.

            //System.out.println("Alfabeto: " + alfabetoAFD);
            
            for(int i = 0; i < alfabetoAFD.size(); i++){
                // System.out.println("Caracter del alfabeto: " + alfabetoAFD.get(i)); // Debug del alfabeto.

                for(int j = 0; j < totalStates.size(); j++){
                    System.out.println("Estado: " + totalStates.get(j));
                    System.out.println("Caracter del alfabeto: " + alfabetoAFD.get(i));

                    // Mandando a move el estado que se va a porcesar.
                    mover(estado, alfabetoAFD.get(i));
                    

                    System.out.println("Resultado del move: " + mover(estado, alfabetoAFD.get(i)));

                    cerradura(mover(estado, alfabetoAFD.get(i))); // Haciendo eClosure del resultado de move.

                    System.out.println("Resultado del eClosure: " + cerradura(mover(estado, alfabetoAFD.get(i))));

                    if(!totalStates.contains(cerradura(mover(estado, alfabetoAFD.get(i))))){
                        cerraduraResult.push(cerradura(mover(estado, alfabetoAFD.get(i)))); // Pusheando el resultado de eClosure del move con cada símbolo del alfabeto.
                        totalStates.push(cerradura(mover(estado, alfabetoAFD.get(i)))); // Pusheando el resultado a totalStates.
                    }

                }
            }

        }
        // Imprimiendo totalStates.
        // System.out.println(totalStates.toString());
        for (int i = 0; i < totalStates.size(); i++) {
            System.out.println((i+1)+ " " +"Total States: " + totalStates.get(i).toString());
        }

    }

    // Creando método para calcular la cerradura de un estado.
    public ArrayList<Estado> cerradura(ArrayList<Estado> estado) {

        // Se crea una lista para guardar las transiciones del estado actual.
        ArrayList<Estado> resultado = new ArrayList<Estado>();

        // Creando una pila para guardar los estados que se van a procesar.
        Stack<Estado> pila = new Stack<Estado>();

        Transiciones tr = new Transiciones();

        // Guardando el contenido de la lista de estados en la pila.
        for (int i = 0; i < estado.size(); i++) {
            pila.push(estado.get(i));
            resultado.add(estado.get(i));
        }

        // System.out.println("Pila: " + pila.toString());

        Estado t = new Estado(); // Creando instancia de la clase Estado.

        // Se recorre la pila de estados.
        while (!pila.isEmpty()) {

            t = pila.pop(); // Sacando un estado de la pila.

            // Recorriendo la lista de transiciones del estado actual.
            for (Transiciones ts : tr.getTransicionesEstado(t)) {
                // System.out.println("Transición: " + ts.getDe() + " " + ts.getSimbolo() + " "
                // + ts.getA());
                if (ts.getSimbolo().equals("&") && !resultado.contains(ts.getA())) {

                    // System.out.println("Estado: " + ts.getA());
                    pila.push(ts.getA());
                    resultado.add(ts.getA());

                }

            }

        }

        return resultado;

    }

    // Creando método para calcular el movimiento de un estado.
    private ArrayList<Estado> mover(ArrayList<Estado> estados, String string) {

        // Se crea una lista para guardar las transiciones del estado actual.
        ArrayList<Estado> resultado = new ArrayList<Estado>();

        // Creando una pila para guardar los estados que se van a procesar.
        Stack<Estado> pila = new Stack<Estado>();

        Transiciones tr = new Transiciones();

        // Guardando el contenido de la lista de estados en la pila.
        for (int i = 0; i < estados.size(); i++) {
            pila.push(estados.get(i));
            // resultado.add(estados.get(i));
        }

        // System.out.println("Pila: " + pila.toString());

        Estado t = new Estado(); // Creando instancia de la clase Estado.

        // Se recorre la pila de estados.
        while (!pila.isEmpty()) {

            t = pila.pop(); // Sacando un estado de la pila.

            // Se obtienen las transiciones del estado actual.
            for (Transiciones ts : tr.getTransicionesEstado(t)) {

                // System.out.println("Transición: " + ts.getDe() + " " + ts.getSimbolo() + " "
                // + ts.getA());

                // Calculando el movimiento de los estados.
                if (string.contains(ts.getSimbolo()) && !resultado.contains(ts.getA())) {

                    // System.out.println("Estado: " + ts.getA());
                    pila.push(ts.getA());
                    resultado.add(ts.getA());

                }

            }

        }

        return resultado;
    }
}
