import hashlife.Hashlife;
import utils.FileParser;
import java.io.File;
import java.util.List;

public class Main {
	static public void main(String[] args) {
		File file = new File("patterns/pentomino.rle");
		int[][] positions = FileParser.readLifeRLEFile(file);
		Hashlife a = new Hashlife();
		a.loadFromArray(positions);
		long startTime = System.nanoTime();
		
		a.printState(5);

		long stopTime = System.nanoTime();
      	long elapsedTime = stopTime - startTime;
      	System.out.println("Time: " + elapsedTime);
	}
}
