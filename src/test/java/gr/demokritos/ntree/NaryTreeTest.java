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
	 * Test that retrieval and nearest neighbour work for
	 * {@link NTree} as expected.
	 */
	public void testIntegerTree() {
		NodeComparator<Integer> nComp = new NodeComparator<Integer>() {
			@Override
			public int compare(Node<Integer> objA, Node<Integer> objB) {
				return (objA.getData() - objB.getData());
			}

			public double getDistance(
					Node<Integer> objA,
					Node<Integer> objB) 
			{
				return Math.abs(objA.getData() - objB.getData());
			}
		};

		// create a tree with branching factor = 4
		NTree<Integer> nTree = new NTree<Integer>(10, nComp, 4);

		nTree.addData(2); nTree.addData(5); nTree.addData(9);
		nTree.addData(6); nTree.addData(12); nTree.addData(15);

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
	}
}
