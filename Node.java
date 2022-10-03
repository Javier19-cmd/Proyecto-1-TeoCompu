import java.util.HashSet;
import java.util.Set;


public class Node {

    private String simbolo;
    private Node origen;
    private Node izquierda;
    private Node derecha;

    private Set<Integer> firstPos;
    private Set<Integer> lastPos;
    private boolean nullable;

    public Node(String simbolo) {
        this.simbolo = simbolo;
        origen = null;
        izquierda = null;
        derecha = null;

        firstPos = new HashSet<>();
        lastPos = new HashSet<>();
        nullable = false;
    }

    /**
     * @return el simbolo
     */
    public String getSimbolo() {
        return simbolo;
    }

    /**
     * @param simbolo para setear
     */
    public void setSymbol(String simbolo) {
        this.simbolo = simbolo;
    }

    /**
     * @return el origen del nodo
     */
    public Node getOrigen() {
        return origen;
    }

    /**
     * @param origen para setear
     */
    public void setOrigen(Node parent) {
        this.origen = origen;
    }

    /**
     * @return la izquierda del nodo
     */
    public Node getIzquierda() {
        return izquierda;
    }

    /**
     * @param izquierda para setear
     */
    public void setLeft(Node izquierda) {
        this.izquierda = izquierda;
    }

    /**
     * @return la derecha del nodo
     */
    public Node getDerecha() {
        return derecha;
    }

    /**
     * @param derecha para setear
     */
    public void setRight(Node derecha) {
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
     * @return el firstPos
     */
    public Set<Integer> getFirstPos() {
        return firstPos;
    }

    /**
     * @return el lastPos
     */
    public Set<Integer> getLastPos() {
        return lastPos;
    }

    /**
     * @return the nullable
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * @param nullable the nullable to set
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
}
