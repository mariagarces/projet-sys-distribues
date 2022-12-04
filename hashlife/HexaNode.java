package hashlife;

public class HexaNode extends ComposedNode{

    private FourNode[] quad = new FourNode[4];
    
    HexaNode(Node ... quad) {
		super(quad);
		for(int i=0; i<4; i++)
			this.quad[i] = (FourNode) quad[i];
	}

    @Override
	public void calculateResult(int s) {
		assert(s == 0);
		int[] count = new int[4];
		
		count[0] = 	quad[0].quad[0] 	+ quad[0].quad[1] 	+ quad[1].quad[0] +
					quad[0].quad[2] 						+ quad[1].quad[2] + 
					quad[2].quad[0] 	+ quad[2].quad[1] 	+ quad[3].quad[0];
		count[1] = 	quad[0].quad[1] 	+ quad[1].quad[0] 	+ quad[1].quad[1] + 
					quad[0].quad[3] 						+ quad[1].quad[3] + 
					quad[2].quad[1] 	+ quad[3].quad[0] 	+ quad[3].quad[1];
		count[2] = 	quad[0].quad[2] 	+ quad[0].quad[3] 	+ quad[1].quad[2] + 
					quad[2].quad[0] 						+ quad[3].quad[0] + 
					quad[2].quad[2] 	+ quad[2].quad[3] 	+ quad[3].quad[2];
		count[3] = 	quad[0].quad[3] 	+ quad[1].quad[2] 	+ quad[1].quad[3] + 
					quad[2].quad[1] 						+ quad[3].quad[1] + 
					quad[2].quad[3] 	+ quad[3].quad[2] 	+ quad[3].quad[3];
		
		LeafNode[] newQuad = new LeafNode[4];
		for(int i=0; i<4; i++) {
			if(count[i] < 2 || count[i] > 3)
				newQuad[i] = LeafNode.off;
			else if(count[i] == 3)
				newQuad[i] = LeafNode.on;
			else
				newQuad[i] = quad[i].getQuad(3-i);
		}
		this.getResult()[0] = Memoization.get(newQuad);
	}

	@Override
	public FourNode getQuad(int i) {
		return quad[i];
	}
    
}
