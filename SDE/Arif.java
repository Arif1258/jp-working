import java.util.ArrayList;
import java.util.List;

class Node {
    int value;
    Node parent;
    List<Node> children;
    boolean locked;
    int lockedChildCount;

    Node(int value, Node parent) {
        this.value = value;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.locked = false;
        this.lockedChildCount = 0;

        if (parent != null) {
            parent.addChild(this);
        }
    }

    void addChild(Node child) {
        children.add(child);
    }

    Node getParent() {
        return parent;
    }

    boolean isLocked() {
        return locked;
    }

    void lockNode() {
        locked = true;
    }

    void unlockNode() {
        locked = false;
    }
}

class LockingTree {

    public static boolean lock(Node node) {
        if (node.isLocked() || node.lockedChildCount > 0) {
            return false;
        }

        Node curr = node.getParent();
        while (curr != null) {
            if (curr.isLocked()) {
                return false;
            }
            curr = curr.getParent();
        }

        curr = node.getParent();
        while (curr != null) {
            curr.lockedChildCount++;
            curr = curr.getParent();
        }

        node.lockNode();
        return true;
    }

    public static boolean unlock(Node node) {
        if (!node.isLocked()) {
            return false;
        }

        Node curr = node.getParent();
        while (curr != null) {
            curr.lockedChildCount--;
            curr = curr.getParent();
        }

        node.unlockNode();
        return true;
    }
}

public class Arif {

    public static void main(String[] args) {

        Node A = new Node(1, null);
        Node B = new Node(2, A);
        Node C = new Node(3, A);
        Node D = new Node(4, B);
        Node E = new Node(5, B);

        /*
                A
               / \
              B   C
             / \
            D   E
        */

        System.out.println(LockingTree.lock(D));
        System.out.println(LockingTree.lock(B)); 
        System.out.println(LockingTree.lock(A)); 
        System.out.println(LockingTree.lock(C));
        System.out.println(LockingTree.unlock(D));
        System.out.println(LockingTree.lock(B));
        System.out.println(LockingTree.lock(A));
    }
}