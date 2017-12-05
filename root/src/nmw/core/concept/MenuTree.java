package mg.util;

import java.util.ArrayList;
import java.util.List;

public class MenuTree {
	private List<MenuTree> elements = null;
	private String nodeName;

	public MenuTree(String nodeName) {
		this.elements = new ArrayList<MenuTree>();
		this.nodeName = nodeName;
	}

	public void addLeaf(MenuTree leaf) {
		elements.add(leaf);
	}

	public List<MenuTree> getElements() {

		return elements;
	}

	public String getNodeName() {

		return nodeName;
	}

	@Override
	public String toString() {
		return "" + nodeName + "{" + elements + "}";
	}
}
