package org.tuc.node;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.tuc.plh201.FileManager;

public class SortedNodeGenerator {
	private final static int PAGE_SIZE = 128;
	private static SortedNodeGenerator _instance = null;
	private static ArrayList<Node> _nodes = new ArrayList<>();
	private static int _counter = 0;
	private static  byte[] _buffer = new byte[PAGE_SIZE];
	private static int NO_OF_ELEMENTS = 10000;
	
	
	private SortedNodeGenerator() {}
	
	public void resetCoutner() {_counter = 0;}
	public void clearNodeList() {
		_nodes.clear();
	}
	
	public static void loadTheHoleFile(FileManager fm) throws IOException {
		fm.resetPos();
		int startingPos;
		
		while(fm.readNextBlock() != 0) {
			if(fm.getPointer())  // pointer file
				for(int i=0; i<16; i++) {
					startingPos = i*8;
					
					_nodes.add(new Node(fm.byteToInt(fm.getBuffer(), 4, startingPos), fm.byteToInt(fm.getBuffer(), 4, startingPos+4)));
				}			
			else  // data file
				for(int i=0; i<4; i++) {
					startingPos = i*32;
					
					_nodes.add(new Node(fm.byteToInt(fm.getBuffer(), 4, startingPos), new String(fm.getBuffer(),startingPos+4,28, StandardCharsets.UTF_8)));
				}			
		}
	}
	
	public void printAllNodeKeys() {
		for(Node node : _nodes){
			System.out.println(node.getKey());
		}
	}
	
	
	/**
	 * This method fills buffer with random data
	 * @throws IOException
	 */
	public static byte[] fillBufferWithData() throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	DataOutputStream out = new DataOutputStream(bos);
    	
    	for(int i=0; i<4; i++) {
    		final Node node = getNode();
    		final byte[] src = node.getData().getBytes();
    		out.writeInt(node.getKey());
    		
    		final byte dst[] = new byte[28];
    		System.arraycopy(src, 0, dst, 0, src.length);
    		
    		out.write(dst, 0, 28);
    	}
    	_buffer = bos.toByteArray();
    	bos.close();
    	out.close();
    	return _buffer;
	}
	
	/**
	 * This method fills the buffer with keys and pointers
	 * @throws IOException
	 */
	public static byte[] fillBufferWithKeysAndPointers() throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	DataOutputStream out = new DataOutputStream(bos);
    	
    	for(int i=0; i<16; i++) {
    		final Node node = getNode();
    		out.writeInt(node.getKey());
    		out.writeInt(node.getPointer());
    	}
    	_buffer = bos.toByteArray();
    	bos.close();
    	out.close();
    	return _buffer;
	}
	
	public static void sortNodes() {
		SortNodes.getInstance().sortNodes(_nodes);
	}
	
	public boolean isCounterAtMax() {
		return _counter == NO_OF_ELEMENTS;
	}
	
	public static Node getNode() {
		return _nodes.get(_counter++);
	}
	
	public static ArrayList<Node> getNodes(){
		return _nodes;
	}
	
	public static SortedNodeGenerator getInstance() {
		return _instance == null ? _instance = new SortedNodeGenerator() : _instance;
	}
}

