package hashlife;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

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

	public List<int[][]> divideArrayByRows(int d, int h, int w, int[][] array){
		int[][] matrix;
		List<int[][]> matrices = new ArrayList<>();

		int hSize = (int)Math.round((float)h/(float)d);
		int initP=0;
		
		if(d<=h){
			for(int i=0; i<d; i++){
				matrix = new int[hSize][w];
	
				for (int[] row: matrix)
					Arrays.fill(row, 0);
	
				for (int k=0; k<hSize; k++){
					for(int j=0; j<array[k].length; j++){
						if(k+initP < array.length)
							matrix[k][j] = array[k+initP][j];
					}
				}
	
				initP += hSize;
	
				matrices.add(matrix);
			}
		}
		
		return matrices;
	}

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

	public int[][] printEachState(int steps){
		int[][] array = this.state.getState().toArray();
		this.evolve(steps);
		array = this.state.getState().toArray();

		// for(int k=0; k<steps; k++){
		// 	this.evolve(k);
		// 	array = this.state.getState().toArray();

		// 	for (int i=0; i<array.length; i++){
		// 		for(int j=0; j<array[i].length; j++){
		// 			System.out.print(array[i][j]);
		// 		}
		// 		System.out.print("\n");
		// 	}
		// 	System.out.print("\n");
		// }
		return array;
	}

	public int[][] fixGrid(int height, int width, int[][] array){
		int[][] newArray = new int[height][width];

		for (int[] row: newArray)
    		Arrays.fill(row, 0);

		for(int row=0; row<array.length && row<newArray.length; row++){
			for(int col=0; col<array[row].length; col++){
				newArray[row][col] = array[row][col];
			}
		}

		return newArray;
	}

	public void draw(int x, int y, HashlifeState state) {
		Node cell = ((HashlifeState) state).getState();
		int realSize = cell.getSize();
		
		x -= realSize/2;
		y -= realSize/2;

		recDraw(x, y, cell);
	}

	private void recDraw(int x, int y, Node cellToDraw) {
		//Compute the screenspace size of the cell
		int size = cellToDraw.getSize();
		int realSize = size;
		
		//do not draw cells outside of the screen
		int w = 3, h = 3;
		if(x + realSize <= 0 || x >= w || y + realSize <= 0 || y >= h){
			return;
		}

		if(cellToDraw.isOff()) {
			return;
		}else if(cellToDraw.getDimension() == 0){
			System.out.println('1'+" "+x+" "+y);
			return;
		}
		
		// //fill a rectangle for BooleanCells
		// if(cellToDraw.dim == 0){
		// 	g.setColor(Color.white);
		// 	g.fillRect(x, y, 1<<zoom, 1<<zoom);
		// 	return;
		// }
		
		// //do draw a pixel for non-empty 1-pixel large cells
		// if(cellToDraw.dim <= -zoom) {
		// 	int color = 255;
		// 	int rgb = (new Color(color, color, color).getRGB());
		// 	image.setRGB(x, y, rgb);
		// 	return;
		// }


		
		//Make the recursive call
		int offset = realSize/2;
		recDraw(x         , y         , cellToDraw.getQuad(0));
		recDraw(x + offset, y         , cellToDraw.getQuad(1));
		recDraw(x         , y + offset, cellToDraw.getQuad(2));
		recDraw(x + offset, y + offset, cellToDraw.getQuad(3));

	}
}
