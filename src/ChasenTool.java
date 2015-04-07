import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChasenTool {
	JapaneseWordReader read;
	private int i;
	private  static final String  CHASEN_CMD_FORMAT = "chasen -F %M\\t%y1\\t%a1\\t%U(%P-)\\n  -o";

	public ChasenTool(){
		this(new JapaneseFullNameReader());
	}

	public ChasenTool(JapaneseWordReader read){
		this.read = read;
	}

	public boolean cmdExe(String infile, String outfile) throws Exception{
		String cmd = getExeCmd(infile, outfile);
		Process process = Runtime.getRuntime().exec(cmd);
		process.waitFor();

		if (process.exitValue()!=0){
			System.err.println("chasen's processing of "+ infile+ " has failed.");
		}

		return new File(outfile).exists();
	}



	public  List<JapaneseWord> readWordsFromChasenFile(String fileSource, String encoding) throws Exception {
		List<JapaneseWord> inputWords  = new ArrayList<JapaneseWord>();
		BufferedReader tempReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileSource), encoding));
		JapaneseWord word = null;
		String sentence = "";									//sentenceを空にする

		while ((word = read.getNext(tempReader)) != null){
			inputWords.add(word);
			sentence = sentence + word.basicForm;	//sentenceに、形態素解析の結果を順番に結合する
			}
		ArrayList<String> sentences = new ArrayList<String>(); //。区切った文を格納するリストを宣言
		String[] lines = sentence.split("[、。]");			//、と。で文を区切る
		for(int i=0; i< lines.length;i++){


			sentences.add(lines[i]);						//区切った文をsentencesに代入
//			System.out.println(lines[i]);

		}

//		map.put(fileSource, sentences);
//		System.out.println(map.get("nikkei.txt").get(0));
		tempReader.close();

		return inputWords;
	}
	private String getName() {
		// TODO
		return null;
	}

	public Map<JapaneseWord, Integer> importDictionaryFromChasenFile(String fileSource) throws Exception{
		List<JapaneseWord> list =  readWordsFromChasenFile(fileSource);
		Map<JapaneseWord, Integer> dict = new HashMap<JapaneseWord, Integer>();
		for(JapaneseWord jw: list){
			dict.put(jw, dict.size() + 1);
		}
		return dict;
	}
	
	public  List<JapaneseWord> readWordsFromChasenFile(String fileSource) throws Exception {
		return this.readWordsFromChasenFile(fileSource, "shift_jis");
	}


	private String getExeCmd(String infile, String outfile){
		return CHASEN_CMD_FORMAT +" "+ outfile + " "+ infile;
	}
}
