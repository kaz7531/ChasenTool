import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Execution {
	public static void main(String[] args) throws Exception {

		String dir = "C:\\Users\\Jumatsu\\chasen\\20141107";
		String dictFile = "C:\\Users\\Jumatsu\\chasen\\dict.txt";
		String symOutput = "C:\\Users\\Jumatsu\\chasen\\symfile.txt";
		countWordsFromDir(dir, dictFile); //symfileを作成する際はコメントアウト
		File file = new File("C:\\Users\\Jumatsu\\chasen\\20141107");
		File files[] = file.listFiles();
		
//		File[] files = new File[] 
//		
////		{
////		}
////				
////				new File("C:\\Users\\Jumatsu\\chasen\\20141107\\nikkei1.txt"),
////				new File("C:\\Users\\Jumatsu\\chasen\\20141107\\nikkei2.txt"),
////				new File("C:\\Users\\Jumatsu\\chasen\\20141107\\nikkei3.txt"),
////				new File("C:\\Users\\Jumatsu\\chasen\\20141107\\nikkei4.txt"),
////				new File("C:\\Users\\Jumatsu\\chasen\\20141107\\nikkei5.txt"),
////				new File("C:\\Users\\Jumatsu\\chasen\\20141107\\spo1.txt"),
////				new File("C:\\Users\\Jumatsu\\chasen\\20141107\\spo2.txt"),
////				new File("C:\\Users\\Jumatsu\\chasen\\20141107\\spo3.txt"),
////				new File("C:\\Users\\Jumatsu\\chasen\\20141107\\spo4.txt"),
////				new File("C:\\Users\\Jumatsu\\chasen\\20141107\\spo5.txt"),
////				new File("C:\\Users\\Jumatsu\\chasen\\20141107\\syohi1.txt"),
////				new File("C:\\Users\\Jumatsu\\chasen\\20141107\\syohi2.txt"),
////				new File("C:\\Users\\Jumatsu\\chasen\\20141107\\syohi3.txt"),
////				new File("C:\\Users\\Jumatsu\\chasen\\20141107\\syohi4.txt"),
////				new File("C:\\Users\\Jumatsu\\chasen\\20141107\\syohi5.txt") };
		convertFilesToSymbol(dictFile, files, symOutput);

	}

	public static void countWordsFromDir(String dir, String output)
			throws Exception {
		ChasenTool chasen = new ChasenTool();
		WordCounter counter = new WordCounter();

		// String dir = "";
		// String vobfile = "";

		Map<JapaneseWord, Integer> wordCounting = counter.countWordsFromDir(chasen, dir);
		counter.outputSortedWordCount(wordCounting, output);
	}
	
	public static void convertFilesToSymbol(String dictFile, File[] files,
			String output) throws Exception {
		ChasenTool chasen = new ChasenTool();
		WordCounter counter = new WordCounter();
		File cf = new File("C:\\TEMP\\chasen.txt");
		cf.createNewFile();

		Map<JapaneseWord, Integer> dict = chasen.importDictionaryFromChasenFile(dictFile);

		BufferedWriter out = new BufferedWriter(new FileWriter(output));
		
		TreeMap<String, List<String>> map = new TreeMap<String, List<String>>();
		
		for (File f : files) {
			BufferedReader tempReader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "shift_jis"));
			String line = "";

			while ((line = tempReader.readLine()) != null){
//				System.out.println(line);
				String[] lines = line.split("。");			
				ArrayList<String> sentence_raw = new ArrayList<String>(); 
				for(int i=0; i< lines.length;i++){
//					System.out.println(lines[i]);
					sentence_raw.add(lines[i]);
//					System.out.println(lines[i]);
				}
				map.put(f.getName(), sentence_raw);
//				System.out.println(sentence_raw);
			}
	
			tempReader.close();
			chasen.cmdExe(f.getAbsolutePath(), cf.getAbsolutePath());
			List<Integer> fsym = counter.convertFileToSymbol(dict,
					chasen.readWordsFromChasenFile(cf.getAbsolutePath()));
			for (Integer i : fsym) {
				out.append(i + " ");
			}
			out.append("\n");
		}
//		System.out.println(map.get("nikkei1.txt").get(1));
		
		out.flush();
		out.close();
	}
}
