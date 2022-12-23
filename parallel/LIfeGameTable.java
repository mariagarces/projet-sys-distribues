package parallel;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import utils.FileParser;

public class LIfeGameTable{
    private static String prefixStr = "row";
    public static void main(String[] args) {
        try {
            HashLifeServer implServeur = new HashLifeServer();
            Registry reg = LocateRegistry.createRegistry(2098);
            File file = new File("patterns/pentomino.rle");
            int[][] positions = FileParser.readLifeRLEFile(file);
            List<LGRow> rows = new ArrayList<>();

            implServeur.getHashLife().loadFromArray(positions);
            implServeur.setMatrix(positions);
            implServeur.setGenerations(5);
            List<int[]> arrays = implServeur.getHashLife().divideArrayByRow(3, 3, positions);
            String nom = "positionsMatrix";
            reg.bind(nom, implServeur);

            int size = implServeur.getMatrixSize();

            for (int i = 0; i < size; i++) {
                LGRow impl = new LGRow(i, arrays.get(i), reg);
                reg.bind(prefixStr+i, impl);
                rows.add(impl);
            }

            for (LGRow lgRow : rows) {
                Thread t = new Thread(lgRow);
                t.start();
            }
        } catch (Exception e) {
            System.err.println("Erreur :" + e);
        }
    }
}