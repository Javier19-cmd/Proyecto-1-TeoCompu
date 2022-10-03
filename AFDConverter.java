import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

    private Stack<StatesAFD> estados_iniciales = new Stack<>();

    Stack<StatesAFD> estados_finales = new Stack<>();

    static HashSet<HashSet<Estado>> resultado = new HashSet<HashSet<Estado>>();

    // Creando lista para guardar los estados del AFD.
    static ArrayList<StatesAFD> estadosAFD = new ArrayList<StatesAFD>();

    // Creando arreglo para guardar el estado inicial del AFD.
    static HashSet<HashSet<Estado>> estado_inicial_AFD = new HashSet<HashSet<Estado>>();

    // Matriz para el resultado de eClosure.

    List<Estado> eClosure_List = new CopyOnWriteArrayList<Estado>();

    static ArrayList<AFD> resultado_trans = new ArrayList<AFD>(); // Lista para guardar el resultado de las
                                                                  // transiciones.

    private static HashSet<StatesAFD> estados_aceptacion = new HashSet<StatesAFD>(); // Hashset para guardar los estados
                                                                                     // de
    // aceptación.

    private static ArrayList<String> alfabetoAFDs = new ArrayList<String>(); // ArrayList para el alfabeto.

    // Arreglo para el estado inicial del AFD.
    static HashSet<StatesAFD> estado_inicial = new HashSet<StatesAFD>();

    static HashSet<HashSet<Estado>> subconjunto_aceptacion = new HashSet<HashSet<Estado>>(); // HashSet para guardar el
                                                                                             // subconjunto de estados
                                                                                             // de aceptación.

    // Guardando el estado de aceptación en un ArrayList para escribirlo en el
    // archivo.
    static ArrayList<StatesAFD> grupo2Archivo = new ArrayList<StatesAFD>();

    // Guardando el estado inicial en un ArrayList para escribirlo en el archivo.
    static ArrayList<StatesAFD> grupo1_inicial = new ArrayList<StatesAFD>();

    // Creando HashSet para guardar las transiciones del AFD.
    static HashSet<String> transicionesAFD = new HashSet<String>();

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

        // Guardando el resultado de la cerradura inicial en otro arreglo.
        estado_inicial_AFD.add(cerraduraResult);

        // System.out.println("Resultado con cerraduraResult: " + resultado.toString());

        // Guardando el alfabeto en un ArrayList global.
        alfabetoAFDs = alfabeto;

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
                // System.out.println((j)+"Resultado: " + resultado.toArray()[j]);

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

                        // Guardando el subconjunto que tiene el estado de aceptación.
                        subconjunto_aceptacion.add(eClosure);
                    }
                }

                resultado_trans.add(afd); // Guardando la transición.

            }

        }

        // Guardando el estado inicial en estado_inicial.
        estado_inicial.add(estadosAFD.get(0));

        // Área de debuggeo.

        // Imprimiendo cada resultado de la transición.
        for (int i = 0; i < resultado_trans.size(); i++) {
            System.out.println(resultado_trans.get(i));
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
    private static HashSet<Estado> mover(HashSet<Estado> estados, String string) {
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

    public static HashSet<StatesAFD> moveState(StatesAFD estado, String simbolo) {
        HashSet<StatesAFD> alcanzados = new HashSet<StatesAFD>();

        // Metiendo el estado a un HashSet para poder utilizar el método mover.
        HashSet<StatesAFD> estadoSet = new HashSet<StatesAFD>();

        estadoSet.add(estado);

        // Calculando el movimiento de un estado del AFD.
        for (StatesAFD s : moves(estadoSet, simbolo)) {
            alcanzados.add(s);
        }

        return alcanzados;
    }

    // Método de move para el AFD.
    public static HashSet<StatesAFD> moves(HashSet<StatesAFD> estado, String simbolo) {
        HashSet<StatesAFD> resultado = new HashSet<StatesAFD>(); // Creando HashSet para guardar los resultados de los
                                                                 // estados
        // alcanzados.

        // Instanciando la clase de transiciones.
        AFD afd = new AFD();

        // Imprimiendo las transiciones.
        // System.out.println("Transiciones: " + afd.getTransicionesEstado(estado));

        for (AFD ts : new AFD().getTransicionesEstado(estado)) { // Recorriendo las
                                                                 // transiciones
                                                                 // del estado.
            StatesAFD s = ts.getA(); // Obteniendo el estado al que se llega con la transición.
            String simbolo_tr = ts.getSimbolo(); // Obteniendo el símbolo de la transición.

            // System.out.println("Estado: " + s.toString() + " Símbolo: " + simbolo_tr);

            if (simbolo_tr.equals(simbolo)) {
                resultado.add(s);
            }
        }

        // System.out.println("Resultado: " + resultado);

        return resultado;
    }

    // Getters funcionales.

    // Getter de los estados del AFD.
    public static ArrayList<StatesAFD> getEstados() {
        return estadosAFD;
    }

    public static StatesAFD getEstados(int i) {
        return estadosAFD.get(i);
    }

    // Getter del alfabeto.
    public static ArrayList<String> getAlfabeto() {
        return alfabetoAFDs;
    }

    // Getter del estado incial.
    public static HashSet<StatesAFD> getInicio() {
        return estado_inicial;
    }

    // Getter de las transiciones.
    public static HashSet<StatesAFD> getAceptacion() {
        return estados_aceptacion;
    }

    /*
     * Método para realizar la minimización con el
     * algoritmo de Hopcroft
     */
    public static void MinimizacionAFD() {
        // Creando un ArrayList para guardar los estados que se van a dividir.
        ArrayList<ArrayList<StatesAFD>> particiones = new ArrayList<ArrayList<StatesAFD>>();

        // Dividiendo los estados en dos grupos.
        ArrayList<StatesAFD> grupo1 = new ArrayList<StatesAFD>();
        ArrayList<StatesAFD> grupo2 = new ArrayList<StatesAFD>();

        // Guardando el grupo1 en el grupo1_inicial.

        // Agregando los estados de aceptación al grupo 1.
        for (int i = 0; i < estados_aceptacion.size(); i++) {
            // Obteniendo del HashSet de estados de aceptación el estado.
            StatesAFD estado = (StatesAFD) estados_aceptacion.toArray()[i];
            grupo1.add(estado);
        }

        // Agregando los estados de no aceptación al grupo 2.
        for (int i = 0; i < estadosAFD.size(); i++) {
            // Obteniendo del ArrayList de estados el estado.
            StatesAFD estado = estadosAFD.get(i);

            // Verificando que el estado no esté en el grupo 1.
            if (!grupo1.contains(estado)) {
                grupo2.add(estado);
            }
        }

        // Debuggeando las dos particiones.
        // System.out.println("Grupo 1: " + grupo1);
        // System.out.println("Grupo 2: " + grupo2);

        // Agregando los grupos a la lista de particiones.
        particiones.add(grupo1);
        particiones.add(grupo2);

        // Imprimiendo las particiones.
        System.out.println("Particiones: " + particiones);

        // Agregando los estados del grupo 2 al ArrayList.
        for (int i = 0; i < grupo2.size(); i++) {
            grupo2Archivo.add(grupo2.get(i));
        }

        // Agregando los estados del grupo 2 al ArrayList.
        for (int i = 0; i < grupo1.size(); i++) {
            grupo1_inicial.add(grupo1.get(i));
        }

        // Ahora se procede a crear nuevas particiones.
        // Creando un HashSet para guardar las nuevas particiones.
        HashSet<HashSet<StatesAFD>> nuevas_particiones = new HashSet<HashSet<StatesAFD>>();

        HashMap<String, ArrayList<StatesAFD>> estados_alcanzados = new HashMap<String, ArrayList<StatesAFD>>();

        // Para cada grupo g en particiones.
        for (ArrayList<StatesAFD> grupoG : particiones) {
            // System.out.println("Grupo G: " + grupoG);

            // Por cada estado q en g, se calcula el movimiento de q con cada símbolo del
            // alfabeto.
            // Creando un HashMap para guardar los estados alcanzados.

            for (StatesAFD s : grupoG) {
                // Creando un ArrayList para guardar los estados alcanzados.
                ArrayList<StatesAFD> alcanzados = new ArrayList<StatesAFD>();

                // Para cada simbolo del alfabeto, se calcula el movimiento de cada estado y se
                // verifica en que grupo está el estado alcanzado.
                for (String simbolo : alfabetoAFDs) {
                    // System.out.println("Símbolo: " + simbolo);

                    // Obteniendo el estado alcanzado.
                    HashSet<StatesAFD> estado_alcanzado = moveState(s, simbolo);

                    // System.out.println("Estado alcanzado: " + estado_alcanzado);

                    // Agregando el estado alcanzado al ArrayList de estados alcanzados.
                    alcanzados.addAll(estado_alcanzado);
                }
                // Guardando los estados alcanzados en el HashMap.
                estados_alcanzados.put(s.toString(), alcanzados);
            }
        }

        // Verificando a que grupo pertenece cada estado alcanzado.
        for (String estado : estados_alcanzados.keySet()) {
            // System.out.println("Estado: " + estado);

            // Obteniendo los estados alcanzados.
            ArrayList<StatesAFD> estados_alcanzados_estado = estados_alcanzados.get(estado);

            // System.out.println("Estados alcanzados: " + estados_alcanzados_estado);

            // Creando un ArrayList para guardar los grupos a los que pertenece el estado.
            ArrayList<ArrayList<StatesAFD>> grupos = new ArrayList<ArrayList<StatesAFD>>();

            // Para cada estado alcanzado.
            for (StatesAFD estado_alcanzado : estados_alcanzados_estado) {
                // System.out.println("Estado alcanzado: " + estado_alcanzado);

                // Para cada grupo.
                for (ArrayList<StatesAFD> grupo : particiones) {
                    // System.out.println("Grupo: " + grupo);

                    // Verificando si el estado alcanzado está en el grupo.
                    if (grupo.contains(estado_alcanzado)) {
                        // System.out.println("Estado alcanzado: " + estado_alcanzado + " está en el
                        // grupo: " + grupo);

                        // Agregando el grupo a la lista de grupos.
                        grupos.add(grupo);
                    }
                }
            }

            // System.out.println("Grupos: " + grupos);

            // Creando un HashSet para guardar los estados que pertenecen a los grupos.
            HashSet<StatesAFD> estados = new HashSet<StatesAFD>();

            // Para cada grupo.
            for (ArrayList<StatesAFD> grupo : grupos) {
                // System.out.println("Grupo: " + grupo);

                // Para cada estado del grupo.
                for (StatesAFD estado_grupo : grupo) {
                    // System.out.println("Estado del grupo: " + estado_grupo);

                    // Agregando el estado al ArrayList de estados.
                    estados.add(estado_grupo);
                }
            }

            // System.out.println("Estados: " + estados);

            // Agregando los estados a la lista de nuevas particiones.
            nuevas_particiones.add(estados);

        }

        // System.out.println("Nuevas particiones: " + nuevas_particiones);

        // Con las nuevas particiones, se verifica con que estado se puede llegar a cada
        // partición.
        // Creando un HashMap para guardar los estados alcanzados.
        HashMap<String, HashSet<StatesAFD>> estados_alcanzados_particiones = new HashMap<String, HashSet<StatesAFD>>();

        // Para cada partición.
        for (HashSet<StatesAFD> particion : nuevas_particiones) {
            // System.out.println("Partición: " + particion);

            // Creando un ArrayList para guardar los estados alcanzados.
            HashSet<StatesAFD> estados_alcanzadoss = new HashSet<StatesAFD>();

            // Para cada estado de la partición.
            for (StatesAFD estado : particion) {
                // System.out.println("Estado: " + estado);

                // Para cada símbolo del alfabeto.
                for (String simbolo : alfabetoAFDs) {
                    // System.out.println("Símbolo: " + simbolo);

                    // Obteniendo el estado alcanzado.
                    HashSet<StatesAFD> estado_alcanzado = moveState(estado, simbolo);

                    // System.out.println("Estado alcanzado: " + estado_alcanzado);

                    // Agregando el estado alcanzado al ArrayList de estados alcanzados.
                    estados_alcanzadoss.addAll(estado_alcanzado);
                }
            }

            // Guardando los estados alcanzados en el HashMap.
            estados_alcanzados_particiones.put(particion.toString(), estados_alcanzadoss);
            // System.out.println("Estados alcanzados: " + estados_alcanzados);
        }

        System.out.println("Particiones: " + estados_alcanzados_particiones);

        // Imprimiendo con que símbolo se puede llegar a la partición.
        for (String particion : estados_alcanzados_particiones.keySet()) {
            // System.out.println("Partición: " + particion);

            // Obteniendo los estados alcanzados.
            HashSet<StatesAFD> estados_alcanzados_particion = estados_alcanzados_particiones.get(particion);

            // System.out.println("Estados alcanzados: " + estados_alcanzados_particion);

            // Para cada estado alcanzado.
            for (StatesAFD estado_alcanzado : estados_alcanzados_particion) {
                // System.out.println("Estado alcanzado: " + estado_alcanzado);

                // Para cada grupo.
                for (ArrayList<StatesAFD> grupo : particiones) {
                    // System.out.println("Grupo: " + grupo);

                    // Verificando si el estado alcanzado está en el grupo.
                    if (grupo.contains(estado_alcanzado)) {
                        // System.out.println("Estado alcanzado: " + estado_alcanzado + " está en el
                        // grupo: " + grupo);

                        // Obteniendo el símbolo.
                        String simbolo = getSymbol(estado_alcanzado, grupo);

                        if (alfabetoAFDs.contains(simbolo)) {

                            // Obteniendo el estado desde donde se va a la partición hasta donde se va a la
                            // partición y el símbolo.
                            ArrayList<StatesAFD> estado_desde_particion = getFromState(particion, grupo);

                            // Obteniendo el estado hasta donde se va a la partición.
                            ArrayList<StatesAFD> estado_hasta_particion = getToState(particion, grupo);

                            // System.out.println("Estado desde partición: " + estado_desde_particion);
                            // Obteniendo el símbolo hacia donde se va a la partición.
                            String simbolos = getSymbol(estado_alcanzado, grupo);

                            // Armando la transición.
                            String transicion = estado_desde_particion + " - " + simbolos + " -> "
                                    + estado_hasta_particion;

                            // System.out.println("Transición: " + transicion);

                            // Agregando la transición al HashSet de transiciones.
                            transicionesAFD.add(transicion);

                        }

                        // System.out.println("Símbolo: " + simbolo);

                    }
                }
            }
        }

    }

    // Método para escribir el AFD minimizado.
    public static void writeAFDMinimizado() {
        try {
            // Creando el archivo con codificado UTF-8.
            File archivo = new File("automataMinimizado.txt");

            // Creando el archivo si no existe.
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            // Creando el escritor.
            FileWriter escritor = new FileWriter(archivo);

            BufferedWriter bw = new BufferedWriter(escritor);

            // System.out.println("Expresión regular en el método escribirArchivo: " +
            // expresion_postfixs + "");

            // escritor.write("Expresión regular: " + expresion_postfixs);

            // Escribiendo las transiciones.
            bw.write("Transiciones:" + transicionesAFD.toString());
            bw.newLine();

            // Escribiendo el alfabeto.
            bw.write("Alfabeto: " + alfabetoAFDs.toString());
            bw.newLine();

            // Escribiendo los estados que son de aceptación.
            bw.write("Subconjuntos de aceptación: " + grupo2Archivo.toString());
            bw.newLine();

            // Escribiendo el estado inicial.
            bw.write("Estado inicial: " + grupo1_inicial.toString());
            bw.newLine();

            // Cerrando el escritor.
            bw.close();

            // Escribiendo en el archivo.
        } catch (Exception e) {
            System.out.println("Error al escribir el archivo.");
        }
    }

    // Método para obtener el estado hacia donde se va a la partición.
    public static ArrayList<StatesAFD> getToState(String particion, ArrayList<StatesAFD> grupo) {
        // System.out.println("Partición: " + particion);
        // System.out.println("Grupo: " + grupo);

        // Obteniendo el estado hacia donde se va a la partición.
        String estado_hasta_particion = "";

        ArrayList<StatesAFD> estados_hasta_particion = new ArrayList<StatesAFD>();

        // Para cada estado del grupo.
        for (StatesAFD estado_grupo : grupo) {
            // System.out.println("Estado del grupo: " + estado_grupo);

            // Obteniendo el estado hacia donde se va a la partición.
            estado_hasta_particion = estado_grupo.toString();

            estados_hasta_particion = grupo;
        }

        // System.out.println("Estado hacia donde se va a la partición: " +
        // estado_hasta_particion);

        return estados_hasta_particion;
    }

    // Método para obtener el grupo desde donde se va a la partición hasta donde se
    // va a la partición.
    public static ArrayList<StatesAFD> getFromState(String particion, ArrayList<StatesAFD> grupo) {
        // System.out.println("Partición: " + particion);
        // System.out.println("Grupo: " + grupo);

        // Obteniendo el estado desde donde se va a la partición.
        String estado_desde_particion = "";

        ArrayList<StatesAFD> estados_desde_particion = new ArrayList<StatesAFD>();

        // Para cada estado del grupo.
        for (StatesAFD estado_grupo : grupo) {
            // System.out.println("Estado del grupo: " + estado_grupo);

            // Obteniendo el estado desde donde se va a la partición.
            estado_desde_particion = estado_grupo.toString();

            // Agregando el grupo desde donde se va a la partición.
            estados_desde_particion = grupo;

            // Obteniendo la partición del estado.
            // System.out.println("Partición del estado: " + estado_grupo + " es: " +
            // grupo);
        }

        // System.out.println("Estado desde donde se va a la partición: " +
        // estado_desde_particion);

        return estados_desde_particion;
    }

    // Método para obtener el símbolo con el que se puede llegar a un estado.
    public static String getSymbol(StatesAFD estado_alcanzado, ArrayList<StatesAFD> grupo) {
        // Para cada estado del grupo.
        for (StatesAFD estado_grupo : grupo) {
            // System.out.println("Estado del grupo: " + estado_grupo);

            // Para cada símbolo del alfabeto.
            for (String simbolo : alfabetoAFDs) {
                // System.out.println("Símbolo: " + simbolo);

                // Obteniendo el estado alcanzado.
                HashSet<StatesAFD> estado_alcanzado2 = moveState(estado_grupo, simbolo);

                // System.out.println("Estado alcanzado: " + estado_alcanzado2);

                // Verificando si el estado alcanzado es el estado que se busca.
                if (estado_alcanzado2.contains(estado_alcanzado)) {
                    // System.out.println("Estado alcanzado: " + estado_alcanzado + " es el estado
                    // que se busca.");

                    // Retornando el símbolo.
                    return simbolo;
                }
            }
        }

        // Retornando null.
        return null;
    }

    public static HashSet<AFD> getAFDMinimizado(HashMap<String, HashSet<StatesAFD>> estados_alcanzados) {
        HashSet<AFD> afd = new HashSet<AFD>();

        return afd;
    }

    // Método para escribir el archivo con extensión txt.
    public static void EscribirArchivo() {
        try {
            // Creando el archivo con codificado UTF-8.
            File archivo = new File("automataDe.txt");

            // Creando el archivo si no existe.
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            // Creando el escritor.
            FileWriter escritor = new FileWriter(archivo);

            BufferedWriter bw = new BufferedWriter(escritor);

            // System.out.println("Expresión regular en el método escribirArchivo: " +
            // expresion_postfixs + "");

            // escritor.write("Expresión regular: " + expresion_postfixs);

            // Escribiendo el estado inicial.
            bw.write("Estado inicial: " + estado_inicial + "");
            bw.newLine();

            // Escribiendo el alfabeto.
            bw.write("Alfabeto: " + alfabetoAFDs + "");
            bw.newLine();

            // Escribiendo los estados de aceptación.
            bw.write("Estados de aceptación: " + estados_aceptacion + "");
            bw.newLine();

            // Escribiendo los estados del AFD.
            bw.write("Estados del AFD: " + estadosAFD + "");
            bw.newLine();

            // Escribiendo las transiciones.
            bw.write("Transiciones del AFD: " + resultado_trans + "");
            bw.newLine();

            // Cerrando el escritor.
            bw.close();

            // Escribiendo en el archivo.
        } catch (Exception e) {
            System.out.println("Error al escribir el archivo.");
        }
    }

    // Método para poder simular el AFD que se armó desde el AFN.
    public static void Simulacion() {
        long startTime = System.nanoTime(); // Conteo inicial de la simulación.
        System.out.println("Ingrese la cadena a evaluar: ");
        Scanner sc = new Scanner(System.in);
        String cadena = sc.nextLine();

        System.out.println("La cadena es: " + cadena);

        // Ingresando la cadena a un ArrayList.
        ArrayList<String> cadena_array = new ArrayList<String>();

        // Guardando el estado inicial del AFD en una variable.
        // Conviritendo el HashSet a un arreglo.
        StatesAFD[] estadosAFD_array = estadosAFD.toArray(new StatesAFD[estadosAFD.size()]);

        // Sacando el primer elemento del arreglo.
        StatesAFD estado_inicial = estadosAFD_array[0];

        // System.out.println("Estado inicial: " + estado_inicial);

        // Imprimiendo el HashSet de resultados.
        // System.out.println("HashSet de resultados: " + resultado.toString());

        HashSet<Estado> es = (HashSet<Estado>) estado_inicial_AFD.toArray()[0];

        System.out.println("Primera posición del HashSet: " + es);

        HashSet<StatesAFD> es2 = new HashSet<StatesAFD>(); // HashSet de estados del AFD.

        // Agregando cada caracter de la cadena a un ArrayList.
        for (int i = 0; i < cadena.length(); i++) {
            cadena_array.add(String.valueOf(cadena.charAt(i)));

            // Si la cadena contiene un caracter que no está en el alfabeto, se termina la
            // simulación.
            if (!alfabetoAFDs.contains(String.valueOf(cadena.charAt(i)))) {
                System.out.println("La cadena contiene un caracter que no está en el alfabeto.");
                return;
            }

            AFD afd = new AFD();

            afd.getTransicionesEstado(AFDConverter.estado_inicial);

            // System.out.println("Tipo del estado inicial: " +
            // AFDConverter.estado_inicial.getClass());

            // Calculando el move del estado.
            // es = move(AFDConverter.estado_inicial.getClass(),
            // String.valueOf(cadena.charAt(i)));

            System.out.println(
                    "Resultado del move: " + moves(AFDConverter.estado_inicial, String.valueOf(cadena.charAt(i))));

            // Guardando el resultado del move en una variable.
            es2 = moves(AFDConverter.estado_inicial, String.valueOf(cadena.charAt(i)));

            long endTime = System.nanoTime(); // Tiempo de finalización.

            double duracion = (endTime - startTime) / (1e6); // Tiempo de duración de la simulación.

            System.out.println("El tiempo de duración fue: " + duracion + "ms.");

        }

        // System.out.println("Estados del AFD: " + estadosAFD + "");

        // System.out.println("Subconjuntos del AFD: " + resultado + "");

        // Buscando el estado en la lista de estados.
        for (int i = 0; i < resultado.size(); i++) {

            // Si el estado está en la lista de estados de aceptación, se acepta la cadena.
            if (estados_aceptacion.contains(es2)) {
                System.out.println("La cadena es aceptada.");
            } else {
                System.out.println("La cadena no es aceptada.");
            }
            return;

        }

    }

    // Método para hacer la simulación del AFD minimizado.
    public static void SimulacionMinimizado() {
        long startTime = System.nanoTime(); // Conteo inicial de la simulación.
        System.out.println("Ingrese la cadena a evaluar: ");
        Scanner sc = new Scanner(System.in);
        String cadena = sc.nextLine();

        System.out.println("La cadena es: " + cadena);

        // Ingresando la cadena a un ArrayList.
        ArrayList<String> cadena_array = new ArrayList<String>();

        // Guardando el estado inicial del AFD en una variable.
        // Conviritendo el HashSet a un arreglo.
        StatesAFD[] estadosAFD_array = estadosAFD.toArray(new StatesAFD[estadosAFD.size()]);

        // Sacando el primer elemento del arreglo.
        StatesAFD estado_inicial = estadosAFD_array[0];

        // System.out.println("Estado inicial: " + estado_inicial);

        // Imprimiendo el HashSet de resultados.
        // System.out.println("HashSet de resultados: " + resultado.toString());

        HashSet<Estado> es = (HashSet<Estado>) estado_inicial_AFD.toArray()[0];

        System.out.println("Primera posición del HashSet: " + es);

        HashSet<StatesAFD> es2 = new HashSet<StatesAFD>(); // HashSet de estados del AFD.

        // Agregando cada caracter de la cadena a un ArrayList.
        for (int i = 0; i < cadena.length(); i++) {
            cadena_array.add(String.valueOf(cadena.charAt(i)));

            // Si la cadena contiene un caracter que no está en el alfabeto, se termina la
            // simulación.
            if (!alfabetoAFDs.contains(String.valueOf(cadena.charAt(i)))) {
                System.out.println("La cadena contiene un caracter que no está en el alfabeto.");
                return;
            }

            AFD afd = new AFD();

            afd.getTransicionesEstado(AFDConverter.estado_inicial);

            // System.out.println("Tipo del estado inicial: " +
            // AFDConverter.estado_inicial.getClass());

            // Calculando el move del estado.
            // es = move(AFDConverter.estado_inicial.getClass(),
            // String.valueOf(cadena.charAt(i)));

            System.out.println(
                    "Resultado del move: " + moves(AFDConverter.estado_inicial, String.valueOf(cadena.charAt(i))));

            // Guardando el resultado del move en una variable.
            es2 = moves(AFDConverter.estado_inicial, String.valueOf(cadena.charAt(i)));

            long endTime = System.nanoTime(); // Tiempo de finalización.

            double duracion = (endTime - startTime) / (1e6); // Tiempo de duración de la simulación.

            System.out.println("El tiempo de duración fue: " + duracion + "ms.");

            // Verificando si el estado es de aceptación.
            if (estados_aceptacion.contains(es2)) {
                System.out.println("La cadena es aceptada.");
            } else {
                System.out.println("La cadena no es aceptada.");
            }
        }
    }
}
