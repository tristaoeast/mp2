import java.io.*;
import java.util.*;

public class Ex2 {

	private static Hashtable<String, Integer> lemma1UnigramTable = new Hashtable<String, Integer>();
	private static Hashtable<String, Integer> lemma1BigramTable = new Hashtable<String, Integer>();
	private static Hashtable<String, Integer> lemma2UnigramTable = new Hashtable<String, Integer>();
	private static Hashtable<String, Integer> lemma2BigramTable = new Hashtable<String, Integer>();
	private static int lemma1UnigramsTotal;
	private static int lemma2UnigramsTotal;
	private static String lemma1;
	private static String lemma2;
	private static String param;

	public static double getProbabilityBigramAdd1(String word1, String word2, int lemma) {

		if (word1 == null || word2 == null)
			return 1;

		String bigram = word1 + " " + word2;
		String unigram = word1;
		double bigramCountAdd1 = 1;
		double word1CountAdd1 = lemma1UnigramsTotal + lemma2UnigramsTotal;
		if (1 == lemma) {

			if (lemma1BigramTable.containsKey(bigram))
				bigramCountAdd1 = ((double) lemma1BigramTable.get(bigram) + 1.0);
			if (lemma1UnigramTable.containsKey(word1))
				word1CountAdd1 = ((double) lemma1UnigramTable.get(word1) + lemma1UnigramsTotal + lemma2UnigramsTotal);


		} else if (2 == lemma) {

			if (lemma2BigramTable.containsKey(bigram))
				bigramCountAdd1 = ((double) lemma2BigramTable.get(bigram) + 1.0);
			if (lemma2UnigramTable.containsKey(word1))
				word1CountAdd1 = ((double) lemma2UnigramTable.get(word1) + lemma1UnigramsTotal + lemma2UnigramsTotal);

		}
		System.out.println(bigram + "-" + lemma);
		System.out.println(bigramCountAdd1 + "/" + word1CountAdd1);
		return ((double) bigramCountAdd1 / word1CountAdd1);
	}

	public static void lemmatizeLine(String previousWord, String currentWord, String nextWord, BufferedWriter writer, String line) {

		double probLemma1 = getProbabilityBigramAdd1(previousWord, currentWord, 1)
		                    * getProbabilityBigramAdd1(currentWord, nextWord, 1);
		double probLemma2 = getProbabilityBigramAdd1(previousWord, currentWord, 2)
		                    * getProbabilityBigramAdd1(currentWord, nextWord, 2);

		System.out.println("pWORD: " + previousWord + " cWORD: " + currentWord + " nWORD: " + nextWord);
		System.out.println(lemma1 + ": " + probLemma1 + " ||| " + lemma2 + ": " + probLemma2);

		try {
			if (probLemma1 > probLemma2)
				writer.write(lemma1 + "\t" + line + "\n");
			else if (probLemma1 < probLemma2)
				writer.write(lemma2 + "\t" + line + "\n");
			else if (probLemma1 == probLemma2)
				writer.write(lemma1 + "," + lemma2 + "\t" + line + "\n");
			else
				writer.write("?" + "\t" + line + "\n");
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}


	public static void lemmatizeFile(String fname) {
		try {

			FileInputStream fstream = new FileInputStream(fname);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String[] fnameParts = fname.split(".txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter (fnameParts[0] + "Lemmatized.out"));


			String strLine;
			while ((strLine = br.readLine()) != null) {

				boolean paramFound = false;
				StringTokenizer st = new StringTokenizer(strLine);
				String previousWord = null;
				String nextWord = null;
				String currentWord = st.nextToken().toLowerCase();
				while (st.hasMoreTokens() && !paramFound) {
					nextWord = st.nextToken().toLowerCase();
					if (currentWord.equals(param)) {
						paramFound = true;
						lemmatizeLine(previousWord, currentWord, nextWord, writer, strLine);
						break;
					}
					previousWord = currentWord;
					currentWord = nextWord;
					nextWord = null;
				}

				// Situation where the desired param is the last token in sentence
				if (currentWord.equals(param) && !paramFound) {
					lemmatizeLine(previousWord, currentWord, nextWord, writer, strLine);
				}

			}
			writer.close();

		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}


	}

	private static void loadParamLemmas(String fname) {

		try {

			FileInputStream fstream = new FileInputStream(fname);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			param = br.readLine();
			String lemmasLine = br.readLine();
			String[] lemmasParts = lemmasLine.split("\\s");

			if (!((lemma1.equals(lemmasParts[0]) && lemma2.equals(lemmasParts[1]))
			        || (lemma1.equals(lemmasParts[1]) && lemma2.equals(lemmasParts[0])))) {
				System.err.println("ERROR: Wrong parametrization file or wrong NGrams files");
				System.exit(-1);
			} else
				System.out.println("PARAM: " + param);

		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}


	public static void loadTables(String[] args) {

		try {

			String[] fnameParts = args[0].split("[A-Z]");
			lemma1 = fnameParts[0];
			System.out.println("LEMMA1: " + lemma1);

			FileInputStream fstream = new FileInputStream(args[0]);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			lemma1UnigramsTotal = Integer.parseInt(br.readLine());
			String strLine;
			while ((strLine = br.readLine()) != null) {
				String[] lineParts = strLine.split("\t");
				lemma1UnigramTable.put(lineParts[0], Integer.parseInt(lineParts[1]));
			}

			fstream = new FileInputStream(args[1]);
			br = new BufferedReader(new InputStreamReader(fstream));
			while ((strLine = br.readLine()) != null) {
				String[] lineParts = strLine.split("\t");
				lemma1BigramTable.put(lineParts[0], Integer.parseInt(lineParts[1]));
			}

			fnameParts = args[2].split("[A-Z]");
			lemma2 = fnameParts[0];
			System.out.println("LEMMA2: " + lemma2);

			fstream = new FileInputStream(args[2]);
			br = new BufferedReader(new InputStreamReader(fstream));
			lemma2UnigramsTotal = Integer.parseInt(br.readLine());
			while ((strLine = br.readLine()) != null) {
				String[] lineParts = strLine.split("\t");
				lemma2UnigramTable.put(lineParts[0], Integer.parseInt(lineParts[1]));
			}

			fstream = new FileInputStream(args[3]);
			br = new BufferedReader(new InputStreamReader(fstream));
			while ((strLine = br.readLine()) != null) {
				String[] lineParts = strLine.split("\t");
				lemma2BigramTable.put(lineParts[0], Integer.parseInt(lineParts[1]));
			}

		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}


	public static void main(String[] args) {

		if (args.length != 6) {
			System.err.println("ERROR: Usage as follows - java Ex2 lema1Unigramas.txt lema1Bigramas.txt lema2Unigramas.txt lema2Bigramas.txt params.txt frases.txt");
			System.exit(-1);
		}

		loadTables(args);
		loadParamLemmas(args[4]);
		System.out.println(lemma1 + ": " + lemma1UnigramsTotal + " ||| " + lemma2 + ": " + lemma2UnigramsTotal);
		lemmatizeFile(args[5]);

	}
}