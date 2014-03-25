/**
 *  This class contains the methods of Stack
 *
 *  @author  Cian Cronin
 *  @notes: toString method taken from DLL class. All credit to previous author
 */

class Node<T extends Comparable<T>>
{
	public T data;
	public Node<T> next;
	
	public Node(T theData, Node<T> nextNode)
	{
		data = theData;
		next = nextNode; //The one under it in the stack
	}
}

public class Stack<T extends Comparable<T>>
{

	public Node<T> head;
	
	public Stack()
	{
		head = null;
	} //Constructor
	
	/**
	 * @return False if the list is empty, true if it is not empty
	 */
	public boolean isEmpty()
	{
		return (head==null);
	} //Return the boolean on whether the stack is empty or not

	/**
	 * Justification: TODO
	 */
	public void push(T data)
	{
		
		Node<T> topOfStack = new Node<T>(data, head);
		head = topOfStack;
		
	} //Insert the new data on the top of the stack
	 
	/**
	 * @return Returns the data from the top of the stack,
	 * null if the stack is empty.
	 */
	public T pop()
	{
		if(isEmpty()) return null;
		
		T data = head.data;
		head = head.next;
		return data;
	} //Returns then delete the head of the stack, null if empty
	
	/**
	 * @return: A String form of the stack starting from the head
	 */
	public String toString()
	{
		String s = "";
	    boolean isFirst = true; 

	    // iterate over the list, starting from the head
	    Node<T> i = head;
	    while (i != null)
	    {
	    	if (!isFirst)
	    {
	        s += ",";
        }
	    isFirst = false;
	    s += i.data.toString();
	    i = i.next;
	    }

	    return s;
	} //To String method for stack
	
}
