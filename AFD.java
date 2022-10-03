/*
 * Clase de construcción para lo de subconjuntos AFD.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class AFD {
    StatesAFD de, a;
    String simbolo;
    private static Set<Integer>[] followPos;
    private static Node root;
    private static Set<State> DStates;
    private static HashSet<String> AFDResult;

    private static Set<String> input; //Set de caracteres de entrada que se usan en la expresion regular
    /*
    * Un número se asigna a cada carácter (incluso los duplicados)
    * 
    * @param symbNum es un mapa hash tiene una clave que menciona el número y tiene
    * un valor que menciona el carácter correspondiente o a veces una cadena
    * para los caracteres se siguen con barra invertida como "\ *"
    */
    private static HashMap<Integer, String> symbNum;

    public AFD() {

    }

    public AFD(StatesAFD de, StatesAFD a, String simbolo) {
        this.a = a;
        this.de = de;
        this.simbolo = simbolo;

        this.de.agregarEstadoSiguiente(a);
        this.a.agregarEstadoAnterior(de);
    } // Juntando estados del AFD.

    public String toString() {
        return this.de + "-" + this.simbolo + "->" + this.a;
    }

    // Transiciones de un estado.
    public StatesAFD getDe() {
        return this.de;
    }

    public StatesAFD getA() {
        return this.a;
    }

    public String getSimbolo() {
        return this.simbolo;
    }

    public ArrayList<AFD> getTransicionesEstado(HashSet<StatesAFD> estado) { // Método para obtener las transiciones de
                                                                             // un estado.
        ArrayList<AFD> transiciones = new ArrayList<AFD>();

        // Imprimiendo el estado que se está analizando.
        // System.out.println("Estado: " + estado);

        // Recorriendo el arreglo de las transiciones.
        for (int i = 0; i < AFDConverter.resultado_trans.size(); i++) {
            // System.out.println("Transición: " + AFDConverter.resultado_trans.get(i));
            // Sacando cada estado de estado.
            for (StatesAFD s : estado) {
                // Verificando que s esté en resultado_trans.
                if (AFDConverter.resultado_trans.get(i).getDe().equals(s)) {
                    transiciones.add(AFDConverter.resultado_trans.get(i));
                }
            }
        }

        // System.out.println("Resultado final: " + transiciones);
        return transiciones;
    }

    // Método para crear la transición entre los grupo.
    public HashSet<StatesAFD> crearTransicion(HashSet<StatesAFD> de, HashSet<StatesAFD> a, String simbolo) {
        // Creando el HashSet para el resultado.
        HashSet<StatesAFD> resultado = new HashSet<StatesAFD>();

        return resultado;
    }

    // Método para inicializar la construcción directa del AFD.
    public static void initialize(String regex, String cadena) {

        String valorRegex= regex;

        // Asignando memoria para el arreglo de followPos.
        DStates = new HashSet<>();
        input = new HashSet<String>();

        getSymbols(valorRegex);

        /*
         * asignando la expresión regular al constructor de la clase SyntaxTree y
         * creando el árbol de sintaxis de la expresión regular en él
         */
        SyntaxTree st = new SyntaxTree(regex);
        root = st.getRoot(); //root of the syntax tree
        followPos = st.getFollowPos(); //the followpos of the syntax tree
        /*
         * Creando el AFD usando el árbol de sintaxis que se creó arriba y
         * devolviendo el estado de inicio del AFD resultante
        */
        State q0 = createDFA();
        DfaTraversal dfat = new DfaTraversal(q0, input);
        //System.out.println("DFA:" + dfat.toString());
        boolean acc = false;
        for (char c : cadena.toCharArray()) {
            if (dfat.setCharacter(c)) {
                acc = dfat.traverse();
            } else {
                System.out.println("CARACTER DEFECTUOSO!");
                System.exit(0);
            }
        }
        if (acc) {
            System.out.println("La cadena ingresada es aceptada por el regex!");
        } else {
            System.out.println("La cadena ingresada no es aceptada por el regex!");
        }
    }

    // Método para obtener el simbolo (alfabeto) de la expresión regular.
    private static void getSymbols(String regex) {
         /*
          * op es un conjunto de caracteres que tienen un significado operativo, por ejemplo, '*'
          * podría ser un operador de cierre
          */
        Set<Character> op = new HashSet<>();
        Character[] ch = {'(', ')', '*', '|', '&', '.', '\\', '[', ']', '+'};
        op.addAll(Arrays.asList(ch));

        input = new HashSet<>();
        symbNum = new HashMap<>();
        int num = 1;
        for (int i = 0; i < regex.length(); i++) {
            char charAt = regex.charAt(i);
             /*
              * Si un carácter que también es un operador, está seguido de una barra invertida ('\'),
                * entonces debemos considerarlo como un carácter normal y no como un operador
              */
            if (op.contains(charAt)) {
                if (i - 1 >= 0 && regex.charAt(i - 1) == '\\') {
                    input.add("\\" + charAt);
                    symbNum.put(num++, "\\" + charAt);
                }
            } else {
                input.add("" + charAt);
                symbNum.put(num++, "" + charAt);
            }
        }
        System.out.println("Alfabeto: " + input);
    }

    // Método donde se crea el AFD una vez habiendo armado nuestro arbol
    private static State createDFA() {
        int id = 0;
        Set<Integer> firstpos_n0 = root.getFirstPos();
        AFDResult = new HashSet<String>();

        State q0 = new State(id++);
        q0.asignarTodoNombre(firstpos_n0);
        if (q0.getName().contains(followPos.length)) {
            q0.setAccept();
        }
        DStates.clear();
        DStates.add(q0);
        //System.out.println("Estado inicial: " + q0.getName());
        while (true) {
            boolean exit = true;
            State s = null;
            for (State state : DStates) {
                if (!state.getIsMarked()) {
                    exit = false;
                    s = state;
                }
            }
            if (exit) {
                break;
            }

            if (s.getIsMarked()) {
                continue;
            }
            s.setIsMarked(true); //Marcar el estado
            Set<Integer> name = s.getName();
            for (String a : input) {
                Set<Integer> U = new HashSet<>();
                for (int p : name) {
                    //System.out.println("p: " + p);
                    if (symbNum.get(p).equals(a)) {
                        //System.out.println("symbNum: " + symbNum.get(p));
                        U.addAll(followPos[p - 1]);
                        
                    }
                }
                //System.out.println("U: " + U);
                boolean flag = false;
                State tmp = null;
                for (State state : DStates) {
                    if (state.getName().equals(U)) {
                        tmp = state;
                        flag = true;
                        //System.out.println("Estado: " + state.getName());
                        break;
                    }
                    
                }

                
                
                if (!flag) {
                    State q = new State(id++);
                    q.asignarTodoNombre(U);
                    if (U.contains(followPos.length)) {
                        q.setAccept();
                    }
                    DStates.add(q);
                    tmp = q;
                    AFDResult.add(s.getName() + " -- " + a + " --> " + q.getName());
                }
                s.move(a, tmp);
                //System.out.println("Estados: " + tmp.getName().toString());
                //System.out.println("Transiciones: " + a.toString());
            //System.out.println("Estado: " + s.getName().toString());
            //AFDResult.add(s.getName().toString() + " - " + a.toString() + " -> " + tmp.getName().toString());
                
            }
        }
        
        //System.out.println("AFD: " + q0.getName().toString());

        return q0;
    }

    public String toStringRegexAFD(){
        return AFDResult.toString();
    }

    //Escritura del AFD a un archivo de texto

    public static void writeAFDDirecto() {
        try {
            // Creando el archivo con codificado UTF-8.
            File archivo = new File("AFDdirecto.txt");

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
            bw.write("Transiciones:" + AFDResult.toString());
            bw.newLine();

            // Escribiendo el alfabeto.
            bw.write("Alfabeto: " + input.toString());
            bw.newLine();

            // Cerrando el escritor.
            bw.close();

            // Escribiendo en el archivo.
        } catch (Exception e) {
            System.out.println("Error al escribir el archivo.");
        }
    }

}