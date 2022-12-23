package parallel;

import java.rmi.server.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import java.util.ArrayList;
import hashlife.Hashlife;

public class HashLifeServer extends UnicastRemoteObject implements IHashLifeServer{
    private int[][] matrix;
    private Hashlife a;
    private List<GenerationMessage> msgs;
    private int n;
    private int numGen;
    Registry reg = LocateRegistry.getRegistry(2098);
    private long startTime;

    public HashLifeServer() throws RemoteException{
        super();
        a = new Hashlife();
        msgs = new ArrayList<>();
    }

    @Override
    public int[] getMatrixAssignment(int row) {
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
    public synchronized void calculateNextGen(GenerationMessage msg) throws RemoteException {        
        List<GenerationMessage> copyMsgs;
        msgs.add(msg);

        try {
            if(this.msgs.size() == getMatrixSize() && this.numGen >= 0) {
                printMatrix();
                for(int i=0; i<this.msgs.size(); i++) {
                    a.loadFromArray(msg.getInfo());
                    int[][] matrix = a.printEachState(1);
                    GenerationMessage gMessage = new GenerationMessage(matrix, -1);
                    ILGRow iRow = (ILGRow)reg.lookup("row"+this.msgs.get(i).getMsgSource());
                    iRow.setNewGeneration(gMessage);
                }

                copyMsgs = new ArrayList<>(this.msgs);

                for(int i=0; i<copyMsgs.size(); i++) {
                    ILGRow iRow = (ILGRow)reg.lookup("row"+copyMsgs.get(i).getMsgSource());
                    Thread t = new Thread(new Runnable() {
                         @Override
                         public void run() {
                             try {
                                iRow.triggerNextGeneration();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                         }
                    });
                    t.start();
                }

                this.msgs = new ArrayList<>();
                this.numGen--;
            }
        } catch(Exception e){
            System.err.println("Erreur :" + e);
        }
    }

    public void setGenerations(int numGen) {
        this.numGen = numGen;
    }

    public void printMatrix() throws AccessException, RemoteException, NotBoundException{
        for(int i=0; i<getMatrixSize(); i++){
            ILGRow iRow = (ILGRow)reg.lookup("row"+i);
            int[] row = iRow.getCurrRow();

            for(int j=0; j<row.length; j++){
                System.out.print(row[j]);
            }
            System.out.println("");
        }
        if(this.numGen == 5){
            startTime = System.nanoTime();
        }
        if(this.numGen == 0){
            long stopTime = System.nanoTime();
      	    long elapsedTime = stopTime - startTime;
      	    System.out.println("Time: "+ elapsedTime);
        }
        System.out.println("End of generation");
    }
}
