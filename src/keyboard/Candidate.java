package keyboard;

public class Candidate implements Comparable<Candidate> {
	private String word;
	private Integer confidence;
	
	public Candidate (String word, Integer confidence){
		this.word = word;
		this.confidence = confidence;
	}
	
	public String getWord() {
		return word;
	}
	
	public Integer getConfidence() {
		return confidence;
	}
	
	public void incrementConfidence(){
		this.confidence++;
	}
	
	public String toString(){
		return word + ": " + confidence;
	}

	@Override
	public int compareTo(Candidate o) {
		return o.getConfidence().compareTo(confidence);
	}
	
}
