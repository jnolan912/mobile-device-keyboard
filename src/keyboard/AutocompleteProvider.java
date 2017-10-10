package keyboard;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class AutocompleteProvider {
	AutocompleteNode tree;
	
	// Maximum size of list of autocomplete words
	public AutocompleteProvider(Integer maxSize){
		tree = new AutocompleteNode(null, 0, maxSize);
	}
	
	// Gets list to autocomplete the string fragment
	public List<Candidate> getWords(String fragment){
		return tree.getCandidates(fragment.toLowerCase());
	}
	
	public void train(String passage){
		// Get rid of everything except letters, numbers, _, and '
		String myPassage = passage.replaceAll("[^A-Za-z0-9_'\\s]", "");
		
		// Make everything lowercase and add to tree
		for (String s: myPassage.split("\\s")) {
			tree.addWord(s.toLowerCase());
		}
	}
	
	// Train with input from a text file
	public void trainFromFile(String file){
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
            while((line = bufferedReader.readLine()) != null) {
            	train(line);
            }   

            bufferedReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + file);
		} catch (IOException ex) {
			System.out.println("Error printing file: " + file);
		}
	}
}
