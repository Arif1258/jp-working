import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

class Node {
    int value;
    Node parent;
    List<Node> children = new ArrayList<>();

    boolean locked = false;
    int lockedChildCount = 0;

    ReentrantLock mutex = new ReentrantLock();

    Node(int value, Node parent) {
        this.value = value;
        this.parent = parent;
        if (parent != null) {
            parent.children.add(this);
        }
    }
}


//lock
class ConcurrentLockingTree {

    public static boolean lock(Node node) {
        List<Node> path = new ArrayList<>();


        Node curr = node;
        while (curr != null) {
            path.add(curr);
            curr = curr.parent;
        }


        Collections.reverse(path);
        for (Node n : path) {
            n.mutex.lock();
        }

        try {

            if (node.locked || node.lockedChildCount > 0) {
                return false;
            }

            for (int i = 0; i < path.size() - 1; i++) {
                if (path.get(i).locked) {
                    return false;
                }
            }


            node.locked = true;
            for (int i = 0; i < path.size() - 1; i++) {
                path.get(i).lockedChildCount++;
            }

            return true;
        } finally {

            for (int i = path.size() - 1; i >= 0; i--) {
                path.get(i).mutex.unlock();
            }
        }
    }


// unlock
    public static boolean unlock(Node node) {
        List<Node> path = new ArrayList<>();
        Node curr = node;

        while (curr != null) {
            path.add(curr);
            curr = curr.parent;
        }

        Collections.reverse(path);
        for (Node n : path) {
            n.mutex.lock();
        }

        try {
            if (!node.locked) {
                return false;
            }

            node.locked = false;
            for (int i = 0; i < path.size() - 1; i++) {
                path.get(i).lockedChildCount--;
            }

            return true;
        } finally {
            for (int i = path.size() - 1; i >= 0; i--) {
                path.get(i).mutex.unlock();
            }
        }
    }
}
