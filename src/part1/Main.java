package part1;
import java.util.Vector;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

public class Main {

	public static final String className = "Spam";
	public static final String failName = "Not spam";

	private final String trainingPath; // file we are using as base
	private final String dataPath; // file we are applying our learning to
	private final HashMap<Vector<Boolean>,String> trainingData = new HashMap<>();

	public static void main (String[] input) {
		if (input.length != 2) {
			System.err.println("This program requires two parameters, not "+input.length+". The first is a training file and the second is a data file.");
			System.exit(-1);
		}
		Main m;
		m = new Main(input[0],input[1]);
		m.readTrainingFile();
	}

	public Main(String trainingPath, String dataPath) {
		this.trainingPath = trainingPath;
		this.dataPath = dataPath;
		readTrainingFile();
	}

	/** Reads all information from the paths given when the object was created, populating myTrainingData.	 */
	public void readTrainingFile() {
		// first, read the training data into some storage location
		try {
			Scanner s = new Scanner(new File(trainingPath));
			while (s.hasNext()) {
				Scanner lineScan = new Scanner(s.nextLine());
				Vector<Boolean> v = new Vector<>();
				int classifier = -1;
				while (lineScan.hasNext()) {
					int val = lineScan.nextInt();
					if (lineScan.hasNext()) {
						v.add(val == 1); // and what about non-binary values hm
					} else {
						classifier = val;
						continue;
					}
				}
				trainingData.put(v, (classifier == 1 ? className : failName));
				lineScan.close();
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.err.println("Attempted to read "+trainingPath+", but was not found.");
			System.exit(-1);
		}
	}
}
