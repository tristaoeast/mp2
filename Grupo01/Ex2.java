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


	public static void lemmatizeFile(String fname) {
		try {

			FileInputStream fstream = new FileInputStream(fname);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));


			// nextWord = "Troll";
			// if (prevWord == null)
			// 	System.out.println("PREVWORD!");
			// if (nextWord != null)
			// 	System.out.println("NEXTWORD: " + nextWord);

			String strLine;
			while ((strLine = br.readLine()) != null) {
				boolean paramFound = false;
				StringTokenizer st = new StringTokenizer(strLine);
				String previousWord = null;
				String nextWord = null;
				String currentWord = st.nextToken().toLowerCase();
				while (st.hasMoreTokens() && !paramFound) {
					nextWord = st.nextToken().toLowerCase();
					if(currentWord.equals(param)){
						paramFound = true;
					}
					previousWord = currentWord;
					currentWord = nextWord;

				}
				if(currentWord.equals(param) && !paramFound){
					
				}

			}

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
			System.err.println("ERROR: Ussage as follows - java Ex2 lemma1UnigramasAdd1.txt lemma1BigramasAdd1.txt lemma2UnigramasAdd1.txt lemma2BigramasAdd1.txt params.txt frases.txt");
			System.exit(-1);
		}

		loadTables(args);
		loadParamLemmas(args[4]);
		lemmatizeFile(args[5]);

	}
}