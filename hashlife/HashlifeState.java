package hashlife;

public class HashlifeState {
    private Node state;
	
	HashlifeState(Node state) {
		this.state = state.simplify();
	}
	
	HashlifeState(int[][] array) {
		this.state = Memoization.fromArray(array);
	}
	
	HashlifeState(HashlifeState other) {
		this.state = other.getState();
	}

	public HashlifeState copy() {
		return new HashlifeState(this);
	}
	
	public int getCellAt(int x, int y) {
		return this.state.getCell(x + this.state.getSize()/2, y + this.state.getSize()/2);
	}
	
	public void setCellAt(int x, int y, int newState) {
		this.state = this.state.setCell(x + this.state.getSize()/2, y + this.state.getSize()/2, newState);
	}

	public int[][] toArray() {
		return state.toArray();
	}
	
	void evolve(int steps) {
		int s = 32 - Integer.numberOfLeadingZeros(steps);
		int n = 1<<s;
		
		//Make sure we can go as far in the futur as we want
		for(int i = 0; i<=s; i++)
		this.state = this.state.centre().centre();
		//We are using a binary decomposition as state.result(s) works with powers of two
		while(n > 0) {
			if((steps&n) != 0){
				this.state = this.state.result(s).centre();
			}
			n /= 2;
			s--;
		}
		
		//Delete unnecessary borders introduced by borderize()
		this.state = this.state.simplify();
	}

	public Node getState(){
		return this.state;
	}
}
