public class Node {

    String IP;
    Node LeftChild;
    Node RightChild;
    int height;

    Node(String key) {
        IP = key;
        height = 1; // initial height is 1
    }

    public Node(Node root, Node par) {
    }

}
