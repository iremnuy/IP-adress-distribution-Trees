import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class AvlTree {

    Node root;

    int height(Node S) {
        if (S == null)
            return 0;
        return S.height;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    Node rightRotate(Node y) {
        Node x = y.LeftChild;
        Node T2 = x.RightChild;
        x.RightChild = y;
        y.LeftChild = T2;
        y.height = max(height(y.LeftChild), height(y.RightChild)) + 1;
        x.height = max(height(x.LeftChild), height(x.RightChild)) + 1;
        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.RightChild;
        Node T2 = y.LeftChild;
        y.LeftChild = x;
        x.RightChild = T2;
        x.height = max(height(x.LeftChild), height(x.RightChild)) + 1;
        y.height = max(height(y.LeftChild), height(y.RightChild)) + 1;
        return y;
    }

    // Get balance factor of a node
    int getBalanceFactor(Node N) {
        if (N == null)
            return 0;
        return height(N.LeftChild) - height(N.RightChild);
    }

    // insertion
    Node insertNode(Node root, String key, FileWriter writer) throws IOException {

        if (root == null) {
            return new Node(key);
        }

        /* Otherwise, recur down the tree */
        if (root.IP.compareTo(key) > 0) {
            writer.write(root.IP + ": New node being added with IP:" + key + "\n");
            root.LeftChild = insertNode(root.LeftChild, key, writer);

        } else if (root.IP.compareTo(key) < 0) { // if root ıp is greater than the newly added node
            writer.write(root.IP + ": New node being added with IP:" + key + "\n");
            root.RightChild = insertNode(root.RightChild, key, writer);

        } else {
            return root;
        }

        root.height = 1 + max(height(root.LeftChild), height(root.RightChild));

        int balanceFactor = getBalanceFactor(root);
        if (balanceFactor > 1) {
            if (root.LeftChild.IP.compareTo(key) > 0) { // then the inserted node is at the left of the current,the left
                                                        // is heavier because balance ise greater than 1,so we have to
                                                        // right rotate
                writer.write("Rebalancing: right rotation" + "\n");
                return rightRotate(root);
            }
            if (root.LeftChild.IP.compareTo(key) < 0) { // then the unbalanced situation due to heaviness of the
                                                        // left side is caused by the new node which is greater
                                                        // than the current node,so we have to first leftrotate
                                                        // and make the trilogy linear and then rotate them to
                writer.write("Rebalancing: left-right rotation" + "\n"); // right to heal the left side
                root.LeftChild = leftRotate(root.LeftChild); // first rotate to left and make three of them linear
                return rightRotate(root); // then rotate right to take the middle node in between grandparent t
            }
        }
        if (balanceFactor < -1) {
            if (root.RightChild.IP.compareTo(key) < 0) {
                writer.write("Rebalancing: left rotation" + "\n");
                return leftRotate(root);
            }
            if (root.RightChild.IP.compareTo(key) > 0) {
                writer.write("Rebalancing: right-left rotation" + "\n");
                root.RightChild = rightRotate(root.RightChild);
                return leftRotate(root);
            }
        }

        return root;// at the end the initial root will return right???,This makes sure that as we
        // move back up the tree, the other node connections aren't changed.
    }

    public void send(String senderip, String rec_ip, FileWriter myWritera) throws IOException {
        ArrayList<Node> ArrayOfSender = Search(root, senderip, new ArrayList<Node>());
        ArrayList<Node> ArrayOfRec = Search(root, rec_ip, new ArrayList<Node>());
        if (!(ArrayOfRec.size() == 1 || ArrayOfSender.size() == 1)) {

            while (!(ArrayOfRec.get(0) == ArrayOfSender.get(0) && ArrayOfRec.get(1) != ArrayOfSender.get(1))) {
                ArrayOfRec.remove(0);
                ArrayOfSender.remove(0);
                ArrayOfSender.removeAll(Collections.singleton(null));
                ArrayOfRec.removeAll(Collections.singleton(null));

            }
        }
        ArrayOfRec.remove(0);
        Collections.reverse(ArrayOfSender);
        ArrayOfSender.addAll(ArrayOfRec);

        ArrayOfSender.removeAll(Collections.singleton(null));
        for (int i = 0; i < ArrayOfSender.size(); i++) {

            if (i == 0) {
                myWritera.write(senderip + ": Sending message to: " + rec_ip + "\n");
            } else if (i == ArrayOfSender.size() - 1) {
                myWritera.write(
                        ArrayOfSender.get(i).IP + ": Received message from: " + senderip + "\n");

            } else {
                myWritera.write(ArrayOfSender.get(i).IP + ": Transmission from: " + ArrayOfSender.get(i - 1).IP
                        + " receiver: " + rec_ip + " sender:" + senderip + "\n");

            }

        }

    }

    public ArrayList<Node> Search(Node root, String ip, ArrayList<Node> newal) throws NoSuchElementException { // creates
                                                                                                               // an
                                                                                                               // arraylist
                                                                                                               // from
                                                                                                               // root
                                                                                                               // to
                                                                                                               // search
                                                                                                               // node
        // if root is null project2 can write it to
        // console
        if (root == null) {
            return newal;
        }

        if (ip.equals(root.IP)) {
            newal.add(root);
            return newal;
        } else if (root.IP.compareTo(ip) < 0) {
            newal.add(root);
            return Search(root.RightChild, ip, newal);

        } else {
            newal.add(root);
            return Search(root.LeftChild, ip, newal);
        }

    }

    public Node Deletion(Node root, String ip, FileWriter writer, Node parent) throws IOException {

        // Find the node to be deleted and remove it
        if (root == null)
            return root;
        if (root.IP.compareTo(ip) > 0)
            root.LeftChild = Deletion(root.LeftChild, ip, writer, root);
        else if (root.IP.compareTo(ip) < 0)
            root.RightChild = Deletion(root.RightChild, ip, writer, root);
        else {
            if ((root.LeftChild == null) || (root.RightChild == null)) {
                Node temp = null;
                if (temp == root.LeftChild)
                    temp = root.RightChild;
                else
                    temp = root.LeftChild;
                if (temp == null) { // leaf node sceneraio
                    writer.write(parent.IP + ": Leaf Node Deleted: " + ip + "\n"); // using parent ip for leaf nodes
                                                                                   // deletion
                    // message

                    temp = root; // node to be deleted which is leaf
                    root = null;
                } else {
                    // writer.write("Non Leaf Node Deleted; removed: " + root.IP + " replaced: " +
                    // temp.IP + "\n");
                    writer.write(parent.IP + ": Node with single child Deleted: " + root.IP + "\n");

                    root = temp; // one childed node now is replaced by its only child which the temp holds
                }
            } else { // this is a two childed node which we attempt to delete
                Node temp = MinValofRight(root.RightChild);
                writer.write("Non Leaf Node Deleted; removed: " + root.IP + " replaced: " + temp.IP + "\n");
                String newIp = temp.IP;
                root.IP = newIp;
                if (temp.RightChild == null)
                    temp = temp.RightChild;
                else {
                    temp = null;
                }
            }
        }
        if (root == null)
            return root;
        root.height = max(height(root.LeftChild), height(root.RightChild)) + 1;
        int balanceFactor = getBalanceFactor(root);
        if (balanceFactor > 1) {
            if (getBalanceFactor(root.LeftChild) >= 0) {
                writer.write("Rebalancing: right rotation" + "\n");
                return rightRotate(root);

            } else {
                writer.write("Rebalancing: left-right rotation" + "\n");
                root.LeftChild = leftRotate(root.LeftChild);
                return rightRotate(root);

            }
        }
        if (balanceFactor < -1) {
            if (getBalanceFactor(root.RightChild) <= 0) {
                writer.write("Rebalancing: left rotation" + "\n");
                return leftRotate(root);

            } else {
                writer.write("Rebalancing: right-left rotation" + "\n");
                root.RightChild = rightRotate(root.RightChild);
                return leftRotate(root);

            }
        }
        return root;
    }

    private Node MinValofRight(Node rightChild) { // finds the min val of right subtree
        Node sample = rightChild;
        while (sample.LeftChild != null) {
            sample = sample.LeftChild;
        }
        return sample;
    }

}
