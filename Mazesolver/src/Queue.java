// -------------------------------------------------------------------------
/**
 *  This class contains the methods of Doubly Linked List changed for
 *  a queuing (FIFO) data structure.
 *
 *  @author  Cian Cronin (Student No. 12310411)
 */

class DLLNode<T extends Comparable<T>> 
{
    public T data;
    public DLLNode<T> next;
    public DLLNode<T> prev;

    public DLLNode( T theData, DLLNode<T> prevNode, DLLNode<T> nextNode ) 
    {
      data = theData;
      prev = prevNode;
      next = nextNode;
    }

}

class Queue<T extends Comparable<T>> 
{
    private DLLNode<T> head, tail; //head is first; tail is last.

    /**
     * Constructor
     * @return DoublyLinkedList Queue
     */
    public Queue() 
    {
    	head = null;
    	tail = null;
    } 

    public boolean isEmpty()
    {
      boolean isEmpty = (head==null);
      return isEmpty;
    } //Checks if the queue is empty

    public void add( T data ) 
    {
      DLLNode<T> newNode = new DLLNode<T>(data, null, head);
      if(isEmpty())
      {
    	  tail = newNode;
      }
      else
      {
    	  head.prev = newNode;
      }
      head = newNode;
    } //Insert first element at the back of the queue
    
    public T pull()
    {
    	T data = getLast();
    	deleteLast();
    	return data;
    } //Uses combination of getLast/deleteLast to implement FIFO queuing system
    
    public T getLast()
    {
      if(isEmpty())
      {
    	  return null;
      }
      else
      {
    	  return tail.data;
      }
    } //Get the data stored in the tail, return null if empty

    public boolean deleteLast() 
    {
      if(isEmpty())
      {
    	  return false;
      }
      else if(tail.prev==null) //If there is only one item in the list
      {
    	  head = null;
    	  tail = null;
      }
      else //For the rest of the cases
      {
    	  tail.prev.next = null;
    	  tail = tail.prev;
      }
      
      return true;
    } //Return false if isEmpty() returns true, else make deletion and return true

}
