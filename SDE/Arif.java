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


// lock
class LockingTree {

    public static boolean lock(Node node) {
        // Condition 1: node already locked OR has locked descendants
        if (node.isLocked() || node.lockedChildCount > 0) {
            return false;
        }

        // Condition 2: no locked ancestor
        Node curr = node.getParent();
        while (curr != null) {
            if (curr.isLocked()) {
                return false;
            }
            curr = curr.getParent();
        }

        // Lock is allowed → update ancestors
        curr = node.getParent();
        while (curr != null) {
            curr.lockedChildCount++;
            curr = curr.getParent();
        }

        node.lockNode();
        return true;
    }



    // unlock

    public static boolean unlock(Node node) {
        if (!node.isLocked()) {
            return false;
        }

        // Update ancestors
        Node curr = node.getParent();
        while (curr != null) {
            curr.lockedChildCount--;
            curr = curr.getParent();
        }

        node.unlockNode();
        return true;
    }
}
