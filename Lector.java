import java.util.Scanner;

public class Lector {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        regex postfix = new regex(); // Instanciando la clase regex para pasar a postfix la expresión regular.
        Thompson thompson = new Thompson(); // Instanciando la clase Thompson para crear el AFN.

        String r = "";

        System.out.println("Introduzca la expresión regular: ");
        r = teclado.nextLine(); // lee la expresión regular
        String post_value = postfix.evaluar(r); // pasar a postfix la expresión regular
        // System.out.println("Valor postfix: " + post_value);

        thompson.post(post_value); // Mando a evaluar la expresión regular.
    }
}