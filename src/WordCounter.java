import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class WordCounter {
	//static final File TEMP_FILE = new File(System.getProperty("java.io.tmpdir"), "chasen.txt");
	static final File TEMP_FILE = new File("C:\\TEMP\\chasen1.txt");
	
	static{
		if (!TEMP_FILE.exists())
			try {
				TEMP_FILE.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	public List<Integer> convertFileToSymbol(Map<JapaneseWord, Integer> dict, List<JapaneseWord> wList){
		List<Integer> symList = new ArrayList<Integer>();
		for (JapaneseWord js: wList){
			if(dict.containsKey(js)){
				symList.add(dict.get(js));
			}
		}
		return symList;
	}
	
	public Map<JapaneseWord, Integer> countWordsFromDir(ChasenTool chasen, String dirPath) throws Exception{
		File df = new File(dirPath);
		
		File []cf = df.listFiles();
		
		Map<JapaneseWord, Integer> vob = new HashMap<JapaneseWord, Integer>();
		
		for(File f : cf){
			if(f.isFile()){
				chasen.cmdExe(f.getAbsolutePath(), TEMP_FILE.getAbsolutePath());
				List<JapaneseWord> ws = chasen.readWordsFromChasenFile(TEMP_FILE.getAbsolutePath());
				for (JapaneseWord w: ws){
					if(vob.containsKey(w)){
						vob.put(w, vob.get(w) + 1);
					}
					else{
						vob.put(w, 1);
					}
				}
			}
		}
		
		return vob;	
	}
	
	
	public void outputSortedWordCount(Map<JapaneseWord, Integer> vob, String filepath) throws IOException{
		 List<Map.Entry<JapaneseWord, Integer>> list =
		            new LinkedList<Map.Entry<JapaneseWord, Integer>>( vob.entrySet() );
		 
		        Collections.sort( list, new Comparator< Map.Entry<JapaneseWord, Integer> >()
		        {
		            public int compare( Map.Entry<JapaneseWord, Integer> o1, Map.Entry<JapaneseWord, Integer> o2 )
		            {
		                return (o1.getValue()).compareTo( o2.getValue() );
		            }
		        } );


		        BufferedWriter out = new BufferedWriter(new FileWriter(filepath));
		        for (Map.Entry<JapaneseWord, Integer> entry: list){
		        	out.write(entry.getKey() + "\t" + entry.getValue() + "\n");
		        }
		        out.flush();
		        out.close();	        
	}
	
}
