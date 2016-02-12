/* This file is part of NTree.
 *
 * NTree is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NTree is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NTree.  If not, see <http://www.gnu.org/licenses/>. */

package gr.demokritos.ntree;

import java.util.List;
import java.util.ArrayList;

/**
 * A java class that implements an N-ary tree with generic nodes.
 * 
 * TODO: 
 * 1) enforce unique objects (what happens if an item is inserted twice?)
 * 2) enrich API with K-Nearest Neighbour methods.
 */
public class NTree<T> {
	/**
	 * The {@link NodeComparator} used internally by the n-ary tree's nodes.
	 */
	final NodeComparator<T> nodeComp;

	/**
	 * The n-ary tree's branching factor (N).
	 */
	final int branching;

	/**
	 * The default branching factor to be used in absence
	 * of a user-provided value.
	 */
	private static int DEFAULT_BRANCHING = 8;

	/**
	 * A variable holding the number of leaf nodes in the tree.
	 */
	private int size;

	/**
	 * The tree's root node.
	 */
	protected Node<T> root;

	/**
	 * Builds a new tree from a specified root and sets the tree's node
	 * comparator to be the same as the one used in the root node.
	 *
	 * @param root the root node of the new tree
	 */
	public NTree(Node<T> root) {
		this.root = root;
		this.size = 1;
		nodeComp = root.nodeComp;
		branching = root.branching;
	}

	/**
	 * Builds a new tree given the root node's data, using a specified
	 * {@link NodeComparator} and the default branching factor.
	 *
	 * @param rootData the data of the root node
	 * @param ndComp a user-specified node comparator
	 */
	public NTree(T rootData, NodeComparator<T> ndComp) {
		this.root = new Node<T>(rootData, ndComp, DEFAULT_BRANCHING);
		this.size = 1;
		this.nodeComp = ndComp;
		this.branching = DEFAULT_BRANCHING;
	}

	/**
	 * Builds a new tree given the root node's data, using a specified
	 * {@link NodeComparator} and a user-provided branching factor.
	 *
	 * @param rootData the data of the root node
	 * @param ndComp a user-specified node comparator
	 * @param n the branching factor of the tree
	 */
	public NTree(T rootData, NodeComparator<T> ndComp, int n) {
		this.root = new Node<T>(rootData, ndComp, n);
		this.size = 1;
		this.nodeComp = ndComp;
		this.branching = n;
	}

	/**
	 * Builds a new tree that is initially empty, using a specified
	 * {@link NodeComparator} and the default branching factor.
	 *
	 * @param ndComp a user-specified node comparator
	 */
	public NTree(NodeComparator<T> ndComp) {
		this.root = null;
		this.size = 0;
		this.nodeComp = ndComp;
		this.branching = DEFAULT_BRANCHING;
	}
	
	/**
	 * Builds a new tree that is initially empty, using a specified
	 * {@link NodeComparator} and a specified branching factor.
	 *
	 * @param ndComp a user-specified node comparator
	 * @param n the branching factor of the tree
	 */
	public NTree(NodeComparator<T> ndComp, int n) {
		this.root = null;
		this.size = 0;
		this.nodeComp = ndComp;
		this.branching = n;
	}

	/**
	 * Checks if the tree is empty.
	 *
	 * @return a boolean indicating if the tree is empty or not.
	 */
	public boolean isEmpty() {
		return (root == null);
	}

	/**
	 * Gets the size of the tree, which is assumed to be the number
	 * of leaf nodes it contains.
	 *
	 * @return an integer containing the tree's size
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks if the tree contains a specified node.
	 *
	 * @param key the node to search for
	 * @return a boolean indicating if the tree contains that node
	 */
	protected boolean containsNode(Node<T> key) {
		return root.hasChild(key);
	}

	/**
	 * Checks if the tree contains a specified data item
	 * in one of its nodes.
	 *
	 * @param key the item to search for
	 * @return a boolean indicating if the tree contains that item
	 */
	public boolean containsItem(T key) {
		return containsNode(new Node<T>(key, nodeComp, branching));
	}

	/**
	 * Adds a new node to the tree. If the tree is empty, the node
	 * becomes the tree's root. This method returns a boolean value
	 * that is true if an addition was performed and false in the
	 * case that a node with the same key already existed in the
	 * tree. If an addition is performed successfully, it also
	 * updates the tree size.
	 *
	 * @param toAdd the new node to add in the tree.
	 * @return a boolean value indicating whether an addition was
	 * performed or not.
	 */
	protected boolean addNode(Node<T> toAdd) {
		boolean res = false;
		if (root == null) {
			this.root = toAdd;
			size++;
			return true;
		}
		else {
			res = root.addChild(toAdd);
			if (res)
				size++;
			return res;
		}
	}

	/**
	 * Adds a new data item to the tree by creating a node and then
	 * adding the node to it.
	 * @see Node#addChild(T)
	 *
	 * @param toAdd the data item to add in the tree.
	 * @return a boolean indicating whether a node was added or not
	 */
	public boolean addData(T toAdd) {
		Node<T> _toAdd = new Node<T>(toAdd, nodeComp, branching);
		return root.addChild(_toAdd);
	}

	/**
	 * Looks for the node in the tree whose data is the most similar
	 * to a specified query item. 
	 *
	 * @param query the reference data
	 * @return the data from the node in the tree that exhibits
	 * the highest similarity to the query
	 */
	public T getNearestNeighbour(T query) {
		if (isEmpty())
			return null;
		Node<T> toSeek = new Node<T>(query, nodeComp, branching);
		Node<T> result = root.getMostSimilarChild(toSeek);
		return result.getKey();
	}

	/**
	 * Traverses the tree in arbitrary order.
	 *
	 * @return a list containing the tree's items
	 */
	public List<T> traverse() {
		ArrayList<T> results = new ArrayList<T>();
		/* run traverse in child to populate list */
		root.traverse(results);
		return results;
	}

	/**
	 * Static function to quickly assess this class's performance.
	 */
	public static void main(String[] args) {
		NodeComparator<Integer> nComp = new NodeComparator<Integer>() {
			@Override
			public int compare(Node<Integer> objA, Node<Integer> objB) {
				return (objA.getKey() - objB.getKey());
			}

			public double getDistance(Node<Integer> objA, Node<Integer> objB) {
				return Math.abs(objA.getKey() - objB.getKey());
			}
		};

		// create a new node with branching factor 4
		NTree<Integer> nTree = new NTree<Integer>(10, nComp, 4);

		nTree.addData(2); nTree.addData(5);
		nTree.addData(9); nTree.addData(6); 
		nTree.addData(15); nTree.addData(10);

		System.out.println(nTree.containsItem(5));
		System.out.println(nTree.containsItem(12));
		System.out.println(nTree.containsItem(6));
		System.out.println(nTree.getNearestNeighbour(7));
	}
}
