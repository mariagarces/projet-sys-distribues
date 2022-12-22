package parallel;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import hashlife.Hashlife;

public class ImplClient extends UnicastRemoteObject {
    private int[][] matrix;
    private Hashlife a = new Hashlife();

    public ImplClient() throws RemoteException{super();}

    public void setMatrix(int[][] matrix){
        this.matrix = matrix;
    }

    public int[][] getMatrix(){
        return this.matrix;
    }

    public Hashlife getHashLife(){
        return this.a;
    }

}
