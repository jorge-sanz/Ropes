package ropes;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Rope is a class which implements a string as a binary tree where each internal
 * node is another rope which represents a two substrings concatenation (left and 
 * right children) and each external node is also another rope which represents
 * a literal string.
 * 
 * @author José Luis Martín
 * @author Jorge Sanz
 *
 */

public class Rope {
	private int h, n;
	private Rope leftRope, rightRope;
	private String associatedString;
	
	/**
	 * Initializes an external rope with a specified string value.
	 * 
	 * @param associatedString String associated to a external rope
	 */
	public Rope(String associatedString) {
		h = 0;
		n = associatedString.length();
		leftRope = null;
		rightRope = null;
		this.associatedString = associatedString;
	}
	
	/**
	 * Initializes an internal rope specifying an associated right Rope and an 
	 * associated left Rope.
	 * 
	 * @param leftRope associated left Rope
	 * @param rightRope associated right Rope
	 */
	public Rope(Rope leftRope, Rope rightRope) {
		n = leftRope.getN() + rightRope.getN();
		h = Math.max(leftRope.getH() + 1, rightRope.getH() + 1);
		this.leftRope = leftRope;
		this.rightRope = rightRope;
		associatedString = null;
	}
	
	/**
	 * Returns the height of the tree
	 * 
	 * @return h height of the tree
	 */
	public int getH(){
		return h;
	}
	
	/**
	 * Returns the length of the entire associated String
	 * 
	 * @return n length of the entire associated String
	 */
	public int getN(){
		return n;
	}
	
	/**
	 * Returns the associated String
	 * 
	 * @return associatedString associated String
	 */
	public String getAssociatedString(){
		return associatedString;
	}
	
	/**
	 * Returns the associated left Rope
	 * 
	 * @return leftRope associated left Rope
	 */
	public Rope getLeftRope(){
		return leftRope;
	}
	
	/**
	 * Returns the associated right Rope
	 * 
	 * @return rightRope associated right Rope
	 */
	public Rope getRightRope(){
		return rightRope;
	}
	
	/**
	 * Returns the character associated to a specified position
	 * 
	 * @param index integer of the position
	 * @return (character in the specified position)
	 * @throws Exception 
	 */
	public char charAt(int index) throws Exception {
		if (index < getN()) {
			if (getH() == 0) {
				return associatedString.charAt(index);
			} else {
				if (leftRope.getN() > index) {
					return leftRope.charAt(index);
				} else {
					return rightRope.charAt(index - leftRope.getN());
				}
 			}
		} else {
			throw new Exception("El índice supera la longitud del rope");
		}
	}
	
	/**
	 * Returns a balanced version of the Rope.
	 * <br>
	 * Balanced means that it's a tree where every ropes in a same level have the same
	 * height.
	 * 
	 * @return balancedRope
	 */
	public Rope balance() {
		Stack<Rope> ropesStack = new Stack<Rope>();
		ArrayList<Rope> listOfOrderedExternalRopes = new ArrayList<Rope>();
		
		ropesStack.push(this);
		while (!(ropesStack.empty())) {
			if (ropesStack.peek().getH() == 0) {
				listOfOrderedExternalRopes.add(ropesStack.peek());
				ropesStack.pop();
			} else {
				Rope temporalLeftRope = ropesStack.peek().getLeftRope();
				Rope temporalRightRope = ropesStack.peek().getRightRope();
				ropesStack.pop();
				ropesStack.push(temporalRightRope);
				ropesStack.push(temporalLeftRope);
			}
		}
		return makeABalancedTree(listOfOrderedExternalRopes);
		
	}
	
	/**
	 * Returns a Rope which represents a balanced tree from a specified ArrayList
	 * of external Ropes.
	 * 
	 * @param list ArrayList of external Ropes
	 * @return balancedRope balanced Rope
	 */
	private Rope makeABalancedTree(List<Rope> list) {
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return new Rope(makeABalancedTree(list.subList(0, list.size()/2)), 
					makeABalancedTree(list.subList(list.size()/2, list.size())));
		}
	}
}
