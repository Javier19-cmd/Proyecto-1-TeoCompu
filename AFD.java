/*
 * Clase de construcción para lo de subconjuntos AFD
 */

 public class AFD{
    StatesAFD de,a;
    String simbolo;


    public AFD(StatesAFD de, StatesAFD a, String simbolo){
        this.a=a;
        this.de=de;
        this.simbolo=simbolo;

        this.de.agregarEstadoSiguiente(a);
        this.a.agregarEstadoAnterior(de);
    } // Juntando estados del AFD.
 }