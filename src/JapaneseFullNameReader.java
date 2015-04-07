import java.io.BufferedReader;
import java.io.IOException;

public class JapaneseFullNameReader implements JapaneseWordReader {

	@Override
	public JapaneseWord getNext(BufferedReader read) throws IOException{
		String line = null;
		while ((line=read.readLine())!=null){
			String[] s = line.split("\\s+");
			if (s.length>=4){ //&& checkWord(s[3])==true)		//checkWordを使用する場合はコメントを外す
					return new JapaneseWord(s[0],s[1],s[2],s[3]);
			}
		}

		return null;
	}

	@Override
	public boolean checkWord(String wordProperty){
		String []c = wordProperty.split("-");
		boolean flag = false;
		for (int i=0; i<c.length; i++){
			if (c[i].equals("名詞") || c[i].equals("動詞") || c[i].equals("形容詞") )
				flag=true;
			if (c[i].equals("固有名詞") )		//""内に含める文字がわからない。呂さんに聞く
				flag=true;
			if(c[i].equals("接尾") && flag==true){
				return false;
			}
		}
		return flag;

	}
}
