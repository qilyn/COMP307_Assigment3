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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

public class NaiveBayes {

	public static final String className = "Spam";
	public static final String failName = "Not spam";

	private final String classPath; // file we are using as base
	private final String dataPath; // file we are applying our learning to
	private final ArrayList<ArrayList<Integer>> trainingData;

	public static void main (String[] input) {
		if (input.length != 2) {
			System.err.println("This program requires two parameters, not "+input.length+". The first is a training file and the second is a data file.");
			System.exit(-1);
		}
		NaiveBayes m;
		m = new NaiveBayes(input[0],input[1]);
	}

	public NaiveBayes(String trainingPath, String dataPath) {
		trainingData = new ArrayList<ArrayList<Integer>>();
		this.classPath = trainingPath;
		this.dataPath = dataPath;
		readTrainingFile();
		createNaiveBayes();
	}

	/** Creates a network where the class label is the parent and all variables are the
	 * one child of that parents.
	 */
	private void createNaiveBayes() {
		Node root = new NodeInst( 0 );
		for (int var = 0; var < trainingData.get(0).size(); var++) {
			root.addChild(var+1); // root is 0
			for (ArrayList<Integer> a : trainingData) {
				Node variable = root.getChildByName(var+1);
				if (a.get(var) == 1) {
					variable.addTrueInstances(1);
				} else {
					variable.addFalseInstances(1);
				}
			}
		}
		System.out.println(root.toStringDescendants());
	}

	/** Reads all information from the paths given when the object was created, populating myTrainingData.	 */
	public void readTrainingFile() {
		// first, read the training data into some storage location
		try {
			Scanner s = new Scanner(new File(classPath));
			while (s.hasNext()) {
				String line = s.nextLine();
				if (line.length() == 0)
					continue;
				Scanner lineScan = new Scanner(line);
				ArrayList<Integer> arr = new ArrayList<>();
				while (lineScan.hasNext()) {
					arr.add(lineScan.nextInt());
				}
				trainingData.add(arr);
				lineScan.close();
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.err.println("Attempted to read "+classPath+", but was not found.");
			System.exit(-1);
		}
	}
}
