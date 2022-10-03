import java.util.HashSet;
import java.util.Set;

public class LeafNode extends Node {

    private int num;
    private Set<Integer> followPos;

    public LeafNode(String symbol, int num) {
        super(symbol);
        this.num = num;
        followPos = new HashSet<>();
    }

    /**
     * @return el número del nodo
     */
    public int getNum() {
        return num;
    }

    /**
     * @param num el número del nodo para setear
     */
    public void setNum(int num) {
        this.num = num;
    }
     /*
      * Se calcula el followPos de un nodo hoja
      */
    public void addToFollowPos(int number){
        followPos.add(number);
    }

    /**
     * @return se retorna un set con los followPos
     */
    public Set<Integer> getFollowPos() {
        return followPos;
    }

    /**
     * @param followPos los followPos para setear
     */
    public void setFollowPos(Set<Integer> followPos) {
        this.followPos = followPos;
    }
}
