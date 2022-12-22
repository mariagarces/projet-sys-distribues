package parallel;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class LIfeGameTable {
    private static String prefixStr = "row";
    public static void main(String[] args) {
        try {
            int id = Integer.parseInt(args[0]);
            Registry reg = LocateRegistry.getRegistry(2098);
            List<LGRow> rows = new ArrayList<>();
            // debe recibir el número de steps
            IHashLifeServer server = (IHashLifeServer) reg.lookup("positionsMatrix");
            int size = server.getMatrixSize();
            int n = server.getSubMatrixSize();
            for (int i = 0; i < size; i++) {
                LGRow impl = new LGRow(i,n, reg);
                reg.bind(prefixStr+i, impl);
                rows.add(impl);
            }

            for (LGRow lgRow : rows) {
                lgRow.triggerNextGeneration();
            }
            // for (int k = 0; k < impl.getMatrix().length; k++) {
            //     for (int j = 0; j < impl.getMatrix()[k].length; j++) {
            //         System.out.print(impl.getMatrix()[k][j]);
            //     }
            //     System.out.print("\n");
            // }
            // System.out.print("\n");

        } catch (Exception e) {
            System.err.println("Erreur :" + e);
        }
    }
}