import java.util.*;

class ArbolBinario {
    
    /*
        ***
            (a|b)*a => creando la sintaxis de la expresión regular para el arbol binario.
                                .
                               / \
                              *   a
                             /
                            |
                           / \
                          a   b
        ***
    */

    private int leafNodeID = 0;
    
    //Creamos dos pilas, una para los simbolos y otra para los operadores.
    private Stack<Nodo> stackNode = new Stack<>();
    private Stack<Character> operator = new Stack<Character>();

    // Set de caracteres que representan los operadores
    private Set<Character> input = new HashSet<Character>();
    private ArrayList<Character> op = new ArrayList<>();

    //Gerando el arbol binario con la expresion regular y retornando su raiz.
    public Nodo generateTree(String regular) {

        Character[] ops = {'*', '|', '&'};
        op.addAll(Arrays.asList(ops));

        //Solamente los simbolos disponibles del alfabeto
        Character ch[] = new Character[26 + 26];
        for (int i = 65; i <= 90; i++) {
            ch[i - 65] = (char) i;
            ch[i - 65 + 26] = (char) (i + 32);
        }
        Character integer[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        Character others[] = {'#','\\', '=', '_', '.', '*', '/', '+', '-', ' ', '(', ')'};
        input.addAll(Arrays.asList(ch));
        input.addAll(Arrays.asList(integer));
        input.addAll(Arrays.asList(others));


        //Generar la expresion regular con la concatenacion
        regular = AddConcat(regular);
        
        // Limpiando las pilas
        stackNode.clear();
        operator.clear();


        // Marca que es verdadero cuando hay algo como: \( o \* o etc
        boolean esSimbolo = false;

        for (int i = 0; i < regular.length(); i++) {

            if (regular.charAt(i) == '\\') {
                esSimbolo = true;
                continue;
            }
            if (esSimbolo || isInputCharacter(regular.charAt(i))) {
                if (esSimbolo) {
                    //Creando un nodo con el simbolo "\{simbolo}"
                    pushStack("\\"+Character.toString(regular.charAt(i)));
                }
                else{
                    pushStack(Character.toString(regular.charAt(i)));
                }
                esSimbolo = false;
            } else if (operator.isEmpty()) {
                operator.push(regular.charAt(i));

            } else if (regular.charAt(i) == '(') {
                operator.push(regular.charAt(i));

            } else if (regular.charAt(i) == ')') {
                while (operator.get(operator.size() - 1) != '(') {
                    realizarOperacion();
                }

                //Pop el '(' izquierdo parentesis
                operator.pop();

            } else {
                while (!operator.isEmpty()
                        && simbolosPrioritarios(regular.charAt(i), operator.get(operator.size() - 1))) {
                    realizarOperacion();
                }
                operator.push(regular.charAt(i));
            }
        }

        // Limpia los elementos restantes en la pila
        while (!operator.isEmpty()) {
            realizarOperacion();
        }

        // Obtiene el arbol completo
        Nodo completeTree = stackNode.pop();
        return completeTree;
    }

    private boolean simbolosPrioritarios(char primero, Character segundo) {
        if (primero == segundo) {
            return true;
        }
        if (primero == '*') {
            return false;
        }
        if (segundo == '*') {
            return true;
        }
        if (primero == '&') {
            return false;
        }
        if (segundo == '&') {
            return true;
        }
        if (primero == '|') {
            return false;
        }
        return true;
    }

    // Hace la operacion deseada basado en el tope de la pila
    private void realizarOperacion() {
        if (this.operator.size() > 0) {
            char charAt = operator.pop();

            switch (charAt) {
                case ('|'):
                    union();
                    break;

                case ('&'):
                    Concatenacion();
                    break;

                case ('*'):
                    Kleen();
                    break;

                default:
                    System.out.println(">>" + charAt);
                    System.out.println("Unkown Symbol !");
                    System.exit(1);
                    break;
            }
        }
    }

    // Hacer la operación de Kleen
    private void Kleen() {
        // Recuperar el nodo superior de la pila
        Nodo node = stackNode.pop();

        Nodo root = new Nodo("*");
        root.setIzquierda(node);
        root.setDerecha(null);
        node.setOrigen(root);

        // Poner el nodo de nuevo en la pila
        stackNode.push(root);
    }

    // Hacer la operación de concatenación
    private void Concatenacion() {
        // Recuperar el nodo 1 y 2 de la pila
        Nodo node2 = stackNode.pop();
        Nodo node1 = stackNode.pop();

        Nodo root = new Nodo("&");
        root.setDerecha(node1);
        root.setDerecha(node2);
        node1.setOrigen(root);
        node2.setOrigen(root);

        // Poner el nodo de nuevo en la pila
        stackNode.push(root);
    }

    // Hacer union de sub nodo 1 con sub nodo 2
    private void union() {
        // Cargar dos nodos en la pila en variables
        Nodo node2 = stackNode.pop();
        Nodo node1 = stackNode.pop();

        Nodo root = new Nodo("|");
        root.setIzquierda(node1);
        root.setDerecha(node2);
        node1.setOrigen(root);
        node2.setOrigen(root);

        // Poner el nodo de nuevo en la pila
        stackNode.push(root);
    }

    // Pushear el simbolo de entrada en la pila
    private void pushStack(String symbol) {
        Nodo node = new hojaNodo(symbol, ++leafNodeID);
        node.setIzquierda(null);
        node.setDerecha(null);

        // POner el nodo de nuevo en la pila
        stackNode.push(node);
    }


    // Añade "." cuando es concatenacion entre dos simbolos que: "." -> "&"
    // se concatenan entre si
    private String AddConcat(String regular) {
        String newRegular = new String("");

        for (int i = 0; i < regular.length() - 1; i++) {
             /*
                * Considerando que a y b son caracteres en Σ
                * y el conjunto: {'(', ')', '*', '+', '&', '|'} son los operadores
                * entonces, si '&' es el símbolo de concatenación, tenemos que concatenar tales expresiones:
                * a & b
                * a & (
                * ) & a
                * * & a
                * * & (
                * ) & (
              */

            if (regular.charAt(i) == '\\' && isInputCharacter(regular.charAt(i + 1))) {
                newRegular += regular.charAt(i);
            } else if (regular.charAt(i) == '\\' && regular.charAt(i + 1) == '(') {
                newRegular += regular.charAt(i);
            } else if ((isInputCharacter(regular.charAt(i)) || (regular.charAt(i) == '(' && i > 0 && regular.charAt(i - 1) == '\\')) && isInputCharacter(regular.charAt(i + 1))) {
                newRegular += regular.charAt(i) + "&";

            } else if ((isInputCharacter(regular.charAt(i)) || (regular.charAt(i) == '(' && i > 0 && regular.charAt(i - 1) == '\\')) && regular.charAt(i + 1) == '(') {
                newRegular += regular.charAt(i) + "&";

            } else if (regular.charAt(i) == ')' && isInputCharacter(regular.charAt(i + 1))) {
                newRegular += regular.charAt(i) + "&";

            } else if (regular.charAt(i) == '*' && isInputCharacter(regular.charAt(i + 1))) {
                newRegular += regular.charAt(i) + "&";

            } else if (regular.charAt(i) == '*' && regular.charAt(i + 1) == '(') {
                newRegular += regular.charAt(i) + "&";

            } else if (regular.charAt(i) == ')' && regular.charAt(i + 1) == '(') {
                newRegular += regular.charAt(i) + "&";

            } else {
                newRegular += regular.charAt(i);
            }

        }
        newRegular += regular.charAt(regular.length() - 1);
        return newRegular;
    }

    // Retornar verdadero si es parte del lenguaje del autómata, de lo contrario es falso
    private boolean isInputCharacter(char charAt) {

        if (op.contains(charAt)) {
            return false;
        }
        for (Character c : input) {
            if ((char) c == charAt && charAt != '(' && charAt != ')') {
                return true;
            }
        }
        return false;
    }
    

    // Método para probar el método buildTree()
    public void printInorder(Nodo node) {
        if (node == null) {
            return;
        }

        // Primer recorrido en el hijo izquierdo
        printInorder(node.getIzquierda());

        // Luego imprimir el dato del nodo
        System.out.print(node.obtenerSimbolo() + " ");


        // Ahora recorrer el hijo derecho
        printInorder(node.getDerecha());
    }
    
    public int getNumberOfLeafs(){
        return leafNodeID;
    }

}