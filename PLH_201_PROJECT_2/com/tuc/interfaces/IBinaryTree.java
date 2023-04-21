package com.tuc.interfaces;

public interface IBinaryTree {

    int getRight(int pos);

    int getLeft(int pos);

    int getKey(int pos);

    void setRight(int num, int pos);

    void setLeft(int num, int pos);

    void insert(int key, int pos);

    int findKey(int pos, int key);

}
