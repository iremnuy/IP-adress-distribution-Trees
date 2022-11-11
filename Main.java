import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    static AvlTree avlNode = new AvlTree();
    static BstTree bstNode = new BstTree();

    public static void main(String[] args) throws Exception {
        try {
            File myObj = new File(args[0]);
            /*
             * File myObj1 = new
             * File("C:\\Users\\wbagger\\Desktop\\project2\\Project2\\inputs\\send.txt");
             * File Fileavl = new File(
             * "C:\\Users\\wbagger\\Desktop\\project2\\Project2\\outputs\\send_av5l.txt");
             * File Filebst = new File(
             * "C:\\Users\\wbagger\\Desktop\\project2\\Project2\\outputs\\send_bst.txt");
             */
            File file_avl = new File(args[1] + "_avl.txt");
            File file_bst = new File(args[1] + "_bst.txt");
            FileWriter myWritera = new FileWriter(file_avl.getAbsoluteFile(), StandardCharsets.UTF_16);

            Scanner myReader = new Scanner(myObj);

            // FileWriter myWritera = new FileWriter(file_avl.getAbsoluteFile(),
            // StandardCharsets.UTF_16);
            FileWriter myWriterb = new FileWriter(file_bst.getAbsoluteFile(), StandardCharsets.UTF_16);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                FunctionChecker(data, myWritera, myWriterb);
            }

            myReader.close();
            myWritera.close();
            myWriterb.close();

        } catch (FileNotFoundException e) {
            System.out.println("Couldn't found the file");
        }

    }

    private static void FunctionChecker(String data, FileWriter myWritera, FileWriter myWriterb) throws IOException {
        String[] arrData = data.split(" ");
        if (arrData[0].length() == 1) { // for the first line,assuming no other line has length 1
            String root = arrData[0]; // no function call no message
            // avlNode.root.IP=root;
            avlNode.root = new Node(root);

            // bstNode.root.IP = root;
            bstNode.root = new Node(root);
            // F 1 100 F 2 200 B 1 100 B 2 200

        }
        if (arrData[0].equals("ADDNODE")) {
            avlNode.insertNode(avlNode.root, arrData[1], myWritera); // maybe root= is
                                                                     // unnecessary
            bstNode.insertNode(bstNode.root, arrData[1], myWriterb,
                    bstNode.root);

        }

        if (arrData[0].equals("DELETE")) {
            avlNode.Deletion(avlNode.root, arrData[1], myWritera, avlNode.root);
            bstNode.Deletion(bstNode.root, arrData[1], myWriterb, bstNode.root);

        }
        if (arrData[0].equals("SEND")) {
            String sendkey = arrData[1];
            String reckey = arrData[2];
            avlNode.send(sendkey, reckey, myWritera);
            bstNode.send(sendkey, reckey, myWriterb);

        }
    }
}
