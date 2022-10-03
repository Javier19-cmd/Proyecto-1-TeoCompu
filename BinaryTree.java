import java.util.*;

class BinaryTree {
    
    /*
        ***
            (a|b)*a => creando el arbol sintactico binario: 
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
    
    // Stacks para los nodos de simbolos y operadores
    private Stack<Node> stackNode = new Stack<>();
    private Stack<Character> operator = new Stack<Character>();

    //Set de entradas
    private Set<Character> input = new HashSet<Character>();
    private ArrayList<Character> op = new ArrayList<>();

    // Genera el arbol sintactico binario usando la expresion regular y retorna la raiz del arbol
    public Node generateTree(String regular) {

        Character[] ops = {'*', '|', '&'};
        op.addAll(Arrays.asList(ops));

        // Solamente se permiten los siguientes simbolos
        // a-z, A-Z, 0-9, *, |, &, (, ), #
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

        // Se agrega el simbolo de concatenacion
        // Generar la expresion regular con la concatenacion
        regular = AddConcat(regular);
        
        // Se limpian los stacks
        stackNode.clear();
        operator.clear();

        // Se recorre la expresion regular
        // Marcar cuando hay algo como: \( o \* o etc
        boolean isSymbol = false;

        for (int i = 0; i < regular.length(); i++) {

            if (regular.charAt(i) == '\\') {
                isSymbol = true;
                continue;
            }
            if (isSymbol || isInputCharacter(regular.charAt(i))) {
                if (isSymbol) {
                    // Crear un nodo con el simbolo "\{simbolo}" 
                    pushStack("\\"+Character.toString(regular.charAt(i)));
                }
                else{
                    pushStack(Character.toString(regular.charAt(i)));
                }
                isSymbol = false;
            } else if (operator.isEmpty()) {
                operator.push(regular.charAt(i));

            } else if (regular.charAt(i) == '(') {
                operator.push(regular.charAt(i));

            } else if (regular.charAt(i) == ')') {
                while (operator.get(operator.size() - 1) != '(') {
                    doOperation();
                }

                // Se popea el '(' izquierdo de la pila de operadores
                operator.pop();

            } else {
                while (!operator.isEmpty()
                        && Priority(regular.charAt(i), operator.get(operator.size() - 1))) {
                    doOperation();
                }
                operator.push(regular.charAt(i));
            }
        }

        // Se limpian los elementos restantes en la pila
        while (!operator.isEmpty()) {
            doOperation();
        }

        // Se retornar el arbol completo
        Node completeTree = stackNode.pop();
        return completeTree;
    }

    private boolean Priority(char first, Character second) {
        if (first == second) {
            return true;
        }
        if (first == '*') {
            return false;
        }
        if (second == '*') {
            return true;
        }
        if (first == '&') {
            return false;
        }
        if (second == '&') {
            return true;
        }
        if (first == '|') {
            return false;
        }
        return true;
    }

    // Hacer el operador deseado basado en el tope de la pila
    private void doOperation() {
        if (this.operator.size() > 0) {
            char charAt = operator.pop();

            switch (charAt) {
                case ('|'):
                    union();
                    break;

                case ('&'):
                    concatenation();
                    break;

                case ('*'):
                    Kleene();
                    break;

                default:
                    System.out.println(">>" + charAt);
                    System.out.println("No se reconoce el simbolo!");
                    System.exit(1);
                    break;
            }
        }
    }

    // Se hace la operacion de cerradura de Kleene
    private void Kleene() {
        // Recuperar el nodo superior de la pila
        Node node = stackNode.pop();

        Node root = new Node("*");
        root.setLeft(node);
        root.setRight(null);
        node.setOrigen(root);

        // Poner el nodo de nuevo en la pila
        stackNode.push(root);
    }

    // Se hace la operacion de concatenacion
    private void concatenation() {
        // Recuperar el nodo 1 y 2 de la pila
        Node node2 = stackNode.pop();
        Node node1 = stackNode.pop();

        Node root = new Node("&");
        root.setLeft(node1);
        root.setRight(node2);
        node1.setOrigen(root);
        node2.setOrigen(root);

        // Put node back to stackNode
        // Poner el nodo de nuevo en la pila
        stackNode.push(root);
    }

    // Se hace la operacion de union
    // Hacer la union de los sub nodos 1 y con el sub nodo 2
    private void union() {
        // Cargar dos nodos en la pila en variables
        Node node2 = stackNode.pop();
        Node node1 = stackNode.pop();

        Node root = new Node("|");
        root.setLeft(node1);
        root.setRight(node2);
        node1.setOrigen(root);
        node2.setOrigen(root);

        // Poner el nodo de nuevo en la pila
        stackNode.push(root);
    }

    // Poner el simbolo de entrada en la pila
    private void pushStack(String symbol) {
        Node node = new LeafNode(symbol, ++leafNodeID);
        node.setLeft(null);
        node.setRight(null);

        // Poner el nodo de nuevo en la pila
        stackNode.push(node);
    }

    // Agregar "." cuando es concatenacion entre dos simbolos que: "." -> "&"
    // se concatenan entre si
    private String AddConcat(String regular) {
        String newRegular = new String("");

        for (int i = 0; i < regular.length() - 1; i++) {
             /* 
              * Considerar que a y b son caracteres en Σ
                * y el conjunto: {'(', ')', '*', '+', '&', '|'} son los operadores
                * entonces, si '&' es el simbolo de concatenacion, tenemos que concatenar las siguientes expresiones:
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

    // Retornar true si es parte del lenguaje del automata, de lo contrario es false
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
    
    // Este metodo es solo para probar buildTree()
    public void printInorder(Node node) {
        if (node == null) {
            return;
        }


        // Primero se recorre el hijo izquierdo
        printInorder(node.getIzquierda());

        // Después se imprime el nodo
        System.out.print(node.getSimbolo() + " ");

        // Por ultimo se recorre el hijo derecho
        printInorder(node.getDerecha());
    }
    
    public int getNumberOfLeafs(){
        return leafNodeID;
    }

}
