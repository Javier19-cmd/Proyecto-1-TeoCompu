public class Transiciones {

    int de, a; // de es el estado de inicio y a es el estado de fin.
    char simbolo; // simbolo de la transicion.

    public void Transicion(int de, int a, char c) {
        this.de = de;
        this.a = a;
        this.simbolo = c;
    } // Constructor de la clase Transicion. Esto servirá para almacenar las
      // transiciones de los estados.
}
