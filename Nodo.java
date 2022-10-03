import java.util.HashSet;
import java.util.Set;

/* 
 * Clase que representa un nodo de un arbol de expresiones regulares.
*/

public class Nodo {

    private String simbolo;
    private Nodo origen;
    private Nodo izquierda;
    private Nodo derecha;

    private Set<Integer> firstPos;
    private Set<Integer> lastPos;
    private boolean nullable;

    public Nodo(String simbolo) {
        this.simbolo = simbolo;
        origen = null;
        izquierda = null;
        derecha = null;

        firstPos = new HashSet<>();
        lastPos = new HashSet<>();
        nullable = false;
    }

    /**
     * @return el simbolo del nodo
	 */
    public String obtenerSimbolo() {
        return simbolo;
    }

    /**
     * @param simbolo setea el simbolo del nodo
     */
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    /**
     * @return el orgien del nodo
     */
    public Nodo getOrigen() {
        return origen;
    }

    /**
     * @param origen setea el padre del nodo
     */
    public void setOrigen(Nodo origen) {
        this.origen = origen;
    }

    /**
     * @return  obtiene la rama izquierda del nodo
     */
    public Nodo getIzquierda() {
        return izquierda;
    }

    /**
     * @param izquierda setea la rama izquierda del nodo
     */
    public void setIzquierda(Nodo izquierda) {
        this.izquierda = izquierda;
    }

    /**
     * @return obtiene la rama derecha del nodo
     */
    public Nodo getDerecha() {
        return derecha;
    }

    /**
     * @param derecha setea la rama derecha del nodo
     */
    public void setDerecha(Nodo derecha) {
        this.derecha = derecha;
    }

    public void addToFirstPos(int number) {
        firstPos.add(number);
    }
    public void addAllToFirstPos(Set set) {
        firstPos.addAll(set);
    }

    public void addToLastPos(int number) {
        lastPos.add(number);
    }
    public void addAllToLastPos(Set set) {
        lastPos.addAll(set);
    }

    /**
     * @return firstPos
     */
    public Set<Integer> getFirstPos() {
        return firstPos;
    }

    /**
     * @return lastPos
     */
    public Set<Integer> getLastPos() {
        return lastPos;
    }

    /**
     * @return nullable
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * @param nullable el nullable a setear
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
}

