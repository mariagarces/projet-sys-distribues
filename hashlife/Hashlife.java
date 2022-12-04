package hashlife;

public class Hashlife {
    private HashlifeState state;
	
	public void setState(HashlifeState state) {
        this.state = state;
	}

	public HashlifeState getState() {
		return state.copy();
	}

    //TODO draw grid
	// public LifeDrawer getDrawer() {
	// 	return new HashLifeDrawer();
	// }

	public void loadFromArray(int[][] array) {
		this.state = new HashlifeState(array);
	}

	public int[][] saveToArray() {
		return this.state.toArray();
	}

	public int getCellAt(int x, int y) {
		return this.state.getCellAt(x, y);
	}

	public void setCellAt(int x, int y, int status) {
		this.state.setCellAt(x, y, status);
	}

	public int toggleCellAt(int x, int y) {
		int s = this.state.getCellAt(x, y);
		this.state.setCellAt(x, y, 1 - s);
		return s;
	}

	public void evolve(int steps) {
		state.evolve(steps);
	}

	public void printState(int steps){
		int[][] array = this.state.getState().toArray();

		for(int k=0; k<steps; k++){
			this.evolve(k);
			array = this.state.getState().toArray();

			for (int i=0; i<array.length; i++){
				for(int j=0; j<array[i].length; j++){
					System.out.print(array[i][j]);
				}
				System.out.print("\n");
			}
			System.out.print("\n");
		}
	}
}
