
public class JapaneseWord {
	String basicForm;
	String oralForm;
	String pronunciation;
	String property;

	
	public JapaneseWord(String basicForm, String oralForm, String pronunciation,String property) {
		super();
		this.basicForm = basicForm;
		this.oralForm = oralForm;
		this.pronunciation = pronunciation;
		this.property = property;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof JapaneseWord == false)
			return false;
		
		JapaneseWord word = (JapaneseWord)obj;
		if(!basicForm.equals(word.basicForm))
			return false;
//		else if(!oralForm.equals(word.oralForm))
//			return false;
//		else if(!pronunciation.equals(word.pronunciation))
//			return false;
//		else if(!property.equals(word.property))
//			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		return basicForm.hashCode();
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return basicForm+"\t"+oralForm+"\t"+pronunciation+"\t"+property;
	}
	
	
	
}
