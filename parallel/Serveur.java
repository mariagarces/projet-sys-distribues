package parallel;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import utils.FileParser;
import java.io.File;

public class Serveur {
    public static void main(String[] args) {
        try {
            ImplServeur impl = new ImplServeur();
            Registry registry = LocateRegistry.createRegistry(2098);
            // Registry registry = LocateRegistry.getRegistry();

            File file = new File("patterns/glider.rle");
            int[][] positions = FileParser.readLifeRLEFile(file);
            impl.getHashLife().loadFromArray(positions);
            impl.setMatrix(positions);

            String nom = "positionsMatrix";
            registry.bind(nom, impl);

            // for (int i = 0; i < numClients; i++) {
            //     String nom = "matrix" + i;
            //     int[][] matrix = impl.divideArrayByRows(3, 6, 20, positions, i);

            //     for (int k = 0; k < matrix.length; k++) {
            //         for (int j = 0; j < matrix[k].length; j++) {
            //             System.out.print(matrix[k][j]);
            //         }
            //         System.out.print("\n");
            //     }
            //     System.out.print("\n");

            //     impl.setMatrix(matrix);
            //     registry.bind(nom, impl);
            //     System.out.println("Register " + nom);
            // }

            System.out.println("Serveur enregistr");

            // registry.bind("Impl",impl);
            // System.out.println("Serveur pret");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}