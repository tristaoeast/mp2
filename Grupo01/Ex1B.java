import java.io.*;
import java.util.*;

public class Ex1B {

	public static void add1Smoothing(String fname) {

		Hashtable<String, Integer> myTable = new Hashtable<String, Integer>();

		try {

			String[] fnameParts = fname.split("SemAlisamento");
			BufferedWriter writer = new BufferedWriter(new FileWriter (fnameParts[0] + ".txt"));
			FileInputStream fstream = new FileInputStream(fname);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;

			// strLine = br.readLine();
			// String[] lineParts = strLine.split("\t");
			// while (lineParts.length < 2) {
			// 	writer.write(lineParts[0] + "\n");
			// 	strLine = br.readLine();
			// 	lineParts = strLine.split("\t");
			// }
			// int count = Integer.parseInt(lineParts[1]);
			// writer.write(lineParts[0] + "\t" + ++count + "\n");
			while ((strLine = br.readLine()) != null) {
				String[] lineParts = strLine.split("\t");
				int count = Integer.parseInt(lineParts[1]);
				// count++;
				writer.write(lineParts[0] + "\t" + ++count + "\n")	;
			}
			writer.close();

		} catch (NumberFormatException e) {
			System.err.println("NFE: " + e.getMessage());
		} catch (FileNotFoundException e) {
			System.err.println("FNFE: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("IOE: " + e.getMessage());
		}
	}

	public static void bigrams(String fname) {

		Hashtable<String, Integer> myTable = new Hashtable<String, Integer>();

		try {
			String[] fnameParts = fname.split("Anotado.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter (fnameParts[0] + "BigramasSemAlisamento.txt"));
			FileInputStream fstream = new FileInputStream(fname);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;

			while ((strLine = br.readLine()) != null)   {
				// strLine = strLine.replaceAll("[\\,\\;\\:\\/\\\\<\\>\\'\\«\\»\\%\\!\\?\"\\(\\)]|\\-\\-|\\s\\.|\\.\\.|\\.\\.\\.", "");
				// strLine = strLine.replaceAll("(\\S)\\)", "$1");
				// strLine = strLine.replaceAll("\\((\\S)", "$1");
				// strLine = strLine.replaceAll("([a-z])\\.", "$1");
				// strLine = strLine.replaceAll("\\s\\)|\\(\\s", "");

				StringTokenizer st = new StringTokenizer(strLine);
				String currentWord = st.nextToken().toLowerCase();
				String currentBigram;
				while (st.hasMoreTokens()) {

					String nextWord = st.nextToken().toLowerCase();
					currentBigram = currentWord + " " + nextWord;

					//If bigram has been seen, add 1 to value
					if (myTable.containsKey(currentBigram)) {
						Integer prevValue = myTable.get(currentBigram);
						myTable.put(currentBigram, prevValue + 1);
					}
					//else create new bigram entry in table
					else {
						myTable.put(currentBigram, new Integer(1));
					}
					currentWord = nextWord;
				}

			}

			//Grab all of the bigram and put them in a list
			ArrayList<String> keys = new ArrayList<String>(myTable.keySet());
			//Sort the list of bigrams
			Collections.sort(keys);

			for (String key : keys) {
				// System.out.println(key + "\t" + myTable.get(key));
				writer.write(key + "\t" + myTable.get(key) + "\n");
			}
			writer.close();
		} catch (Exception e) { //Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	public static void unigrams(String fname) {

		Hashtable<String, Integer> myTable = new Hashtable<String, Integer>();

		try {
			String[] fnameParts = fname.split("Anotado.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter (fnameParts[0] + "UnigramasSemAlisamento.txt"));
			BufferedWriter writerc = new BufferedWriter(new FileWriter (fnameParts[0] + "UnigramasContagem.txt"));
			FileInputStream fstream = new FileInputStream(fname);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;

			while ((strLine = br.readLine()) != null) {
				// Remove all non alphanumerical characters
				// strLine = strLine.replaceAll("[\\,\\;\\:\\/\\\\<\\>\\'\\«\\»\\%\\!\\?\"\\(\\)]|\\-\\-|\\s\\.|\\.\\.|\\.\\.\\.", "");
				// strLine = strLine.replaceAll("(\\S)\\)", "$1");
				// strLine = strLine.replaceAll("\\((\\S)", "$1");
				// strLine = strLine.replaceAll("([a-z])\\.", "$1");
				// strLine = strLine.replaceAll("\\s\\)|\\(\\s", "");

				StringTokenizer st = new StringTokenizer(strLine);
				String currentUnigram;
				while (st.hasMoreTokens()) {

					currentUnigram = st.nextToken().toLowerCase();

					//If unigram has been seen, add 1 to value
					if (myTable.containsKey(currentUnigram)) {
						Integer prevValue = myTable.get(currentUnigram);
						myTable.put(currentUnigram, prevValue + 1);
					}
					//else create new unigram entry in table
					else {
						myTable.put(currentUnigram, new Integer(1));
					}
				}

			}

			//Grab all of the unigrams and put them in a list
			ArrayList<String> keys = new ArrayList<String>(myTable.keySet());
			//Sort the list of unigrams
			Collections.sort(keys);
			// system.out.println("myTable.size: " + myTable.size());
			writerc.write(Integer.toString(myTable.size()));
			for (String key : keys) {
				// System.out.println(key + "\t" + myTable.get(key));
				writer.write(key + "\t" + myTable.get(key) + "\n");
			}
			writer.close();
			writerc.close();
		} catch (Exception e) { //Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}


	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("ERROR: 1 argument only");
		} else {
			try {

				String[] filename = args[0].split("(?=[A-Z])");
				String fname1 = filename[1].toLowerCase();
				String[] fname2parts = filename[2].split("-");
				// system.out.println("FNAME2PARTS[0]: " + fname2parts[0]);
				String[] fname2parts1 = fname2parts[0].split("\\.");
				// system.out.println("FNAME2PARTS1[0]: " + fname2parts1[0]);
				String fname2 = fname2parts1[0].toLowerCase();

				BufferedWriter writer1 = new BufferedWriter(new FileWriter (fname1 + "Anotado.txt"));
				BufferedWriter writer2 = new BufferedWriter(new FileWriter (fname2 + "Anotado.txt"));

				FileInputStream fstream = new FileInputStream(args[0]);
				BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				String strLine;
				while ((strLine = br.readLine()) != null)   {
					String[] parts = strLine.split("\\t");

					// System.out.println("LINE: " + strLine+ "\n");
					// System.out.println("LEMA: " + parts[0] + "\n");

					if (parts[0].equals(fname1)) {
						// System.out.println("FNAME 1");

						writer1.write(parts[1] + "\n");
					} else if (parts[0].equals(fname2)) {
						// System.out.println("FNAME 2");
						writer2.write(parts[1] + "\n");
					}

				}
				writer1.close();
				writer2.close();

				bigrams(fname1 + "Anotado.txt");
				unigrams(fname1 + "Anotado.txt");
				bigrams(fname2 + "Anotado.txt");
				unigrams(fname2 + "Anotado.txt");

				add1Smoothing(fname1 + "UnigramasSemAlisamento.txt");
				add1Smoothing(fname1 + "BigramasSemAlisamento.txt");
				add1Smoothing(fname2 + "UnigramasSemAlisamento.txt");
				add1Smoothing(fname2 + "BigramasSemAlisamento.txt");


			} catch (IOException e) { //Catch exception if any
				System.err.println("ErrorMain: " + e.getMessage());
			}
		}
	}
}