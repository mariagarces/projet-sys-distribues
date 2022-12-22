package parallel;

import java.rmi.Remote;

public interface IHashLifeServer extends Remote{
    public int[] getMatrixAssignment(int row)
        throws java.rmi.RemoteException;
    public void setMatrix(int[][] matrix)
        throws java.rmi.RemoteException;
    public int getMatrixSize()
        throws java.rmi.RemoteException;
    public int getSubMatrixSize()
        throws java.rmi.RemoteException;
    public int[][] printEachState(int steps)
        throws java.rmi.RemoteException;
    public void calculateNextGen(GenerationMessage msg) throws java.rmi.RemoteException;
}
