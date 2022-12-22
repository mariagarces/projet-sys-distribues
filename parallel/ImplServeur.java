package parallel;

import java.rmi.server.*;
import java.rmi.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import hashlife.Hashlife;

public class ImplServeur extends UnicastRemoteObject implements InterfaceTest{
    private int[][] matrix;
    private Hashlife a = new Hashlife();

    public ImplServeur() throws RemoteException{super();}

    public int[][] divideArrayByRows(int d, int h, int w, int[][] array, int index){
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
		
		return matrices.get(index);
	}

    public void setMatrix(int[][] matrix){
        this.matrix = matrix;
    }

    public int[][] getMatrix(){
        return this.matrix;
    }

    public Hashlife getHashLife(){
        return this.a;
    }

    public int[][] printEachState(int steps){
		return this.a.printEachState(steps);
	}

    // public static void main(String arg[]){
    //     try{
    //         ImplServeur s = new ImplServeur();
    //         String nom="Impl";
    //         Naming.rebind(nom,s); //enregistrement
    //         System.out.println("Serveur enregistr");
    //     }
    //     catch (Exception e){
    //         System.err.println("Erreur :"+e);
    //     }
    // }
}
