package hashlife;

import java.util.ArrayList;
import java.util.HashMap;

public class Memoization {
    static private ArrayList<Node> empty = new ArrayList<Node>();
	static private HashMap<Node, Node> built = new HashMap<Node, Node>();

    static Node get(Node ... quad) {
		assert(quad.length == 4 && quad[0].getDimension() == quad[1].getDimension() && quad[1].getDimension() == quad[2].getDimension() && quad[2].getDimension() == quad[3].getDimension());
		Node m;
		if(quad[0].getDimension() == 0)
			m = new FourNode(quad);
		else if(quad[0].getDimension() == 1)
			m = new HexaNode(quad);
		else
			m = new ComposedNode(quad);
		if(built.containsKey(m))
			m = built.get(m);
		else
			built.put(m, m);
		
		return m;
	}

	static Node empty(int dimension) {
		if(dimension < 0)
			throw new RuntimeException("The dimension of an empty MacroCell must be at least 0.");

		empty.ensureCapacity(dimension+1);
		if(empty.isEmpty())
			empty.add(LeafNode.off);
		
		int todo = dimension - empty.size() + 1;
		while(todo-- > 0) {
			Node e = empty.get(dimension - todo - 1);
			empty.add(get(e, e, e, e));
		}
		return empty.get(dimension);
	}

	static Node fromArray(int[][] array) {
		if(array == null || array.length == 0)
			return empty(1);
		int h = array.length, w = array[0].length, dim;
		if(h < w)
			dim = 32 - Integer.numberOfLeadingZeros(w);
		else
			dim = 32 - Integer.numberOfLeadingZeros(h);
		if(dim < 1)
			dim = 1;
		return fromTab(array, 0, 0, dim);
	}

	static private Node fromTab(int[][] array, int i, int j, int dimension) {
		if(dimension == 0) {
			if(i >= array.length || j >= array[i].length || array[i][j] == 0)
				return LeafNode.off;
			return LeafNode.on;
		}
		int offset = 1 << (dimension-1);
		return get(	fromTab(array, i, j, dimension-1),
					fromTab(array, i, j + offset, dimension-1),
					fromTab(array, i + offset, j, dimension-1),
					fromTab(array, i + offset, j + offset, dimension-1));
	}
}
