

import java.util.HashSet;
import java.util.Set;

public class SyntaxTree {

    private String regex;
    private ArbolBinario bt;
    private Nodo root; //the head of raw syntax tree
    private int numOfLeafs;
    private Set<Integer> followPos[];

    public SyntaxTree(String regex) {
        this.regex = regex;
        bt = new ArbolBinario();
        
        /**
         * generando el arbol binario de la expresion regular
         */
        root = bt.generateTree(regex);
        numOfLeafs = bt.getNumberOfLeafs();
        followPos = new Set[numOfLeafs];
        for (int i = 0; i < numOfLeafs; i++) {
            followPos[i] = new HashSet<>();
        }
        //bt.printInorder(root);
        generateNullable(root);
        generateFirstposLastPos(root);
        generateFollowPos(root);
    }

    private void generateNullable(Nodo node) {
        if (node == null) {
            return;
        }
        if (!(node instanceof hojaNodo)) {
            Nodo left = node.getIzquierda();
            Nodo right = node.getDerecha();
            generateNullable(left);
            generateNullable(right);
            switch (node.obtenerSimbolo()) {
                case "|":
                    node.setNullable(left.isNullable() | right.isNullable());
                    break;
                case "&":
                    node.setNullable(left.isNullable() & right.isNullable());
                    break;
                case "*":
                    node.setNullable(true);
                    break;
            }
        }
    }

    private void generateFirstposLastPos(Nodo node) {
        if (node == null) {
            return;
        }
        if (node instanceof hojaNodo) {
            hojaNodo lnode = (hojaNodo) node;
            node.addToFirstPos(lnode.getNum());
            node.addToLastPos(lnode.getNum());
        } else {
            Nodo left = node.getIzquierda();
            Nodo right = node.getDerecha();
            generateFirstposLastPos(left);
            generateFirstposLastPos(right);
            switch (node.obtenerSimbolo()) {
                case "|":
                    node.addAllToFirstPos(left.getFirstPos());
                    node.addAllToFirstPos(right.getFirstPos());
                    node.addAllToLastPos(left.getLastPos());
                    node.addAllToLastPos(right.getLastPos());
                    break;
                case "&":
                    if (left.isNullable()) {
                        node.addAllToFirstPos(left.getFirstPos());
                        node.addAllToFirstPos(right.getFirstPos());
                    } else {
                        node.addAllToFirstPos(left.getFirstPos());
                    }
                    //
                    if (right.isNullable()) {
                        node.addAllToLastPos(left.getLastPos());
                        node.addAllToLastPos(right.getLastPos());
                    } else {
                        node.addAllToLastPos(right.getLastPos());
                    }
                    break;
                case "*":
                    node.addAllToFirstPos(left.getFirstPos());
                    node.addAllToLastPos(left.getLastPos());
                    break;
            }
        }
    }

    private void generateFollowPos(Nodo node) {
        if (node == null) {
            return;
        }
        Nodo left = node.getIzquierda();
        Nodo right = node.getDerecha();
        switch (node.obtenerSimbolo()) {
            case "&":
                Object lastpos_c1[] = left.getLastPos().toArray();
                Set<Integer> firstpos_c2 = right.getFirstPos();
                for (int i = 0; i < lastpos_c1.length; i++) {
                    followPos[(Integer) lastpos_c1[i] - 1].addAll(firstpos_c2);
                }
                break;
            case "*":
                Object lastpos_n[] = node.getLastPos().toArray();
                Set<Integer> firstpos_n = node.getFirstPos();
                for (int i = 0; i < lastpos_n.length; i++) {
                    followPos[(Integer) lastpos_n[i] - 1].addAll(firstpos_n);
                }
                break;
        }
        generateFollowPos(node.getIzquierda());
        generateFollowPos(node.getDerecha());

    }

    public void show(Nodo node) {
        if (node == null) {
            return;
        }
        show(node.getIzquierda());
        Object s[] = node.getLastPos().toArray();

        show(node.getDerecha());
    }

    public void showFollowPos() {
        for (int i = 0; i < followPos.length; i++) {
            Object s[] = followPos[i].toArray();
        }
    }

    public Set<Integer>[] getFollowPos() {
        return followPos;
    }

    public Nodo getRoot() {
        return this.root;
    }
}
