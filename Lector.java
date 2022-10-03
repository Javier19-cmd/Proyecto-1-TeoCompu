import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class Lector {

    private static Set<Integer>[] followPos;
    private static Nodo root;
    private static Set<regextoafdStates> DStates;

    private static Set<String> input; //set of characters is used in input regex
     /*
      * Un número se asigna a cada carácter (incluso los duplicados)
      * 
        * @param symbNum es un mapa hash tiene una clave que menciona el número y tiene
        * un valor que menciona el carácter correspondiente o a veces una cadena
        * para los caracteres se siguen con barra invertida como "\ *"
      */
    private static HashMap<Integer, String> symbNum;

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        regex postfix = new regex(); // Instanciando la clase regex para pasar a postfix la expresión regular.
        //Thompson thompson = new Thompson(); // Instanciando la clase Thompson para crear el AFN.
        AFDConverter afdConverter = new AFDConverter(); // Instanciando la clase AFDConverter para convertir el AFD a un
                                                        // AFD equivalente.

        String r = "";

        //System.out.println("Introduzca la expresión regular: ");
        //r = teclado.nextLine(); // lee la expresión regular
        //String post_value = postfix.evaluar(r); // pasar a postfix la expresión regular
        // System.out.println("Valor postfix: " + post_value);

        //thompson.post(post_value); // Mando a evaluar la expresión regular.

        //thompson.escribirArchivo(); // Escribir el archivo de salida.

        // Haciendo el getter de la lista de transiciones.
        //List<Transiciones> tr = thompson.getTransiciones();

        // System.out.println("Transiciones: " + tr);

        //Estado inicial = thompson.getEstadoInicial(); // Obteniendo el estado inicial.

        // System.out.println("Estado inicial: " + inicial);

        // Haciendo el getter de la lista de estados iniciales.
        //List<Estado> estadosIniciales = thompson.getEstados_iniciales();

        // Haciendo el getter de la lista de estados finales.
        //List<Estado> estadosFinales = thompson.getEstados_aceptacion();

        // System.out.println("Estados inicial: " + estadosIniciales);

        // System.out.println("Estados finales: " + estadosFinales);

        // Haciendo getter de los símbolos del alfabeto.
        //ArrayList<String> alfabeto = thompson.getAlfabeto();

        // Haciendo getter de los estados.
        //Stack<Estado> estados = thompson.getEstadoss();

        // Haciendo el getter del estado de aceptación.
        //Estado aceptacion = thompson.getEstadoAceptacion();

        // System.out.println("Estado de aceptación: " + aceptacion);

        // Simulando el AFN construído.
        //thompson.simulation(aceptacion);

        // Creando el AFD.
        //afdConverter.Proceso(tr, inicial, estadosFinales, alfabeto, estados,  aceptacion);

        // Escribiendo el archivo del AFD.
        //AFDConverter.EscribirArchivo();

        // Minimizando el AFD
        //AFDConverter.MinimizacionAFD();

        //AFDConverter.Simulacion(); // Simulando el AFD.

        // Escribiendo el archivo del AFD minimizado.
        //AFDConverter.writeAFDMinimizado();

        //AFDConverter.SimulacionMinimizado(); // Simulando el AFD.
        
        // Construcción directa regex afd 
        initialize();

    }

    public static void initialize() {
        Scanner in = new Scanner(System.in);
        //allocating
        DStates = new HashSet<>();
        input = new HashSet<String>();

        String regex = getRegex(in);
        getSymbols(regex);

        /**
         * giving the regex to SyntaxTree class constructor and creating the
         * syntax tree of the regular expression in it
         */
        SyntaxTree st = new SyntaxTree(regex);
        root = st.getRoot(); //root of the syntax tree
        followPos = st.getFollowPos(); //the followpos of the syntax tree

        /**
         * creating the DFA using the syntax tree were created upside and
         * returning the start state of the resulted DFA
         */
        regextoafdStates q0 = createDFA();
        RecorridoRegextoAFD dfat = new RecorridoRegextoAFD(q0, input);
        //System.out.println("DFA:" + tmp.toString());
        String str = getStr(in);
        boolean acc = false;
        for (char c : str.toCharArray()) {
            if (dfat.setCharacter(c)) {
                acc = dfat.traverse();
            } else {
                System.out.println("WRONG CHARACTER!");
                System.exit(0);
            }
        }
        if (acc) {
            System.out.println((char) 27 + "[32m" + "this string is acceptable by the regex!");
        } else {
            System.out.println((char) 27 + "[31m" + "this string is not acceptable by the regex!");
        }
        in.close();
    }

    private static String getRegex(Scanner in) {
        System.out.print("Enter a regex: ");
        String regex = in.nextLine();
        return regex+"#";
    }

    private static void getSymbols(String regex) {
        /**
         * op is a set of characters have operational meaning for example '*'
         * could be a closure operator
         */
        Set<Character> op = new HashSet<>();
        Character[] ch = {'(', ')', '*', '|', '&', '.', '\\', '[', ']', '+'};
        op.addAll(Arrays.asList(ch));

        input = new HashSet<>();
        symbNum = new HashMap<>();
        int num = 1;
        for (int i = 0; i < regex.length(); i++) {
            char charAt = regex.charAt(i);

            /**
             * if a character which is also an operator, is followed up by
             * backslash ('\'), then we should consider it as a normal character
             * and not an operator
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
    }

    private static regextoafdStates createDFA() {
        int id = 0;
        Set<Integer> firstpos_n0 = root.getFirstPos();

        regextoafdStates q0 = new regextoafdStates(id++);
        q0.addAllToName(firstpos_n0);
        if (q0.getName().contains(followPos.length)) {
            q0.setAccept();
        }
        DStates.clear();
        DStates.add(q0);

        while (true) {
            boolean exit = true;
            regextoafdStates s = null;
            for (regextoafdStates state : DStates) {
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
            s.setIsMarked(true); //mark the state
            Set<Integer> name = s.getName();
            for (String a : input) {
                Set<Integer> U = new HashSet<>();
                for (int p : name) {
                    if (symbNum.get(p).equals(a)) {
                        U.addAll(followPos[p - 1]);
                    }
                }
                boolean flag = false;
                regextoafdStates tmp = null;
                for (regextoafdStates state : DStates) {
                    if (state.getName().equals(U)) {
                        tmp = state;
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    regextoafdStates q = new regextoafdStates(id++);
                    q.addAllToName(U);
                    if (U.contains(followPos.length)) {
                        q.setAccept();
                    }
                    DStates.add(q);
                    tmp = q;
                }
                s.addMove(a, tmp);
            }
        }
        
        return q0;
    }

    private static String getStr(Scanner in) {
        System.out.print("Enter a string: ");
        String str;
        str = in.nextLine();
        return str;
    }
}