package org.tuc.plh201;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

import org.tuc.node.RandomNodeGenerator;
import org.tuc.node.SortedNodeGenerator;
import org.tuc.types.FMType;

public class FileManager {

	private static final int PAGE_SIZE = 128;
	private String _fileName = "null";
	private int _pos = 0;
	private int _pageNumber = 0;
	private RandomAccessFile _file;
	private  byte[] _buffer = new byte[PAGE_SIZE];
	private int _timesSeached = 0;
	private boolean _pointer;
	private FMType _type;
	
	public FileManager() {}
	public FileManager(boolean b) {
		_pointer = b;
		_type = FMType.Random;
	}
	public FileManager(boolean b, FMType type) {
		_pointer = b;
		_type = type;
	}
	
	/**
	 * Getters-Setters
	 */
	private void incTimesSearched() {_timesSeached++;}
	public void clearTimesSearched() {_timesSeached=0;}
	private void decPageNumber() {_pageNumber--;}
	private void incPageNumber() {_pageNumber++;}
	public int getPageNumber() {return _pageNumber;}
	public int getTimesSearched() {return _timesSeached;}
	public int incAndGetPos() {return _pos++;}
	public void resetPos() {_pos = 2;}
	public boolean getPointer() {return _pointer;}
	public byte[] getBuffer() {return _buffer;}
	public String getSortedName() {return _fileName+"Sorted";}
	public String getFileName() {return _fileName;}
	public FMType getType() {return _type;}
	
	/**
	 * This method prints out the hole buffer
	 */
	public void printBuffer() {
		System.out.println(new String(_buffer, StandardCharsets.UTF_8));
	}
	
	/**
	 * This method prints file's information
	 */
	public void printFileName() {
		if(_pointer) {
			if(_type == FMType.Sorted)System.out.println("Sorted pointer file: "+_fileName);
			else System.out.println("Random pointer file: "+_fileName);
		}else {
			if(_type == FMType.Sorted)System.out.println("Sorted data file: "+_fileName);
			else System.out.println("Random data file: "+_fileName);
		}
	}
	public void printFilePages() {
		if(_pointer) {
			if(_type == FMType.Sorted)System.out.println("Sorted pointer file: "+_pageNumber);
			else System.out.println("Random pointer file: "+_pageNumber);
		}else {
			if(_type == FMType.Sorted)System.out.println("Sorted data file: "+_pageNumber);
			else System.out.println("Random data file: "+_pageNumber);
		}
	}
	public void printPos() {
		if(_pointer) {
			if(_type == FMType.Sorted)System.out.println("Sorted pointer file: "+_pos);
			else System.out.println("Random pointer file: "+_pos);
		}else {
			if(_type == FMType.Sorted)System.out.println("Sorted data file: "+_pos);
			else System.out.println("Random data file: "+_pos);
		}
	}
	
	
	
	
	public void sortFile() throws IOException {
		SortedNodeGenerator.loadTheHoleFile(this);
		SortedNodeGenerator.sortNodes();
	}
	
	/**
	 * This method prints the keys of a page
	 * <p>constructed for testing purposes
	 */
	public void printPageKeys() {
		for(int i=0; i<4; i++) 
			System.out.println(byteToInt(_buffer, 4, i*32));
	}
	
	public void printAllKeysInPointerFile() throws IOException {
		resetPos();
		while(readNextBlock() != 0) {
			for(int i=0; i<16; i++) 
				System.out.println(byteToInt(_buffer, 4, i*8));
		}
	}
	
	/**
	 * This method converts bytes to integers
	 * @see https://stackoverflow.com/questions/7619058/convert-a-byte-array-to-integer-in-java-and-vice-versa
	 * @param bytes
	 * @param length
	 * @param startingPos
	 * @return the integer of choice
	 */
	public int byteToInt(byte[] bytes, int length, int startingPos) {
        int val = 0;
        if(length>4) throw new RuntimeException("Too big to fit in int");
        for (int i = startingPos; i < length+startingPos; i++) {
            val=val<<8;
            val=val|(bytes[i] & 0xFF);
        }
        return val;
    }
	
	/**
	 * 
	 * @param key
	 * @return true if the key is smaller than the first key in buffer, false if it's not
	 */
	public boolean isKeySmaller(int key) {
		return key < byteToInt(_buffer, 4, 0) ? true : false;
	}
	
	/**
	 * 
	 * @param key
	 * @return 1 on success, 0 on failure
	 */
	public int searchForKeyInBuffer(int key) {
		for(int i=0; i<4; i++)
			if(key == byteToInt(_buffer, 4, i*32))
				return 1;		
		return 0;
	}
	
	/**
	 * 
	 * @param key
	 * @return 1 on success, 0 on failure
	 */
	public int searchForKeyInBufferForPointerFile(int key) {
		for(int i=0; i<16; i++)
			if(key == byteToInt(_buffer, 4, i*8))
				return 1;		
		return 0;
	}
	
	
	/**
	 * This method searches all pages of a file one by one until it finds a key
	 * @param key
	 * @return 1 if key found, 0 if not found
	 * @throws IOException
	 */
	public int searchForKeyInFile(int key) throws IOException {
		resetPos();
		while(readNextBlock() != 0) {
			if(_pointer) {
				if(searchForKeyInBufferForPointerFile(key) == 1) return 1;	// if key found
			}	
			else 
				if(searchForKeyInBuffer(key) == 1) return 1;	// if key found
		}

		return 0;
	}
	
	/**
	 * Fills the file with keys and data, or with keys and pointers
	 * @throws IOException
	 */
	public void fillFilePages() throws IOException {
		resetPos();
		while(appendBlock() != 0);
	}
	
	
	/**
	 * This method fills the buffer with keys and pointers
	 * @throws IOException
	 */
	public void fillBufferWithKeysAndPointers() throws IOException {
		_buffer = _type == FMType.Random ? RandomNodeGenerator.fillBufferWithKeysAndPointers() : SortedNodeGenerator.fillBufferWithKeysAndPointers();
	}
	
	/**
	 * This method fills buffer with random data
	 * @throws IOException
	 */
	public void fillBufferWithData() throws IOException {
		_buffer = _type == FMType.Random ? RandomNodeGenerator.fillBufferWithData() : SortedNodeGenerator.fillBufferWithData();
	}
	
	public void fillBufferWithData(boolean n) throws IOException {
		if(!n)fillBufferWithData();
		else fillBufferWithKeysAndPointers();
	}
	
	/**
	 * Saves the file's data to the first page
	 * First 4 bytes for position, next 4 bytes for number of pages, next 10 bytes for file's name
	 * @throws IOException 
	 */
	public void saveFileData() throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	DataOutputStream out = new DataOutputStream(bos);
    	out.writeInt(_pos);
    	out.writeInt(_pageNumber);
    	
    	final byte[] src = _fileName.getBytes();
		
		final byte dst[] = new byte[20];
		System.arraycopy(src, 0, dst, 0, src.length);
		
		out.write(dst, 0, 20);
		
		if(_pointer)
			out.writeInt(1);
		else out.writeInt(2);
		
		if(_type == FMType.Sorted) out.writeInt(1);
		else out.writeInt(2);
		
		_buffer = bos.toByteArray();
		
    	bos.close();
    	out.close();
		
		writeBlock(1);
	}
	
	/**
	 * This method creates a new file and initializes position of page and number of pages to 0
	 * @param fileName
	 * @throws IOException 
	 */
	public int createFile(String fileName) throws IOException {		
		_fileName = fileName;
		_pos = 0;
		_pageNumber = 0;
		_file = new RandomAccessFile (fileName, "rw");
		saveFileData();
		return 1;
	}
	
	/**
	 * This method opens an already existing file and loads the position, the name and the page number to the file manager
	 * @param fileName
	 * @return The number of pages in this file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public int openFile(String fileName) throws FileNotFoundException, IOException{
		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("File doesn't exist");
			return 0;
		}
		
		_file = new RandomAccessFile (fileName, "rw");
		
		_file.seek(0);
		_file.read(_buffer);
		_pos = byteToInt(_buffer, 4, 0);
		_pageNumber = byteToInt(_buffer, 4, 4);
		_fileName = new String(_buffer,8,20, StandardCharsets.UTF_8);
		
		int pointer = byteToInt(_buffer, 4, 28);
		int type = byteToInt(_buffer, 4, 32);
		
		if(pointer == 1)_pointer = true;
		else _pointer = false;
		if(type == 1) _type = FMType.Sorted;
		else _type = FMType.Random;

		return getPageNumber();
	}
	
	/**
	 * This method loads a selected page into buffer
	 * @param pos
	 * @return 0 on failure, 1 on success
	 * @throws IOException
	 */
	public int readBlock(int pos) throws IOException {
		if(pos>getPageNumber())return 0;
		incTimesSearched();
		_file.seek((pos-1)*PAGE_SIZE);
		_file.read(_buffer);
		return 1;
	}
	
	/**
	 * This method loads next page's data into buffer
	 * @return 0 on failure, 1 on success
	 * @throws IOException
	 */
	public int readNextBlock() throws IOException {
		return readBlock(incAndGetPos());
	}
	
	/**
	 * This method writes data from buffer into the chosen position of the file
	 * @param pos
	 * @return 0 on failure, 1 on success
	 * @throws IOException
	 */
	public int writeBlock(int pos) throws IOException {
		if(pos>getPageNumber()+1) return 0;
		if(RandomNodeGenerator.getInstance().isCounterAtMax() && pos != 1 && _type == FMType.Random)return 0; // reaching max index in RandomGenerator
		if(SortedNodeGenerator.getInstance().isCounterAtMax() && pos != 1 && _type == FMType.Sorted)return 0; // reaching max index in RandomGenerator
		
		if(pos!=1)fillBufferWithData(_pointer);
		_pos = pos;
		if(pos>getPageNumber())incPageNumber();
		_file.seek((pos-1)*PAGE_SIZE);
		_file.write(_buffer);
		return 1;
	}
	
	/**
	 * This method write into next page the data from buffer
	 * @return 0 on failure, 1 on success
	 * @throws IOException
	 */
	public int writeNextBlock() throws IOException {
		return writeBlock(incAndGetPos());
	}

	/**
	 * Appends one page into file
	 * @return 0 on failure, 1 on success
	 * @throws IOException
	 */
	public int appendBlock() throws IOException {	
		return writeBlock(getPageNumber()+1);
	}
	

	/**
	 * This method deletes the last page and pastes it in given position
	 * @param pos
	 * @return 0 on failure, 1 on success
	 * @throws IOException
	 */
	public int deleteBlock(int pos) throws IOException {
		if(pos>getPageNumber())return 0;
		
		readBlock(getPageNumber());
		writeBlock(pos);
		decPageNumber();
		return 1;
	}
	
	public void closeFile() throws IOException {
		saveFileData();
		_file.close();
	}
}
