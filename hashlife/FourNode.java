package hashlife;

public class FourNode extends ComposedNode {
    int[] quad = new int[4];

    FourNode(Node ... quad){
        super(quad);
        for(int i = 0; i<4; i++){
            this.quad[i] = ((LeafNode) quad[i]).getValue();
        }
    }

    @Override
	public LeafNode getQuad(int numquad) {
		return (quad[numquad] == 0) ? LeafNode.off : LeafNode.on;
	}

	@Override
	public Node result(int s) {
		throw new RuntimeException("Can't compute the result of a FourNode");
	}
}
