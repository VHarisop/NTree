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

import java.util.Comparator;

/**
 * A simple interface that must be implemented by comparators used
 * from {@link NTree} objects to compare nodes.
 *
 * @author VHarisop
 */
public interface NodeComparator<T> extends Comparator<Node<T>> {
	/**
	 * Computes the distance between objA and objB using an arbitrary
	 * distance metric.
	 *
	 * @param objA the first object
	 * @param objB the second object
	 * @return a double containing the distance of the two objects
	 */
	public double getDistance(Node<T> objA, Node<T> objB);
}
