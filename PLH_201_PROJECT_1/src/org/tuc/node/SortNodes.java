package org.tuc.node;

import java.util.ArrayList;
import java.util.Collections;

public class SortNodes {
	public static SortNodes _instance = null;
	
	private SortNodes() {}
	
	public void sortNodes(ArrayList<Node> nodes) {
		Collections.sort(nodes, (a, b)->{
			return a.getKey()>b.getKey()?1:-1;
		});
	}

	public static SortNodes getInstance() {
		return _instance == null ? _instance = new SortNodes() : _instance;	
	}
}
