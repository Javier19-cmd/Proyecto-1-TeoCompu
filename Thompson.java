import java.util.*;

public class Thompson {
    static String post(String postfix) {

        // System.out.println("Valor postfix: " + postfix);
        // Defininiendo arraylist para el alfabeto.
        ArrayList<String> expresion = new ArrayList<String>();

        // Defininendo una pila para el alfabeto.
        Stack<String> pila = new Stack<String>();

        // Definiendo una pila para las operaciones de la expresión.
        Stack<String> operaciones = new Stack<String>();

        // Definiendo una pila para los estados de la expresión.
        ArrayList<Integer> estados = new ArrayList<Integer>();

        // Instanciando la clase de transiciones.
        Transiciones transicion = new Transiciones();

        // Definiendo los estados de las transiciones. Esto es una clase aparte.
        ArrayList<Transiciones> transiciones = new ArrayList<Transiciones>();

        for (int i = 0; i < postfix.length(); i++) {
            // Pusheando los caracteres de la expresion regular en el arraylist.
            expresion.add(String.valueOf(postfix.charAt(i)));

            /*
             * Si el caracter es diferente de | o * o +, se agrega a la pila.
             */
            switch (postfix.charAt(i)) {
                case '|':
                    operaciones.push(String.valueOf(postfix.charAt(i)));
                    break;
                case '*':
                    operaciones.push(String.valueOf(postfix.charAt(i)));
                    break;
                case '+':
                    operaciones.push(String.valueOf(postfix.charAt(i)));
                    break;
                default:
                    pila.push(String.valueOf(postfix.charAt(i)));
                    break;
            }

        }

        System.out.println("Expresion regular: " + expresion);
        System.out.println("Pila: " + pila);
        System.out.println("Operaciones: " + operaciones);

        return postfix;
    }
}
