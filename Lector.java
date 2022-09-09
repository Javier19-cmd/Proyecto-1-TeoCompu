import java.util.Scanner;

public class Lector {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        regex postfix = new regex(); // Instanciando la clase regex para pasar a postfix la expresión regular.
        Thompson thompson = new Thompson(); // Instanciando la clase Thompson para crear el AFN.
        AFDs afde = new AFDs(); // Instanciando la clase AFDe para crear el AFD.

        String r = "";

        System.out.println("Introduzca la expresión regular: ");
        r = teclado.nextLine(); // lee la expresión regular
        String post_value = postfix.evaluar(r); // pasar a postfix la expresión regular
        System.out.println("Valor postfix: " + post_value);

        thompson.post(post_value); // Mando a evaluar la expresión regular.

        // String s = "";
        // System.out.println("Introduzca la cadena para construir el AFD: ");
        // s = teclado.nextLine(); // lee la cadena para construir el AFD.
        // String post_value2 = postfix.evaluar(s); // pasar a postfix la expresión
        // regular

        // // Instanciando la clase AFDs para crear el AFD.
        // afde.AFDe(post_value2);

    }
}