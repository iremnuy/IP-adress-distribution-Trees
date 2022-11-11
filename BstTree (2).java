import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class BstTree {
    Node root;

    BstTree() {
        root = null;
    }

    BstTree(String root_IP) {
        root = new Node(root_IP);

    }
    // void insert(String key) { root = insertNode(root, key); } //if Addnode is
    // read by the fileReader call this function

    /*
     * A recursive function to
     * insert a new key in BST
     */
    Node insertNode(Node root, String key, FileWriter w, Node parent) throws IOException {

        /*
         * If the tree is empty,
         * return a new node
         */
        if (root == null) {

            root = new Node(key);

            return root;
        }

        /* Otherwise, recur down the tree */
        else if (root.IP.compareTo(key) > 0) {
            w.write(root.IP + ": New node being added with IP:" + key + "\n");

            root.LeftChild = insertNode(root.LeftChild, key, w, root);
        } else if (root.IP.compareTo(key) < 0) {// if root ıp is greater than the newly added node
            w.write(root.IP + ": New node being added with IP:" + key + "\n");
            root.RightChild = insertNode(root.RightChild, key, w, root);
        }

        /* return the (unchanged) node pointer */
        return root; // at the end the initial root will return right???,This makes sure that as we
                     // move back up the tree, the other node connections aren't changed.
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

    public void send(String senderip, String rec_ip, FileWriter b) throws IOException {
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
                b.write(senderip + ": Sending message to: " + rec_ip + "\n");
            } else if (i == ArrayOfSender.size() - 1) {
                b.write(
                        ArrayOfSender.get(i).IP + ": Received message from: " + senderip + "\n");

            } else {
                b.write(ArrayOfSender.get(i).IP + ": Transmission from: " + ArrayOfSender.get(i - 1).IP
                        + " receiver: " + rec_ip + " sender:" + senderip + "\n");

            }

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
