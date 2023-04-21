package org.tuc.node;

public class Node {
	int _key;
	int _pointer;
	String _data;
	
	public Node(int key, String data) {
		_key = key;
		_data = data;
	}
	
	public Node(int key, int pointer) {
		_key = key;
		_pointer = pointer;
	}
	
	public int getKey() {return _key;}
	public void setKey(int key) {_key = key;}
	public String getData() {return _data;}
	public void setData(String data) {_data = data;}
	public int getPointer() {return _pointer;}
	public void setPointer(int pointer) {_pointer = pointer;}
}
