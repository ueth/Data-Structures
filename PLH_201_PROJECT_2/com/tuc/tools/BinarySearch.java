package com.tuc.tools;

public class BinarySearch {

    /**
     * @return the position of the key in the array
     */
    public static int binarySearch(int[] array, int left, int right, int key) {
        if (right >= left && Counter.incCounter(4)) {
            int mid = left + (right - left) / 2;
            Counter.incCounter(4);

            if (array[mid] == key && Counter.incCounter(4))
                return mid;

            if (array[mid] > key && Counter.incCounter(4))
                return binarySearch(array, left, mid - 1, key);

            return binarySearch(array, mid + 1, right, key);
        }

        //if key hasn't been found
        return -1;
    }

    public static void binaryRangeSearch(int[] array, int left, int right){
        int pos = 0;
        Counter.incCounter(4);

        while( Counter.incCounter(4) && Counter.incCounter(4) &&(pos = binarySearch(array,0, array.length, left)) == -1) {
            left++;
            Counter.incCounter(4);
        }

        if (right >= left && Counter.incCounter(4)) {
            while(Counter.incCounter(4) && array[pos] <= right){
                if(Counter.incCounter(4) && array[pos] <= right) {
                    //print
                    pos++;
                    Counter.incCounter(4);
                }
            }
        }
    }
}
