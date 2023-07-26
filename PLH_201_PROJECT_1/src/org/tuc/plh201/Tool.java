package org.tuc.plh201;

import java.io.IOException;
import java.util.Scanner;

import org.tuc.binarySearchFiles.BinarySearch;
import org.tuc.node.RandomNodeGenerator;
import org.tuc.node.SortedNodeGenerator;
import org.tuc.types.FMType;

public class Tool {
	private static Tool _instance = null;
	private FileManager _fm;
	private FileManager _fmPointer;
	private FileManager _fmSorted;
	private FileManager _fmSortedPointer;
	private static Scanner _scan = new Scanner(System.in);
	
	public Tool() {
		RandomNodeGenerator.getInstance();
		_fm = new FileManager(false, FMType.Random);
		_fmPointer = new FileManager(true, FMType.Random);
		_fmSorted = new FileManager(false, FMType.Sorted);
		_fmSortedPointer = new FileManager(true, FMType.Sorted);
	}
	
	public FileManager getFileManager(boolean pointer, FMType type) {
		if(pointer) return type == FMType.Random ? _fmPointer : _fmSortedPointer;
		else  return type == FMType.Random ? _fm : _fmSorted;
	}
	public FileManager getFileManager(boolean pointer) {
		return pointer ? _fmPointer : _fm;
	}
	public FileManager getFileManager() {
		return _fm;
	}
	
	public void printMenu() {
		System.out.println("Enter your choice");
		System.out.println("------------------------------------------------------------------");
		System.out.println("Create and fill 4 new files TypeA TypeB TypeC TypeD           -> 1");
		System.out.println("Open existing files                                           -> 2");
		System.out.println("Print File names                                              -> 3");
		System.out.println("Print number of pages                                         -> 4");
		System.out.println("Print position                                                -> 5");
		System.out.println("Search for 20 keys in random files                            -> 6");
		System.out.println("Search for 20 keys with binary search in sorted files         -> 7");
		System.out.println("Close all files                                               -> 8");
		System.out.println("Exit                                                          -> 9");
		System.out.println("------------------------------------------------------------------");
	}
	
	public void init() throws IOException {
		int choice=0;
//		String name;
		while(choice!=9) {
			Tool.getInstance().printMenu();
			choice = _scan.nextInt();
			switch (choice) {
			case 1 :
				_fm.createFile("TypeA");
				_fmPointer.createFile("TypeB");
				
				RandomNodeGenerator.getInstance().resetCoutner();
				_fmPointer.fillFilePages();

				RandomNodeGenerator.getInstance().resetCoutner();
				_fm.fillFilePages();
				
				System.out.println("Creating sorted files: ");
				SortedNodeGenerator.getInstance().resetCoutner();
				_fm.sortFile();
				_fmSorted.createFile("TypeC");
				_fmSorted.fillFilePages();
				
				SortedNodeGenerator.getInstance().clearNodeList();
				SortedNodeGenerator.getInstance().resetCoutner();
				_fmPointer.sortFile();
				_fmSortedPointer.createFile("TypeD");
				_fmSortedPointer.fillFilePages();
				break;
			case 2:
				_fm.openFile("TypeA");
				_fmPointer.openFile("TypeB");
				_fmSorted.openFile("TypeC");
				_fmSortedPointer.openFile("TypeB");
				
//				System.out.println("Enter File name: ");
//				name = _scan.next();
//				FileManager temp = new FileManager();
//				temp.openFile(name);
//				
//				if(temp.getPointer() && temp.getType() == FMType.Random)
//					_fmPointer = temp;
//				else if (!temp.getPointer() && temp.getType() == FMType.Random)
//					_fm = temp;
//				else if (!temp.getPointer() && temp.getType() == FMType.Sorted)
//					_fmSorted = temp;
//				else _fmSortedPointer = temp;
				break;
			case 3 :
				if(!_fm.getFileName().equals("null") && !_fmSorted.getFileName().equals("null") && 
				!_fmPointer.getFileName().equals("null") && !_fmSortedPointer.getFileName().equals("null")) 
				{
					_fm.printFileName();
					_fmPointer.printFileName();
					_fmSorted.printFileName();
					_fmSortedPointer.printFileName();
				}
				break;
			case 4 : 
				_fm.printFilePages();
				_fmPointer.printFilePages();
				_fmSorted.printFilePages();
				_fmSortedPointer.printFilePages();
				break;
			case 5 : 
				_fm.printPos();
				_fmPointer.printPos();
				_fmSorted.printPos();
				_fmSortedPointer.printPos();
				break;
			case 6 : 
				_fmPointer.clearTimesSearched();
				for(int i=0; i<20; i++) {
					if(_fmPointer.searchForKeyInFile(RandomNodeGenerator.getRandomKeys()[i])==1)
						System.out.println("Key: " + RandomNodeGenerator.getRandomKeys()[i] + "found ");
					
				}
				System.out.println("times searched in pointer file: : " + _fmPointer.getTimesSearched()/20);
				
				_fm.clearTimesSearched();
				for(int i=0; i<20; i++) {
					if(_fm.searchForKeyInFile(RandomNodeGenerator.getRandomKeys()[i])==1)
						System.out.println("Key: " + RandomNodeGenerator.getRandomKeys()[i] + "found ");
					
				}
				System.out.println("times searched data file: " + _fm.getTimesSearched()/20);
				break;
			case 7 : 
				Tool.getInstance().getFileManager(true, FMType.Sorted).clearTimesSearched();
				for(int i=0; i<20; i++) 
					BinarySearch.doBinarySearchKeys(2, 626, RandomNodeGenerator.getRandomKeys()[i], _fmSortedPointer);
				
				System.out.println("times searched in pointer file: : " + _fmSortedPointer.getTimesSearched()/20);
				
				Tool.getInstance().getFileManager(false, FMType.Sorted).clearTimesSearched();
				for(int i=0; i<20; i++) 
					BinarySearch.doBinarySearchKeys(2, 2501, RandomNodeGenerator.getRandomKeys()[i], _fmSorted);
				
				System.out.println("times searched data file: " + _fmSorted.getTimesSearched()/20);
				break;
			case 8 : 
				System.out.println("Closing file: ");
				Tool.getInstance().getFileManager().closeFile();
				Tool.getInstance().getFileManager(true).closeFile();
				Tool.getInstance().getFileManager(false, FMType.Sorted).closeFile();
				Tool.getInstance().getFileManager(true, FMType.Sorted).closeFile();
				break;
			}
		}
		
	}
	
	public static Tool getInstance() {
		return _instance == null ? _instance = new Tool() : _instance;	
	}
}
