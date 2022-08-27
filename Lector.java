import java.util.Scanner;

public class Lector {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        regex postfix = new regex();
        Thompson thompson = new Thompson();

        String r = "";

        System.out.println("Introduzca la expresión regular: ");
        r = teclado.nextLine(); // lee la expresión regular
        String post_value = postfix.evaluar(r);
        // System.out.println("Valor postfix: " + post_value);

        thompson.post(post_value);
    }
}