package org.tuc.node;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class RandomNodeGenerator {
	private static RandomNodeGenerator _instance = null;
	private final static int PAGE_SIZE = 128;
	private static int START_INT = 1;
	private static int END_INT = 1000001;
	private static int NO_OF_ELEMENTS = 10000;
	private static int[] _randomInts;
	private static int _randomKeys[] = new int[20];
	private static ArrayList<Node> _nodes = new ArrayList<>();
	private static int _counter = 0;
	private static int pointerCounter = 0;
	private static  byte[] _buffer = new byte[PAGE_SIZE];
	
	private RandomNodeGenerator() {}
	
	public void resetCoutner() {_counter = 0;}
	
	/**
	 * 
	 * @return true when it has given all of its indexes
	 */
	public boolean isCounterAtMax() {
		return _counter == NO_OF_ELEMENTS;
	}
	
	static {
		generateRandomNumbers();
		generateRandomKeys();
		fillNodes();
	}
	
	/**
	 * This method generates 10.000 random key numbers
	 */
	public static void generateRandomNumbers() {
		java.util.Random randomGenerator = new java.util.Random();
		_randomInts = randomGenerator.ints(START_INT, END_INT).distinct().limit(NO_OF_ELEMENTS).toArray();
	}
	
	public static void generateRandomKeys() {
		java.util.Random randomGenerator = new java.util.Random();
		int r[] = randomGenerator.ints(START_INT, NO_OF_ELEMENTS).distinct().limit(20).toArray();
		for(int i = 0; i < 20; i++) 
			_randomKeys[i] = _randomInts[r[i]];
	}
	
	public static int[] getRandomKeys() {
		return _randomKeys;
	}
	
	/**
	 * This method generates random strings
	 */
	public static String getAlphaNumericString(int length) 
    { 
        // chose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz"; 
  
        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(length); 
  
        for (int i = 0; i < length; i++) {
  
            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index = (int)(AlphaNumericString.length() * Math.random()); 
  
            // add Character one by one in end of sb 
            sb.append(AlphaNumericString.charAt(index)); 
        }
        return sb.toString(); 
    } 
	
	/**
	 * Fill an array of nodes that have random key(unique) and random data
	 */
	public static void fillNodes() {
		for(int i = 0; i<10000; i++)
			_nodes.add(new Node(_randomInts[i], getAlphaNumericString(28)));
	}
	
	public static Node getNode() {
		return _nodes.get(_counter++);
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
    		out.writeInt(getNode().getKey());
    		out.writeInt(pointerCounter);
    		if(i%4==0)pointerCounter++;
    	}
    	_buffer = bos.toByteArray();
    	bos.close();
    	out.close();
    	return _buffer;
	}
	
	public static RandomNodeGenerator getInstance() {
		return _instance == null ? _instance = new RandomNodeGenerator() : _instance;
	}
}
