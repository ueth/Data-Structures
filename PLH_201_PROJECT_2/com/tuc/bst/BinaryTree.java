package com.tuc.bst;

import com.tuc.interfaces.IBinaryTree;
import com.tuc.tools.Counter;

/**
 * Binary Tree implemented with 2d array.
 * _tree[x][0] = key
 * _tree[x][1] = left node
 * _tree[x][2] = right node
 */
public class BinaryTree implements IBinaryTree {

    private static final int NUMBER_OF_NODES = 100000;
    private final int ROOT = 0;

    private final int[][] _tree;
    private int _currentPos;
    private int _nextPosition;

    public BinaryTree(){
        _currentPos = ROOT;
        _tree = new int[NUMBER_OF_NODES][3];
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
        }
        _tree[NUMBER_OF_NODES-1][2] = 0;
    }

    public void resetCurrentPos(){ _currentPos = ROOT; }
    public int getRoot(){ return ROOT; }

    public int getRight(int pos){ return _tree[pos][2]; }
    public int getLeft(int pos){
        return _tree[pos][1];
    }
    public void setRight(int num, int pos) { _tree[pos][2] = num; }
    public void setLeft(int num, int pos) { _tree[pos][1] = num; }
    public int getKey(int pos){
        return _tree[pos][0];
    }

    public boolean isEmpty(int pos){ return getKey(pos) == -1; }

    public int getNextAvailablePosition(){ return _tree[_currentPos][2]; }

    /**
     * When we add a new key we must hold the next available position
     */
    public void addKey(int key){
        _nextPosition = getNextAvailablePosition();
        _tree[_currentPos][0] = key;
        setLeft(-1, _currentPos);
        setRight(-1, _currentPos);

        Counter.incCounter(0);
        Counter.incCounter(0);
        Counter.incCounter(0);
        Counter.incCounter(0);
    }

    /**
     * If the left node is free, then we set it to the next available position
     * @return currentPosition to add the key to
     */
    public int getLeftPointer(){
        if(Counter.incCounter(0) && getLeft(_currentPos) == -1) {
            setLeft(_nextPosition, _currentPos);
            Counter.incCounter(0);
        }

        Counter.incCounter(0);
        return _currentPos = getLeft(_currentPos);
    }

    /**
     * If the right node is free, then we set it to the next available position
     * @return currentPosition to add the key to
     */
    public int getRightPointer() {
        if(Counter.incCounter(0) && getRight(_currentPos) == -1) {
            setRight(_nextPosition, _currentPos);
            Counter.incCounter(0);
        }

        Counter.incCounter(0);
        return _currentPos = getRight(_currentPos);
    }

    public void insert(int key, int pos){
        if(Counter.incCounter(0) && isEmpty(pos)) {
            addKey(key);
            return;
        }

        if(Counter.incCounter(0) && key < getKey(pos))
            insert(key, getLeftPointer());
        else
            insert(key, getRightPointer());
    }

    public void rangeSearch(int pos, int k1, int k2){
        if(Counter.incCounter(5) && pos == -1)
            return;

        if(Counter.incCounter(5) && k1 <= getKey(pos))
            rangeSearch(getLeft(pos), k1, k2);

        if(Counter.incCounter(5) && k1 <= getKey(pos) && Counter.incCounter(5) && k2 >= getKey(pos)){
            /*System.out.println("Key found: " + getKey(pos));*/
        }

        if(Counter.incCounter(5) && k2 >= getKey(pos))
            rangeSearch(getRight(pos), k1, k2);
    }

    public int findKey(int pos, int key) {
        if (Counter.incCounter(1) && pos == -1)
            return -1;

        if (Counter.incCounter(1) && getKey(pos) > key)
            return findKey(getLeft(pos), key);
        else if (Counter.incCounter(1) && getKey(pos) == key)
            return pos;

        return findKey(getRight(pos), key);
    }
}
