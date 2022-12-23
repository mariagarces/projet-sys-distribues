package hashlife;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import java.lang.Math;

public class Hashlife {
    private HashlifeState state;
	
	public void setState(HashlifeState state) {
        this.state = state;
	}

	public HashlifeState getState() {
		return state.copy();
	}

	public List<int[][]> divideArrayByRows(int d, int w, int[][] array){
		int[][] matrix;
		List<int[][]> matrices = new ArrayList<>();

		int h = 1;
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

	public List<int[]> divideArrayByRow(int d, int w, int[][] array){
		int[] finalArray = new int[3];
		List<int[]> arrays = new ArrayList<>();

		Arrays.fill(finalArray, 0);

		for(int i=0; i<array[0].length; i++) {
			for(int j=0; j<finalArray.length; j++) {
				finalArray[j] = array[i][j];
			}
			arrays.add(finalArray);
		}
		
		return arrays;
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

	public int[][] printEachState(int steps){
		int[][] array = this.state.getState().toArray();
		this.evolve(steps);
		array = this.state.getState().toArray();
		
		return array;
	}

	public int[][] fixGrid(int height, int width, int[][] array){
		int[][] newArray = new int[height][width];

		for (int[] row: newArray)
    		Arrays.fill(row, 0);

		for(int row=0; row<array.length && row<newArray.length; row++){
			for(int col=0; col<array[row].length && col<newArray[row].length; col++){
				newArray[row][col] = array[row][col];
			}
		}

		return newArray;
	}
}
