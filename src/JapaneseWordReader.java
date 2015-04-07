

import java.io.BufferedReader;
import java.io.IOException;

public interface JapaneseWordReader {

	public abstract JapaneseWord getNext(BufferedReader read)
			throws IOException;

	public abstract boolean checkWord(String wordProperty);

}