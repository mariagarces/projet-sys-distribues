package parallel;

import java.rmi.server.*;
import java.rmi.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import hashlife.Hashlife;

public class HashLifeServer extends UnicastRemoteObject implements IHashLifeServer{
    private int[][] matrix;
    private Hashlife a;
    private List<GenerationMessage> msgs;
    private int n;

    public HashLifeServer() throws RemoteException{
        super();
        a = new Hashlife();
        msgs = new ArrayList<>();
    }

    @Override
    public int[] getMatrixAssignment(int row){
		// int[][] matrix;
		// List<int[][]> matrices = new ArrayList<>();

		// int hSize = (int)Math.round((float)h/(float)d);
		// int initP=0;

		// if(d<=h){
		// 	for(int i=0; i<d; i++){
		// 		matrix = new int[hSize][w];
	
		// 		for (int[] row: matrix)
		// 			Arrays.fill(row, 0);
	
		// 		for (int k=0; k<hSize; k++){
		// 			for(int j=0; j<array[k].length; j++){
		// 				if(k+initP < array.length)
		// 					matrix[k][j] = array[k+initP][j];
		// 			}
		// 		}
	
		// 		initP += hSize;
	
		// 		matrices.add(matrix);
		// 	}
		// }
		
		// return matrices.get(index);
        return matrix[row];
	}
    @Override
    public void setMatrix(int[][] matrix){
        this.matrix = matrix;
    }
    @Override
    public int getMatrixSize(){
        return this.matrix.length;
    }
    @Override
    public int getSubMatrixSize(){
        return this.n;
    }

    public Hashlife getHashLife(){
        return this.a;
    }

    @Override
    public int[][] printEachState(int steps){
		return this.a.printEachState(steps);
	}

    @Override
    public void calculateNextGen(GenerationMessage msg) throws RemoteException {
        // When number of elements in the lsit is equal to n, per msg:
        // load from array in hashlife the matrix inside msg.
        // evolve hashlife one step. 
        // Send a new GenMsg to corresponding LGRow.
        //SAve msgs into a different pointer, then replace msgs with a new arraylist.
        //When all rows have been traversed, trigger next gen for each row.
        a.evolve(0);
    }

}
