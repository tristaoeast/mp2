import java.io.*;
import java.util.*;

public class OutSpliterNGramGenerator {

	public static String concat(String[] words, int start, int end) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < end; i++)
			sb.append((i > start ? " " : "") + words[i]);
		return sb.toString();
	}

	public static void bigrams(String fname) {

		Hashtable<String, Integer> myTable = new Hashtable<String, Integer>();

		try {
			FileInputStream fstream = new FileInputStream(fname);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				strLine = strLine.replaceAll("[.,;:/\\<>'«»]|--", "");
				StringTokenizer st = new StringTokenizer(strLine);
				String currentWord = st.nextToken().toLowerCase();
				String currentBigram;
				while (st.hasMoreTokens()) {

					currentBigram = currentWord + " " + st.nextToken().toLowerCase();

					System.out.println("CURRENT WORD: " + currentWord);
					System.out.println("CURRENT BIGRAM: " + currentWord);

					//If bigram has been seen, add 1 to value
					if (myTable.containsKey(currentBigram)) {
						Integer prevValue = myTable.get(currentBigram);
						myTable.put(currentBigram, prevValue + 1);
					}
					//else create new bigram entry in table
					else {
						myTable.put(currentBigram, new Integer(1));
					}
				}

			}


			for (String key : myTable.keySet()) {
				System.out.println(key + "\t" + myTable.get(key));
			}
		} catch (Exception e) { //Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	public static List<String> ngrams(int n, String str) {
		List<String> ngrams = new ArrayList<String>();
		String[] words = str.split(" ");
		for (int i = 0; i < words.length - n + 1; i++)
			ngrams.add(concat(words, i, i + n));
		return ngrams;
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

				// BufferedWriter writer11 = new BufferedWriter(new FileWriter (fname1 + "Unigramas.out"));
				// BufferedWriter writer12 = new BufferedWriter(new FileWriter (fname1 + "Bigramas.out"));
				// BufferedWriter writer21 = new BufferedWriter(new FileWriter (fname2 + "Unigramas.out"));
				// BufferedWriter writer22 = new BufferedWriter(new FileWriter (fname2 + "Bigramas.out"));
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
						// for (String ngram : ngrams(1, strLine)) {
						// 	writer11.write(ngram);
						// }
						// for (String ngram : ngrams(2, strLine)) {
						// 	writer12.write(ngram);
						// }
						writer1.write(parts[1] + "\n");
					} else if (parts[0].equals(fname2)) {
						// for (String ngram : ngrams(1, strLine)) {
						// 	writer21.write(ngram);
						// }
						// for (String ngram : ngrams(2, strLine)) {
						// 	writer22.write(ngram);
						// }
						writer2.write(parts[1] + "\n");
					}

				}
				// writer11.close();
				// writer12.close();
				// writer21.close();
				// writer22.close();
				writer1.close();
				writer2.close();

				bigrams(fname1 + ".out");

			} catch (Exception e) { //Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
		}
	}
}