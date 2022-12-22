package parallel;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LGRow extends UnicastRemoteObject implements ILGRow{
    private int[] currRow;
    private int rowID;
    private int n;
    private Registry reg;
    IHashLifeServer server;

    public LGRow(int rowID, int n, Registry reg) throws RemoteException, NotBoundException{
        super();
        this.rowID=rowID;
        this.n=n;
        this.reg= reg;
        server = (IHashLifeServer) reg.lookup("positionsMatrix");
        this.currRow = server.getMatrixAssignment(rowID);
    }

    @Override
    public int[] getRowInformation() throws RemoteException {
        return currRow;
    }

    @Override
    public void setNewGeneration(GenerationMessage msg) throws RemoteException {
        // Update CurrRow according to rowId position
    }

    @Override
    public void triggerNextGeneration(){
        //Ask for required row information, from rowID-n until rowID+n
        //Create a matrix with the needed info.
        //Create a GenerationMessage and send it to Hashlife.
    }

}
