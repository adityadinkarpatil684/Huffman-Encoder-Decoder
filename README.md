# Huffman Compression & Decompression in Java

## 📌 Overview

This project implements **Huffman Coding** in Java for file compression and decompression. It allows you to select a text file using a file chooser, compress it into a binary file (`compressed.bin`), and then decompress it back into a readable text file (`decompressed.txt`).

Huffman Coding is a popular lossless data compression algorithm that assigns shorter codes to more frequent characters and longer codes to less frequent ones.

---

## 🚀 Features

* ✅ File selection via **JFileChooser** (no hardcoding of filenames).
* ✅ Compression of text files into a binary format.
* ✅ Decompression back to the original text.
* ✅ Displays Huffman codes and character frequencies in the console.
* ✅ Uses a **priority queue (min-heap)** for Huffman tree construction.

---

## 📂 File Structure

```
HuffmanCoding.java   # Main program (compression + decompression)
compressed.bin       # Output compressed file
decompressed.txt     # Restored file after decompression
```

---

## ⚙️ How It Works

1. **Compression:**

   * Reads the input file and calculates character frequencies.
   * Builds a Huffman tree using a priority queue.
   * Assigns binary codes to characters.
   * Writes a compressed binary file containing both the frequency table and encoded content.

2. **Decompression:**

   * Reads frequency table from the compressed file.
   * Reconstructs the Huffman tree.
   * Decodes the bitstream into the original text.

---

## ▶️ Usage

1. Compile the project:

   ```bash
   javac HuffmanCoding.java
   ```

2. Run the program:

   ```bash
   java HuffmanCoding
   ```

3. Select a file (e.g., `testing.txt`) when prompted.

4. The program will:

   * Generate `compressed.bin` (compressed file)
   * Generate `decompressed.txt` (restored file)

---

## 🖥️ Example Console Output

```
Select file to compress:

Huffman Codes and Frequencies:
a (freq: 5): 01
b (freq: 2): 001
c (freq: 1): 000
...

File compressed successfully into compressed.bin
File decompressed successfully into decompressed.txt
```

---

![image alt](https://github.com/adityadinkarpatil684/Huffman-Encoder-Decoder/blob/04a96408da94bfa305d919bcfc88d28a5e71ab06/Screenshot%202025-08-25%20191621.png)

## 📖 References

* Huffman, D. A. (1952). *A Method for the Construction of Minimum-Redundancy Codes.* Proceedings of the IRE.
* [GeeksforGeeks - Huffman Coding](https://www.geeksforgeeks.org/huffman-coding-greedy-algo-3/)

---

## 📝 License

This project is licensed under the MIT License. You are free to use, modify, and distribute it.
