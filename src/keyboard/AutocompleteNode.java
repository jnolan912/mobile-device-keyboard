package keyboard;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/* Autocomplete Node is the datastructure used to store information about the common words used
 * based on characters up to that point
 */
public class AutocompleteNode {
	HashMap<Character, AutocompleteNode> children = new HashMap<Character, AutocompleteNode>();
	List<Candidate> candidates = new LinkedList<Candidate>();
	AutocompleteNode parent;
	Candidate curr = null; // Candidate at this node, if it is one at all
	int level; // The index of character being checked on this level
	final Integer MAX_LIST_SIZE;

	
	/* AutocompleteNode constructor */
	public AutocompleteNode(AutocompleteNode parent, int level, Integer maxSize) {
		this.parent = parent;
		this.level = level;
		MAX_LIST_SIZE = maxSize;
	}
	
	/* Adds a new word, input, to the tree. Index is the level of the tree */
	public void addWord(String input){
		if (level < input.length()){
			AutocompleteNode child = children.get(input.charAt(level));
			if (child == null){
				child = new AutocompleteNode(this, level + 1, MAX_LIST_SIZE);
				children.put(input.charAt(level), child);
			}
			child.addWord(input);
		}
		
		else {
			if (curr == null){
				curr = new Candidate(input, 1);
				addNewCandidateToList(curr);
			}
			else {
				curr.incrementConfidence();
				reorderCandidateList(curr);
			}
		}
	}
	
	/* Gets the candidates for a particular fragment */
	public List<Candidate> getCandidates(String fragment){
		// If this is the end of the string, return the candidates
		if (fragment == null || fragment.equals("")){
			return candidates;
		}
		else {
			AutocompleteNode child = children.get(fragment.charAt(0));
			if (child == null) {
				// If there is no valid options, return empty list
				return new LinkedList<Candidate>(); 
			}
			else{
				// recursively get candidates from child with the rest of the string
				return child.getCandidates(fragment.substring(1, fragment.length()));
			}
		}
	}
	
	// Try to add a candidate to the list of candidates in this node. It will be added if it there is room
	// in the list. This is only used to add to the list when the candidate is created. It is not used when
	// the candidate is updated. If a candidate is added to the list, it then tries to add it to the parents list
	public void addNewCandidateToList(Candidate candidate) {
		if (MAX_LIST_SIZE == null || candidates.size() < MAX_LIST_SIZE){
			candidates.add(candidate);
			if (parent != null){
				parent.addNewCandidateToList(candidate);
			}
		}
	}
	
	// Reorders candidate list to contain the MAX_LIST_SIZE most popular candidates
	// in order
	public void reorderCandidateList(Candidate candidate){
		if (candidates.contains(candidate)){ 
			// The list already contains the candidate that was changed, resort the list based on the new value
			candidates.sort(null);
		}
		else {
			// Check to see if the candidate now has a larger value than the smallest candidate, if it does add it and
			// resort the list
			int lastIndex = candidates.size()-1;
			if (candidate.getConfidence() > candidates.get(lastIndex).getConfidence()){
				candidates.remove(lastIndex);
				candidates.add(candidate);
				candidates.sort(null);
			}
		}
		// If it has a parent, reorder the parent
		if (parent != null){
			parent.reorderCandidateList(candidate);
		}
	}
}
