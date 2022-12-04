package hashlife;

public class LeafNode extends Node {
    private int value;
    final static LeafNode on = new LeafNode(1);
    final static LeafNode off = new LeafNode(0);

    public LeafNode(int value){
        super(0, value == 0, value == 0 ? 0 : 255);
        this.value = value == 0 ? 0:1;
    }

    public int getValue(){
        return this.value;
    }

    @Override
	public Node getQuad(int numquad) {
		throw new RuntimeException("Can't get quads of a LeafNode");
	}

	@Override
	public Node result(int numSteps) {
		throw new RuntimeException("Can't compute the result of a LeafNode");
	}

	@Override
	public void fillArray(int[][] array, int x, int y) {
		array[x][y] = this.value;
	}

	@Override
	Node simplify() {
		throw new RuntimeException("Can't simplify a LeafNode");
	}

	@Override
	Node centre() {
		throw new RuntimeException("Can't center a LeafNode");
	}

	@Override
	int getCell(int x, int y) {
		return this.value;
	}

	@Override
	Node setCell(int x, int y, int state) {
		if(state == 0)
			return off;
		return on;
	}
}
