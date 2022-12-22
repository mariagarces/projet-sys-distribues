package parallel;

import java.rmi.Remote;

public interface InterfaceTest extends Remote{
    public int[][] divideArrayByRows(int d, int h, int w, int[][] array, int index)
        throws java.rmi.RemoteException;
    public void setMatrix(int[][] matrix)
        throws java.rmi.RemoteException;
    public int[][] getMatrix()
        throws java.rmi.RemoteException;
    public int[][] printEachState(int steps)
        throws java.rmi.RemoteException;
}
