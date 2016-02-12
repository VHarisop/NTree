package gr.demokritos.ntree;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.List;

/**
 * Unit test for the NTree class.
 */
public class NaryTreeTest 
	extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public NaryTreeTest( String testName )
	{
		super( testName );
	}
	
	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite( NaryTreeTest.class );
	}

	/**
	 * Auxiliary function that returns false if any integer from a set of
	 * values is found more than once in the traversal list of an NTree.
	 */
	private static boolean uniqueHits(List<Integer> intList, int ... vals) {
		for (int i: vals) {
			int count = 0;
			for (int k: intList) {
				if (i == k) { ++count; }
			}
			/* if any val was not found or was found more than once
			 * return false */
			if (count != 1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Test that retrieval and nearest neighbour work for
	 * {@link NTree} as expected.
	 */
	public void testIntegerTree() {
		NodeComparator<Integer> nComp = new NodeComparator<Integer>() {
			@Override
			public int compare(Node<Integer> objA, Node<Integer> objB) {
				return (objA.getKey() - objB.getKey());
			}

			public double getDistance(
					Node<Integer> objA,
					Node<Integer> objB) 
			{
				return Math.abs(objA.getKey() - objB.getKey());
			}
		};

		// create a tree with branching factor = 4
		NTree<Integer> nTree = new NTree<Integer>(10, nComp, 4);

		assertTrue(nTree.addData(2));
		assertTrue(nTree.addData(5)); 
		assertTrue(nTree.addData(9));

		nTree.addData(6); nTree.addData(12); nTree.addData(15);

		// assert that additions of nodes with keys that already
		// exist do not add new items to the tree
		assertFalse(nTree.addData(9));
		assertFalse(nTree.addData(12));
		assertFalse(nTree.addData(10));

		// assert containments
		assertTrue(nTree.containsItem(9));
		assertTrue(nTree.containsItem(5));
		assertTrue(nTree.containsItem(15));
		assertTrue(nTree.containsItem(12));
		assertTrue(nTree.containsItem(10));

		// assert non-containments
		assertFalse(nTree.containsItem(4));
		assertFalse(nTree.containsItem(0));
		assertFalse(nTree.containsItem(1));
		assertFalse(nTree.containsItem(11));

		// assert nearest neighbour
		assertEquals(6, (int)nTree.getNearestNeighbour(7));
		assertEquals(15, (int)nTree.getNearestNeighbour(16));
		assertEquals(2, (int)nTree.getNearestNeighbour(0));

		// make sure that contained nodes' keys are unique
		assertTrue(uniqueHits(nTree.traverse(), 5, 9, 2, 10, 15));
	}
}
