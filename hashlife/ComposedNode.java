package hashlife;

public class ComposedNode extends Node{

    private Node[] quad;
    private Node result[];

    public ComposedNode(Node ... quad){
        super(
            quad[0].getDimension()+1, 
            quad[0].isOff() && 
            quad[1].isOff() && 
            quad[2].isOff() && 
            quad[3].isOff(), 
            calculateDensity(quad));
        
        this.quad = quad.clone();
        this.result = new Node[this.getDimension()-1];
    }
    
    private static int calculateDensity(Node ... quad){
        int density = 0;
		for(int i=0; i<4; i++) {
			density += quad[i].getDensity();
		}
		return density/4;
    }

    public boolean equals(Object o) {
		if(!(o instanceof ComposedNode))
			return false;
        ComposedNode c = (ComposedNode) o;
		return 	this.quad[0] == c.getQuad()[0] &&
                this.quad[1] == c.getQuad()[1] &&
                this.quad[2] == c.getQuad()[2] &&
                this.quad[3] == c.getQuad()[3];
	}

    public int hashCode() {
		int hashCode = 1;
		  for(int i=0; i<4; i++)
		      hashCode = 31*hashCode + System.identityHashCode(this.quad[i]);
		return hashCode;
	}

    @Override
	public Node getQuad(int numquad) {
		return this.quad[numquad];
	}
	
	public void calculateResult(int numSteps) {
		Node nineNodes[][] = new Node[3][3];
		for(int i=0; i<4 ; i++) {
			nineNodes[(i/2)*2][(i%2)*2] = this.quad[i];
		}

		nineNodes[0][1] = Memoization.get(this.quad[0].getQuad(1), this.quad[1].getQuad(0), this.quad[0].getQuad(3), this.quad[1].getQuad(2));
		nineNodes[1][0] = Memoization.get(this.quad[0].getQuad(2), this.quad[0].getQuad(3), this.quad[2].getQuad(0), this.quad[2].getQuad(1));
		nineNodes[1][2] = Memoization.get(this.quad[1].getQuad(2), this.quad[1].getQuad(3), this.quad[3].getQuad(0), this.quad[3].getQuad(1));
		nineNodes[2][1] = Memoization.get(this.quad[2].getQuad(1), this.quad[3].getQuad(0), this.quad[2].getQuad(3), this.quad[3].getQuad(2));
		nineNodes[1][1] = Memoization.get(this.quad[0].getQuad(3), this.quad[1].getQuad(2), this.quad[2].getQuad(1), this.quad[3].getQuad(0));
		
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++) {
				nineNodes[i][j] = nineNodes[i][j].result((numSteps == this.getDimension() - 2)?(numSteps-1):numSteps);
			}
			
		Node fourNodes[] = new Node[4];
		for(int i=0; i<2; i++)
			for(int j=0; j<2; j++) {
				int idx = i*2+j;
				fourNodes[idx] = Memoization.get(nineNodes[i][j], nineNodes[i][j+1], nineNodes[i+1][j], nineNodes[i+1][j+1]);
				if(numSteps == this.getDimension()-2)
                fourNodes[idx] = fourNodes[idx].result();
				else {
					Node tmp[] = new Node[4];
					for(int k=0; k<4; k++)
						tmp[k] = fourNodes[idx].getQuad(k).getQuad(3-k);
                        fourNodes[idx] = Memoization.get(tmp);
				}
			}
		
		result[numSteps] = Memoization.get(fourNodes);
	}

    @Override
	Node result(int numSteps) {
		if(numSteps > this.getDimension() - 2)
			throw new RuntimeException("Can't compute the result at time 2^"+numSteps+" of a Node of dim " + this.getDimension());
		if(numSteps < 0)
			return this;
		if(result[numSteps] == null)
			calculateResult(numSteps);
		return result[numSteps];
	}

	@Override
	public void fillArray(int[][] array, int x, int y) {
		if(this.isOff()){
            for(int k=0; k<this.getSize(); k++){
                for(int l=0; l<this.getSize(); l++){
                    array[x+k][y+l] = 0;
                }
            }
        }else{
            for(int k=0; k<4; k++){
                this.quad[k].fillArray(array, x+(k/2)*(this.getSize()/2), y+(k%2)*(this.getSize()/2));
            }
        }
	}

	@Override
	Node simplify() {
		if(this.isOff())
			return Memoization.empty(1);
		
		if(this.getDimension() == 1)
			return this;
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				if(j != 3-i && !this.quad[i].getQuad(j).isOff())
					return this;
			}
        }

        Node[] newQuad = new Node[4];
		for(int i=0; i<4; i++){
			newQuad[i] = this.quad[i].getQuad(3-i);
        }
		return Memoization.get(newQuad).simplify();
	}

	@Override
	Node centre() {
		if(this.isOff())
			return Memoization.empty(this.getDimension()+1);
            
		Node n = Memoization.empty(this.getDimension()-1);
		return Memoization.get(
				Memoization.get(n,n,n,this.quad[0]),
				Memoization.get(n,n,this.quad[1],n),
				Memoization.get(n,this.quad[2],n,n),
				Memoization.get(this.quad[3],n,n,n));
	}

	@Override
	int getCell(int x, int y) {
		if(x < 0 || y < 0 || x >= this.getSize() || y >= this.getSize())
			return 0;
		int halfSize = this.getSize()/2;
		int i = x/halfSize, j = y/halfSize;
		return this.quad[2*i+j].getCell(x - i*halfSize, y - j*halfSize);
	}

	@Override
	Node setCell(int x, int y, int state) {
		int halfSize = this.getSize()/2;
		if(x < 0 || y < 0 || x >= this.getSize() || y >= this.getSize())
			return centre().setCell(x + halfSize,  y + halfSize, state);
		int i = x/halfSize, j = y/halfSize;
		
		Node[] tmp = new Node[4];
		for(int k=0; k<4; k++)
			tmp[k] = this.quad[k];
		tmp[2*i+j] = tmp[2*i+j].setCell(x - i*halfSize, y - j*halfSize, state);
		return Memoization.get(tmp);
	}

    public Node[] getQuad(){
        return this.quad;
    }

	public Node[] getResult(){
		return this.result;
	}
}
