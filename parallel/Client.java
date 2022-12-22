package parallel;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            int id = Integer.parseInt(args[0]);
            Registry reg = LocateRegistry.getRegistry(2098);
            ImplClient impl = new ImplClient();

            // debe recibir el número de steps
            InterfaceTest impServeur = (InterfaceTest) reg.lookup("positionsMatrix");
            int[][] positions = impServeur.getMatrix();
            int[][] matrix = impServeur.divideArrayByRows(2, 6, 20, positions, id);

            impl.getHashLife().loadFromArray(positions);
            impl.setMatrix(matrix);

            for (int k = 0; k < impl.getMatrix().length; k++) {
                for (int j = 0; j < impl.getMatrix()[k].length; j++) {
                    System.out.print(impl.getMatrix()[k][j]);
                }
                System.out.print("\n");
            }
            System.out.print("\n");

        } catch (Exception e) {
            System.err.println("Erreur :" + e);
        }
    }
}