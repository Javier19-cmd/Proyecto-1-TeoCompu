import java.util.ArrayList;

public class Transiciones {

    // Variables globales de la clase Transiciones.
    int de, a; // de es el estado de inicio y a es el estado de fin.
    String simbolo; // simbolo de la transicion.

    public String Transicion(int de, int a, String c) {
        this.de = de;
        this.a = a;
        this.simbolo = c;

        // System.out.println("de: " + de + " a: " + a + " simbolo: " + c);

        return "(" + this.de + "," + this.simbolo + "," + this.a + ")";

    } // Constructor de la clase Transicion. Esto servirá para almacenar las
      // transiciones de los estados.
}
