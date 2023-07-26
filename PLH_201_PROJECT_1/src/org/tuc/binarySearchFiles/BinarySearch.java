package org.tuc.binarySearchFiles;

import java.io.IOException;

import org.tuc.plh201.FileManager;

public class BinarySearch {
	
	public static void doBinarySearchKeys(int leftIndex, int rightIndex, int key, FileManager fm) throws IOException {
		int found = 0;
		
		if (rightIndex >= leftIndex) { 
            int mid = leftIndex + (rightIndex - leftIndex) / 2; 
            
            // Reading the page into buffer
            fm.readBlock(mid);
            
            if(fm.getPointer()) found = fm.searchForKeyInBufferForPointerFile(key);
            else found = fm.searchForKeyInBuffer(key);
            
            if(found==1) {
            	System.out.println("Key " + key + " found");
            	return;
            }
            
            if(fm.isKeySmaller(key))
            	doBinarySearchKeys(leftIndex, mid-1, key, fm);
            else 
            	doBinarySearchKeys(mid+1, rightIndex, key, fm);
        } 
	}
}
