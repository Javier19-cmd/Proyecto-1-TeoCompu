import java.util.Stack;

public class regex {
    static String evaluar(String infix) {
        String postfix = "";
        Stack<String> pila = new Stack<String>();
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            if (c == '(') {
                pila.push(String.valueOf(c));
            } else if (c == ')') {
                while (!pila.isEmpty() && pila.peek().charAt(0) != '(') {
                    postfix += pila.pop();
                }
                pila.pop();
            } else if (c == '|') {
                while (!pila.isEmpty() && pila.peek().charAt(0) != '(') {
                    postfix += pila.pop();
                }
                pila.push(String.valueOf(c));
            } else {
                postfix += c;
            }
        }
        while (!pila.isEmpty()) {
            postfix += pila.pop();
        }

        System.out.println("Expresión regular postfix: " + postfix);
        return postfix;
    }
}
