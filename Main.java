import hashlife.Hashlife;
import utils.FileParser;
import java.io.File;
import java.util.List;

public class Main {
	static public void main(String[] args) {
		File file = new File("patterns/glider.rle");
		int[][] positions = FileParser.readLifeRLEFile(file);
		Hashlife a = new Hashlife();
		int width = 20;
		int heigth = 5;

		// carga estado inicial
		a.loadFromArray(positions);

		// imprime cada estado y hace el evolve dependiendo del valor del steps
		int[][] stepArray = a.printEachState(4);

		// llena todo de ceros dependiendo del tamaño que queremos
		int[][] result = a.fixGrid(heigth, width, stepArray);

		// for (int k = 0; k < result.length; k++) {
		// 	for (int j = 0; j < result[k].length; j++) {
		// 		System.out.print(result[k][j]);
		// 	}
		// 	System.out.print("\n");
		// }
		// System.out.print("\n");

		// List<int[][]> matrix = a.divideArrayByRows(3, 3, 4, positions);

		// for (int k=0; k<matrix.get(0).length; k++){
		// for(int j=0; j<matrix.get(0)[k].length; j++){
		// System.out.print(matrix.get(0)[k][j]);
		// }
		// System.out.print("\n");
		// }
		// System.out.print("\n");

		// System.out.println("Matrix 1------------------");
		// a.loadFromArray(positions);
		// a.evolve(1);
		// a.printState(1);
		// a.evolve(1);
		// a.printState(1);
		// a.evolve(1);
		// a.printState(1);
		// a.evolve(1);
		// a.printState(1);
		// a.draw(1,0,a.getState());
		// System.out.println(a.getState().toArray().length);
	}
}
