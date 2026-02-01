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

public class Arif {

    public static void main(String[] args) {

        Node A = new Node(1, null);
        Node B = new Node(2, A);
        Node C = new Node(3, A);
        Node D = new Node(4, B);
        Node E = new Node(5, B);

        System.out.println(ConcurrentLockingTree.lock(D));
        System.out.println(ConcurrentLockingTree.lock(B));
        System.out.println(ConcurrentLockingTree.lock(A));
        System.out.println(ConcurrentLockingTree.lock(C));
        System.out.println(ConcurrentLockingTree.unlock(D));
        System.out.println(ConcurrentLockingTree.lock(B));
        System.out.println(ConcurrentLockingTree.lock(A));
    }
}
