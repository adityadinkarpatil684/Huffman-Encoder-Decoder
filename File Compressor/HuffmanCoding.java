import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;

class Node {
    char character;
    int freq;
    Node left, right;

    Node(char c, int f) {
        character = c;
        freq = f;
        left = right = null;
    }
}

public class HuffmanCoding {
    private static final int MAX_CHAR = 256;

    // Priority Queue builder
    private static PriorityQueue<Node> buildMinHeap(int[] freq) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.freq));
        for (int i = 0; i < MAX_CHAR; i++) {
            if (freq[i] > 0) {
                pq.add(new Node((char) i, freq[i]));
            }
        }
        return pq;
    }

    // Huffman tree builder
    private static Node buildHuffmanTree(PriorityQueue<Node> pq) {
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();

            Node newNode = new Node('\0', left.freq + right.freq);
            newNode.left = left;
            newNode.right = right;

            pq.add(newNode);
        }
        return pq.poll();
    }

    // Huffman codes assignment
    private static void assignCodes(Node root, String str, String[] hashmap) {
        if (root == null) return;

        if (root.left == null && root.right == null) {
            hashmap[root.character] = str;
            return;
        }

        assignCodes(root.left, str + "0", hashmap);
        assignCodes(root.right, str + "1", hashmap);
    }

    // Write bytes in 8-bit chunks
    private static void writeByte(FileOutputStream out, StringBuilder bitBuffer) throws IOException {
        while (bitBuffer.length() >= 8) {
            String byteStr = bitBuffer.substring(0, 8);
            int byteVal = Integer.parseInt(byteStr, 2);
            out.write(byteVal);
            bitBuffer.delete(0, 8);
        }
    }

    // Compression
    private static void compress(String inputFile, String outputFile) throws IOException {
        int[] freq = new int[MAX_CHAR];
        FileInputStream input = new FileInputStream(inputFile);
        int c;
        while ((c = input.read()) != -1) {
            freq[c]++;
        }
        input.close();

        PriorityQueue<Node> pq = buildMinHeap(freq);
        Node root = buildHuffmanTree(pq);

        String[] hashmap = new String[MAX_CHAR];
        assignCodes(root, "", hashmap);

        // Print codes
        System.out.println("\nHuffman Codes and Frequencies:");
        for (int i = 0; i < MAX_CHAR; i++) {
            if (freq[i] > 0) {
                System.out.println((char) i + " (freq: " + freq[i] + "): " + hashmap[i]);
            }
        }

        // Write compressed file
        input = new FileInputStream(inputFile);
        FileOutputStream outFile = new FileOutputStream(outputFile);
        ObjectOutputStream metaOut = new ObjectOutputStream(outFile);

        // Save frequencies for decompression
        metaOut.writeObject(freq);

        StringBuilder bitBuffer = new StringBuilder();
        while ((c = input.read()) != -1) {
            bitBuffer.append(hashmap[c]);
            writeByte(outFile, bitBuffer);
        }

        // Pad bits
        if (bitBuffer.length() > 0) {
            while (bitBuffer.length() < 8) {
                bitBuffer.append("0");
            }
            writeByte(outFile, bitBuffer);
        }

        input.close();
        metaOut.close();
        outFile.close();

        System.out.println("File compressed successfully into " + outputFile);
    }

    // Decompression
    private static void decompress(String compressedFile, String outputFile) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(compressedFile);
        ObjectInputStream metaIn = new ObjectInputStream(fileIn);

        int[] freq = (int[]) metaIn.readObject();

        PriorityQueue<Node> pq = buildMinHeap(freq);
        Node root = buildHuffmanTree(pq);

        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int b;
        while ((b = fileIn.read()) != -1) {
            byteBuffer.write(b);
        }

        StringBuilder bits = new StringBuilder();
        for (byte by : byteBuffer.toByteArray()) {
            String binary = String.format("%8s", Integer.toBinaryString(by & 0xFF)).replace(' ', '0');
            bits.append(binary);
        }

        FileWriter writer = new FileWriter(outputFile);
        Node current = root;
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '0') {
                current = current.left;
            } else {
                current = current.right;
            }

            if (current.left == null && current.right == null) {
                writer.write(current.character);
                current = root;
            }
        }
        writer.close();

        metaIn.close();
        fileIn.close();

        System.out.println("File decompressed successfully into " + outputFile);
    }

    public static void main(String[] args) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            System.out.println("Select file to compress:");
            if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;
            String inputFile = fileChooser.getSelectedFile().getAbsolutePath();

            String compressedFile = "compressed.bin";
            String decompressedFile = "decompressed.txt";

            compress(inputFile, compressedFile);
            decompress(compressedFile, decompressedFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
