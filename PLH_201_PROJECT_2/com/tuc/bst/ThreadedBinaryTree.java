package com.tuc.bst;

import com.tuc.interfaces.IBinaryTree;
import com.tuc.tools.Counter;
/**
 * Threaded Binary Tree implemented with 2d array.
 * _tree[x][0] = key
 * _tree[x][1] = left node
 * _tree[x][2] = right node
 * _tree[x][3] = left thread
 * _tree[x][4] = right thread
 */
public class ThreadedBinaryTree implements IBinaryTree {

    private static final int NUMBER_OF_NODES = 100000;

    private final int[][] _tree;
    private int _nextPosition;

    public ThreadedBinaryTree(){
        _tree = new int[NUMBER_OF_NODES][5];
        initTree();
    }

    /**
     * Initiate stack pointers
     */
    private void initTree(){
        for(int i = 0; i<NUMBER_OF_NODES; i++){
            _tree[i][0] = -1;
            _tree[i][1] = -1;
            _tree[i][2] = i+1;
            _tree[i][3] = -1;
            _tree[i][4] = -1;
        }
        _tree[NUMBER_OF_NODES-1][2] = 0;
    }

    public int getRightThread(int pos){ return _tree[pos][4]; }
    public int getLeftThread(int pos){ return _tree[pos][3]; }
    public int getRight(int pos){ return _tree[pos][2]; }
    public int getLeft(int pos){ return _tree[pos][1]; }

    private void setRightThread(int num, int pos){ _tree[pos][4] = num; }
    private void setLeftThread(int num, int pos){ _tree[pos][3] = num; }
    public void setRight(int num, int pos){ _tree[pos][2] = num; }
    public void setLeft(int num, int pos){ _tree[pos][1] = num; }

    private boolean isRightThread(int pos){ return getRightThread(pos) == 1; }
    private boolean isLeftThread(int pos){ return getLeftThread(pos) == 1; }

    public int getKey(int pos){ return _tree[pos][0]; }

    /**
     * When adding a key we must hold the next available position
     */
    public void addKey(int key, int pos){
        _nextPosition = _tree[pos][2];
        _tree[pos][0] = key;
        setLeft(-1, pos);
        setRight(-1, pos);
        setLeftThread(1, pos);
        setRightThread(1, pos);

        Counter.incCounter(2);
        Counter.incCounter(2);
        Counter.incCounter(2);
        Counter.incCounter(2);
        Counter.incCounter(2);
        Counter.incCounter(2);
    }

    public void insert(int key, int pos) {
        int parentPos=-1;

        while(Counter.incCounter(2) && getKey(pos) != -1){
            parentPos = pos;
            Counter.incCounter(2);

            if(Counter.incCounter(2) && key < getKey(pos)){
                if(Counter.incCounter(2) && !isLeftThread(pos)) {
                    pos = getLeft(pos);
                    Counter.incCounter(2);
                }
                else
                    break;
            }
            else{
                if(Counter.incCounter(2) && !isRightThread(pos)) {
                    pos = getRight(pos);
                    Counter.incCounter(2);
                }
                else
                    break;
            }
        }

        if(Counter.incCounter(2) && parentPos == -1){
            addKey(key, pos);
            return;
        }

        pos = _nextPosition;
        Counter.incCounter(2);
        addKey(key, pos);

        if(Counter.incCounter(2) && key < getKey(parentPos)){
            setLeft(getLeft(parentPos), pos);
            setRight(parentPos, pos);
            setLeftThread(-1, parentPos);
            setLeft(pos, parentPos);
            Counter.incCounter(2);
            Counter.incCounter(2);
            Counter.incCounter(2);
            Counter.incCounter(2);
        }
        else{
            setLeft(parentPos, pos);
            setRight(getRight(parentPos), pos);
            setRightThread(-1, parentPos);
            setRight(pos, parentPos);
            Counter.incCounter(2);
            Counter.incCounter(2);
            Counter.incCounter(2);
            Counter.incCounter(2);
        }
    }

    public int findKey(int pos, int key) {
        if (Counter.incCounter(3) && pos == -1)
            return -1;

        while(Counter.incCounter(3) && pos != -1){
            if(Counter.incCounter(3) && key < getKey(pos)) {
                pos = getLeft(pos);
                Counter.incCounter(3); //inc counter
            }
            else {
                pos = getRight(pos);
                Counter.incCounter(3); // inc counter
            }
            if(Counter.incCounter(3) && key == getKey(pos))
                break;
        }

        if(Counter.incCounter(3) && pos != -1) {
            /*System.out.println("Key found "+ getKey(pos));*/
            return key;
        }

        return -1;
    }

    private int leftMost(int pos, int k1){
        while(Counter.incCounter(6) && !isLeftThread(pos) && Counter.incCounter(6) && k1 <= getKey(pos)) {
            pos = getLeft(pos);
            Counter.incCounter(6);
        }

        return pos;
    }

    public void rangeSearch(int pos, int k1, int k2){
        pos = leftMost(pos, k1);
        Counter.incCounter(6);

        while(Counter.incCounter(6) && k2 >= getKey(pos)){

            if(Counter.incCounter(6) && isRightThread(pos)) {
                pos = getRight(pos);
                Counter.incCounter(6);

                if(Counter.incCounter(6) && k1 <= getKey(pos)) {
                    /*System.out.println("Key "+ getKey(pos));*/
                }
            }
            else {
                pos = leftMost(getRight(pos), k1);
                Counter.incCounter(6);
            }
        }
    }
}
