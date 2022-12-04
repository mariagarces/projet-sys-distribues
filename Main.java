import hashlife.Hashlife;
import utils.FileParser;
import java.io.File;

public class Main {
    static public void main(String[] args) {
		File file = new File("patterns/glider.rle");
		int[][] positions = FileParser.readLifeRLEFile(file);
		Hashlife a = new Hashlife();

		a.loadFromArray(positions);
		a.printState(10);
	}
}
