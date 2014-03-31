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
	
	public boolean isEmpty()
	{
		return (head==null);
	} //Return the boolean on whether the stack is empty or not

	public void push(T data)
	{
		
		Node<T> topOfStack = new Node<T>(data, head);
		head = topOfStack;
		
	} //Insert the new data on the top of the stack
	 
	public T pop()
	{
		if(isEmpty()) return null;
		
		T data = head.data;
		head = head.next;
		return data;
	} //Returns then delete the head of the stack, null if empty
	
	
}
