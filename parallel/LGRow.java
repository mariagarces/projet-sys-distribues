package parallel;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class LGRow extends UnicastRemoteObject implements ILGRow, Runnable{
    private int[] currRow;
    private int rowID;
    private Registry reg;
    IHashLifeServer server;
    long startTime;

    public LGRow(int rowID, int[] currRow, Registry reg) throws RemoteException, NotBoundException{
        super();
        this.currRow = currRow;
        this.rowID = rowID;
        this.reg = reg;
        server = (IHashLifeServer) reg.lookup("positionsMatrix");
        this.currRow = server.getMatrixAssignment(rowID);
        startTime = System.nanoTime();
    }

    @Override
    public int getRowID() throws RemoteException {
        return this.rowID;
    }

    @Override
    public int[] getCurrRow() throws RemoteException {
        return this.currRow;
    }

    @Override
    public int[] getRowInformation() throws RemoteException {
        return currRow;
    }

    @Override
    public void setNewGeneration(GenerationMessage msg) throws RemoteException {
        int index = 1, height = 3, width = 3;
        if(this.rowID == 0 || server.getMatrixSize()-1 == this.rowID){
            index = 0;
            height = 2;
        }

        int[][] fixedMatrix = fixGrid(height, width, msg.getInfo());
        this.currRow = fixedMatrix[index];
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

    @Override
    public void triggerNextGeneration(){
        try {
        int [][] matrix = new int[2][3];
        ILGRow downRow, upRow;

        if(this.rowID == 0){
            downRow = (ILGRow) reg.lookup("row"+(this.rowID+1));
            matrix = copyMatrix(0, matrix, this.currRow.clone());
            matrix = copyMatrix(1, matrix, downRow.getCurrRow());
        } else if(server.getMatrixSize()-1 == this.rowID) {
            upRow = (ILGRow) reg.lookup("row"+(this.rowID-1));
            matrix = copyMatrix(0, matrix, upRow.getCurrRow());
            matrix = copyMatrix(1, matrix, this.currRow.clone());
        } else {
            matrix = new int[3][3];
            upRow = (ILGRow) reg.lookup("row"+(this.rowID-1));
            downRow = (ILGRow) reg.lookup("row"+(this.rowID+1));
            matrix = copyMatrix(0, matrix, upRow.getCurrRow());
            matrix = copyMatrix(1, matrix, this.currRow.clone());
            matrix = copyMatrix(2, matrix, downRow.getCurrRow());
        }

        GenerationMessage gMessage = new GenerationMessage(matrix, this.rowID);
        server.calculateNextGen(gMessage);

        } catch (Exception e) {
            System.err.println("Erreur :" + e);
        }
    }

    public int[][] copyMatrix(int d, int[][] resultMatrix, int[] matrix){
        for(int i=0; i<matrix.length; i++){
            resultMatrix[d][i] = matrix[i];
        }

        return resultMatrix;
    }

    public void run()
    {
        try {
        System.out.println("ID: " + getRowID());
        triggerNextGeneration();

        } catch (Exception e) {
            System.err.println("Erreur :" + e);
        }
    }

}
