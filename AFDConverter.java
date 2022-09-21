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

    // Creando método para calcular el movimiento de un estado.
    private static HashSet<StatesAFD> mover_statesAFD(HashSet<StatesAFD> estados, String string) {
        HashSet<StatesAFD> resultado = new HashSet<StatesAFD>(); // Creando HashSet para guardar los resultados de los
                                                                 // estados
        // alcanzados.
        Iterator<StatesAFD> it = estados.iterator(); // Creando iterador para recorrer los estados del HashSet.

        while (it.hasNext()) {
            AFD tr = new AFD();

            for (AFD ts : tr.getTransicionesEstado(estados)) { // Recorriendo las
                                                               // transiciones
                                                               // del estado.
                StatesAFD s = ts.getA(); // Obteniendo el estado al que se llega con la transición.
                String simbolo = ts.getSimbolo(); // Obteniendo el símbolo de la transición.

                if (simbolo.equals(string)) {
                    resultado.add(s);
                }
            }
        }

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

    public static void MinimizacionAFD() {
        // Creamos el conjunto de particiones P
        HashSet<HashSet<StatesAFD>> P = new HashSet<HashSet<StatesAFD>>();

        /*
         * separar los estados entre los que pertenecen al conjunto de estados de
         * aceptacion
         * y los que no, y agregar estos grupos aun conjutno de partició P
         * Ojo: esto significa que la particion P al principio tiene un conjunto con
         * los estados de no acpetacion y otro grupo con los de aceptacion
         */
        HashSet<StatesAFD> estadosSinAceptacion = new HashSet<StatesAFD>();
        for (int i = 0; i < getEstados().size(); i++) {
            if (!getAceptacion().contains(getEstados().get(i))) {
                estadosSinAceptacion.add(getEstados(i));
            }
        }
        P.add(estadosSinAceptacion);
        P.add(getAceptacion());
        // System.out.println(particionP);

        int key = 0;
        HashMap<StatesAFD, HashSet<Integer>> L = new HashMap();

        for (HashSet<StatesAFD> a : P) {
            HashSet<StatesAFD> grupoG = a;
            // System.out.println(grupoG);
            for (StatesAFD s : grupoG) {
                HashSet<StatesAFD> statesG = new HashSet<StatesAFD>();
                statesG.add(s);
                HashSet<Integer> Ds = new HashSet<Integer>();
                // System.out.println(s.getTransiciones());
                for (String alfabeto : getAlfabeto()) {
                    HashSet<StatesAFD> t = mover_statesAFD(statesG, alfabeto);

                    for (int j = 0; j < P.size(); j++) {

                        HashSet<StatesAFD> statesP = (HashSet<StatesAFD>) P.toArray()[j];
                        if (statesP.contains(t)) {
                            Ds.add(j);

                        }
                        L.put(s, Ds);
                        // System.out.println(Ds + "Ds");

                    }
                }

                // System.out.println(Ds+ " Ds");
                key++;
            }

            // System.out.println(L);

            /*
             * tabla2 = new HashMap();
             * for (Estado e : grupoG) {
             * ArrayList<Integer> alcanzados = tablaDs.get(e);
             * if (tabla2.containsKey(alcanzados))
             * tabla2.get(alcanzados).add(e);
             * else {
             * ArrayList<Estado> tmp = new ArrayList();
             * tmp.add(e);
             * tabla2.put(alcanzados, tmp);
             * }
             * }
             */
            int i = 0;

            ArrayList Ki = new ArrayList();
            // Iterator <StatesAFD,HashSet<Integer>> it = L.keySet().iterator();
            for (StatesAFD estado : L.keySet()) {
                HashSet<Integer> estado_num = L.get(estado);
                HashMap<HashSet<Integer>, HashSet<StatesAFD>> tabla2 = new HashMap();
                for (StatesAFD e : grupoG) {
                    HashSet<Integer> alcanzados = estado_num;
                    if (tabla2.containsKey(alcanzados))
                        tabla2.get(alcanzados).add(e);
                    else {
                        HashSet<StatesAFD> tmp = new HashSet();
                        tmp.add(e);
                        tabla2.put(alcanzados, tmp);
                    }
                }

                i++;

                System.out.println("----");
                System.out.println(P);
                System.out.println(Ki);
                System.out.println(grupoG);
                System.out.println("----");
                if (Ki.get(0) != grupoG) {
                    P.remove(grupoG);
                    System.out.println(Ki);
                    System.out.println("ki" + Ki.get(1));
                    for (int j = 0; j < Ki.size(); j++) {
                        P.add((HashSet<StatesAFD>) Ki.get(j));
                    }

                }

            }
            System.out.println(P);
        }

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

        // Agregando cada caracter de la cadena a un ArrayList.
        for (int i = 0; i < cadena.length(); i++) {
            cadena_array.add(String.valueOf(cadena.charAt(i)));

            // Si la cadena contiene un caracter que no está en el alfabeto, se termina la
            // simulación.
            if (!alfabetoAFDs.contains(String.valueOf(cadena.charAt(i)))) {
                System.out.println("La cadena contiene un caracter que no está en el alfabeto.");
                return;
            }

            // Calculando el move de cada estado de es con el caracter de la cadena.
            System.out.println("Estado: " + es);
            System.out.println("Caracter: " + cadena_array.get(i));
            es = mover(es, cadena_array.get(i));

            System.out.println("Resultado del move: " + es);

        }

        // Verificando que s esté en el conjunto de estados de aceptación.
        if (estados_aceptacion.contains(es)) {
            System.out.println("La cadena es aceptada.");
            return;
        } else {
            System.out.println("La cadena no es aceptada.");
            return;
        }

    }
}
