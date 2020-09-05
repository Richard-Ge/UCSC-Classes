/**
 * Richard Ge
 * rwge
 * CS12B/M
 * 5/4/2019
 * This is the class that creates a queue of jobs to be done by the Simulation
 * 
 * Queue.java
 */
public class Queue implements QueueInterface{
    private class Node {
	Object job;
	Node next;
	Node(Object j) {
	    job = j;
	}
	public String toString() {
	    return job.toString();
	}
    }
    Node front, back;
    int num;
    
    public Queue() {
	front = null;
	back = null;
	num = 0;
    }
    // isEmpty()
    // pre: none
    // post: returns true if this Queue is empty, false otherwise
    public boolean isEmpty() {
	return (front==null);
    }
    
    // length()
    // pre: none
    // post: returns the length of this Queue.
    public int length() {
	return num;
    }
    
    // enqueue()
    // adds newItem to back of this Queue
    // pre: none
    // post: !isEmpty()
    public void enqueue(Object newItem) {
	if(num==0) {
	    front = new Node((Job)newItem);
	    back = front;
	    num++;
	} else {
	    back.next = new Node((Job)newItem);
	    back = back.next;
	    num++;
	}
    }
    
    // dequeue()
    // deletes and returns item from front of this Queue
    // pre: !isEmpty()
    // post: this Queue will have one fewer element
    public Object dequeue() throws QueueEmptyException {
	if(num==0 || front==null) {
	    throw new QueueEmptyException("");
	}
	Node temp = front;
	front = front.next;
	num--;
	return temp.job;
    }
    
    // peek()
    // pre: !isEmpty()
    // post: returns item at front of Queue
    public Object peek() throws QueueEmptyException {
	if(num==0) {
	    throw new QueueEmptyException("");
	}
	return front.job;
    }
    
    // dequeueAll()
    // sets this Queue to the empty state
    // pre: !isEmpty()
    // post: isEmpty()
    public void dequeueAll() throws QueueEmptyException {
	if(num==0) {
	    throw new QueueEmptyException("");
	}
	front = null;
	back = null;
	num = 0;
    }
    
    // toString()
    // overrides Object's toString() method
    public String toString() {
	Node temp = front;
	String result = "";
	while(temp != null) {
	    result = result + temp.job.toString() + " ";
	    temp = temp.next;
	}
	return result;
    }
}


