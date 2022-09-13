import java.util.List;
import java.util.Scanner;

public class Lector {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        regex postfix = new regex(); // Instanciando la clase regex para pasar a postfix la expresión regular.
        Thompson thompson = new Thompson(); // Instanciando la clase Thompson para crear el AFN.
        AFDs afde = new AFDs(); // Instanciando la clase AFDe para crear el AFD.
        AFDConverter afdConverter = new AFDConverter(); // Instanciando la clase AFDConverter para convertir el AFD a un
                                                        // AFD equivalente.

        String r = "";

        System.out.println("Introduzca la expresión regular: ");
        r = teclado.nextLine(); // lee la expresión regular
        String post_value = postfix.evaluar(r); // pasar a postfix la expresión regular
        System.out.println("Valor postfix: " + post_value);

        thompson.post(post_value); // Mando a evaluar la expresión regular.

        thompson.escribirArchivo(); // Escribir el archivo de salida.

        // Haciendo el getter de la lista de transiciones.
        List<Transiciones> tr = thompson.getTransiciones();

        System.out.println("Transiciones: " + tr);

        Estado inicial = thompson.getEstadoInicial(); // Obteniendo el estado inicial.

        System.out.println("Estado inicial: " + inicial);

        // Creando el AFD.
        afdConverter.Proceso(tr, inicial);

    }
}