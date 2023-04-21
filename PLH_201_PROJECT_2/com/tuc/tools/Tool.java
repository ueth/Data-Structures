package com.tuc.tools;

import com.tuc.bst.BinaryTree;
import com.tuc.bst.ThreadedBinaryTree;

import java.util.Random;

public class Tool {

    static BinaryTree bst = new BinaryTree();
    static ThreadedBinaryTree tbst = new ThreadedBinaryTree();

    public static void main(String[] args) {
        binaryTreeOperations();
        threadedBinaryTreeOperations();
        sortedArrayOperations();
    }

    private static void binaryTreeOperations() {
        System.out.println();
        System.out.println("---BinaryTree Searches---");

        /*Fill the tree with random numbers*/
        for (int i = 0; i < 100000; i++) {
            bst.resetCurrentPos();
            bst.insert(UniqueRandomGenerator.getRandomInts()[i], 0);
        }
        System.out.println("Times compared in insertion in Binary Tree: " + Counter.getCounter(0) / 100000);

        /*Search for 100 random numbers*/
        for (int i = 0; i < 100; i++)
            bst.findKey(bst.getRoot(), UniqueRandomGenerator.getRandomKeys()[i]);
        System.out.println("Times compared searching a random key in Binary Tree: " + Counter.getCounter(1) / 100);

        for (int i = 0; i < 100; i++) {
            bst.rangeSearch(0, i * 100, i * 100 + 100);
        }
        System.out.println("Times compared searching a key with range search in Binary Tree (when K = 100): " + Counter.getCounter(5) / 100);

        Counter.clearCounter(5);

        for (int i = 0; i < 100; i++) {
            bst.rangeSearch(0, i * 100, i * 100 + 1000);
        }
        System.out.println("Times compared searching a key with range search in Binary Tree (when K = 1000): " + Counter.getCounter(5) / 100);
    }

    private static void threadedBinaryTreeOperations() {
        System.out.println();
        System.out.println("---ThreadedBinaryTree Searches---");

        /*Fill the tree with random numbers*/
        for (int i = 0; i < 100000; i++)
            tbst.insert(UniqueRandomGenerator.getRandomInts()[i], 0);
        System.out.println("Times compared in insertion in Threaded Binary Tree: " + Counter.getCounter(2) / 100000);

        /*Search for 100 random numbers*/
        for (int i = 0; i < 100; i++)
            tbst.findKey(0, UniqueRandomGenerator.getRandomKeys()[i]);
        System.out.println("Times compared searching a random key in Threaded Binary Tree: " + Counter.getCounter(3) / 100);

        for (int i = 0; i < 100; i++) {
            tbst.rangeSearch(0, i * 100, i * 100 + 100);
        }

        System.out.println("Times compared searching a key with range search in Threaded Binary Tree (when K = 100): " + Counter.getCounter(6) / 100);

        Counter.clearCounter(6);

        for (int i = 0; i < 100; i++) {
            tbst.rangeSearch(0, i * 100, i * 100 + 1000);
        }

        System.out.println("Times compared searching a key with range search in Threaded Binary Tree (when K = 1000): " + Counter.getCounter(6) / 100);
    }

    private static void sortedArrayOperations() {
        System.out.println();
        System.out.println("---Binary Searches---");

        Random random = new Random();
        int ran = random.nextInt(1000);

        for (int i = 0; i < 100; i++)
            BinarySearch.binarySearch(UniqueRandomGenerator.getSortedInts(), 0,
                    UniqueRandomGenerator.getSortedInts().length, i + ran);
        System.out.println("Times compared searching the key: " + Counter.getCounter(4) / 100);

        Counter.clearCounter(4);

        for (int i = 0; i < 100; i++) {
            BinarySearch.binaryRangeSearch(UniqueRandomGenerator.getSortedInts(), i * 100 + ran , i * 100 + ran + 100);
        }
        System.out.println("Times compared searching the key with range search (K = 100): " + Counter.getCounter(4) / 100);

        Counter.clearCounter(4);

        for (int i = 0; i < 100; i++) {
            BinarySearch.binaryRangeSearch(UniqueRandomGenerator.getSortedInts(), i * 100 + ran , i * 100 + ran + 1000);
        }
        System.out.println("Times compared searching the key with range search (K = 1000): " + Counter.getCounter(4) / 100);
    }
}
