package com.tuc.tools;

/**
 * _counter[0] -> counts comparisons in BinarySearchTree insert
 * _counter[1] -> counts comparisons in BinarySearchTree findKey
 * _counter[2] -> counts comparisons in ThreadedBinarySearchTree insert
 * _counter[3] -> counts comparisons in ThreadedBinarySearchTree findKey
 * _counter[4] -> counts comparisons in sorted array with binary search
 * _counter[5] -> counts comparisons in BinarySearchTree rangeSearch
 * _counter[6] -> counts comparisons in ThreadedBinarySearchTree rangeSearch
 */
public class Counter {
    private static final int[] _counter = new int[7];

    public static boolean incCounter(int pos) {
        _counter[pos]++;
        return true;
    }

    public static void clearCounter(int pos) {
        _counter[pos] = 0;
    }

    public static int getCounter(int pos) {
        return _counter[pos];
    }
}
