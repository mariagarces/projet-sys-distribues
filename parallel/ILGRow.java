package parallel;

import java.rmi.Remote;

public interface ILGRow extends Remote {
    public int[] getRowInformation() throws java.rmi.RemoteException;
    public void setNewGeneration(GenerationMessage msg) throws java.rmi.RemoteException;
    public void triggerNextGeneration() throws java.rmi.RemoteException;
    public int getRowID() throws java.rmi.RemoteException;
    public int[] getCurrRow() throws java.rmi.RemoteException;
}
