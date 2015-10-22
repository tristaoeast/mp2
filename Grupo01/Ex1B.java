import java.io.*;
import java.util.*;

public class Ex1B {

	public static void add1Smoothing(String fname) {

		Hashtable<String, Integer> myTable = new Hashtable<String, Integer>();

		try {

			String[] fnameParts = fname.split(".out");
			BufferedWriter writer = new BufferedWriter(new FileWriter (fnameParts[0] + "Add1.out"));
			FileInputStream fstream = new FileInputStream(fname);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;

			while ((strLine = br.readLine()) != null)   {
				String[] lineParts = strLine.split("\t");
				Integer count = Integer.parseInt(lineParts[1]);
				count++;
				writer.write(lineParts[0]+ "\t" + count);
			}
			writer.close();

		} catch (Exception e) { //Catch exception if any
			System.err.println("Error: " + e.getMessage());

		}
	}

	public static void bigrams(String fname) {

		Hashtable<String, Integer> myTable = new Hashtable<String, Integer>();

		try {
			String[] fnameParts = fname.split(".out");
			BufferedWriter writer = new BufferedWriter(new FileWriter (fnameParts[0] + "Bigramas.out"));
			FileInputStream fstream = new FileInputStream(fname);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;

			while ((strLine = br.readLine()) != null)   {
				strLine = strLine.replaceAll("[\\,\\;\\:\\/\\\\<\\>\\'\\«\\»\\%\\!\\?\"\\(\\)]|\\-\\-|\\s\\.|\\.\\.|\\.\\.\\.", "");
				strLine = strLine.replaceAll("(\\S)\\)", "$1");
				strLine = strLine.replaceAll("\\((\\S)", "$1");
				strLine = strLine.replaceAll("([a-z])\\.", "$1");
				strLine = strLine.replaceAll("\\s\\)|\\(\\s", "");

				StringTokenizer st = new StringTokenizer(strLine);
				String currentWord = st.nextToken().toLowerCase();
				String currentBigram;
				while (st.hasMoreTokens()) {

					String nextWord = st.nextToken().toLowerCase();
					currentBigram = currentWord + " " + nextWord;

					// System.out.println("CURRENT BIGRAM: " + currentBigram);
					// System.out.println("CURRENT WORD: " + currentWord);


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
			String[] fnameParts = fname.split(".out");
			BufferedWriter writer = new BufferedWriter(new FileWriter (fnameParts[0] + "Unigramas.out"));
			FileInputStream fstream = new FileInputStream(fname);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;

			while ((strLine = br.readLine()) != null)   {
				// Remove all non alphanumerical characters
				strLine = strLine.replaceAll("[\\,\\;\\:\\/\\\\<\\>\\'\\«\\»\\%\\!\\?\"\\(\\)]|\\-\\-|\\s\\.|\\.\\.|\\.\\.\\.", "");
				strLine = strLine.replaceAll("(\\S)\\)", "$1");
				strLine = strLine.replaceAll("\\((\\S)", "$1");
				strLine = strLine.replaceAll("([a-z])\\.", "$1");
				strLine = strLine.replaceAll("\\s\\)|\\(\\s", "");

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
			writer.write(myTable.size() + "\n");
			for (String key : keys) {
				// System.out.println(key + "\t" + myTable.get(key));
				writer.write(key + "\t" + myTable.get(key) + "\n");
			}
			writer.close();
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
				String fname2 = fname2parts[0].toLowerCase();

				BufferedWriter writer1 = new BufferedWriter(new FileWriter (fname1 + ".out"));
				BufferedWriter writer2 = new BufferedWriter(new FileWriter (fname2 + ".out"));

				FileInputStream fstream = new FileInputStream(args[0]);
				BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				String strLine;
				while ((strLine = br.readLine()) != null)   {
					String[] parts = strLine.split("\\t");

					// System.out.println("LINE: " + strLine+ "\n");
					// System.out.println("LEMA: " + parts[0] + "\n");

					if (parts[0].equals(fname1)) {
						writer1.write(parts[1] + "\n");
					} else if (parts[0].equals(fname2)) {
						writer2.write(parts[1] + "\n");
					}

				}
				writer1.close();
				writer2.close();

				bigrams(fname1 + ".out");
				unigrams(fname1 + ".out");
				bigrams(fname2 + ".out");
				unigrams(fname2 + ".out");
				add1Smoothing(fname1+"Unigramas.out");
				add1Smoothing(fname1+"Bigramas.out");
				add1Smoothing(fname2+"Unigramas.out");
				add1Smoothing(fname2+"Bigramas.out");


			} catch (Exception e) { //Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
		}
	}
}