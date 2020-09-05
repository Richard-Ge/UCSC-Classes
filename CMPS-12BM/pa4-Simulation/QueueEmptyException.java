/**
 * Richard Ge
 * rwge
 * CS12B/M
 * 5/4/2019
 * This is the exception thrown when 
 * access or certain mutator methods 
 * are called on an empty queue.
 * 
 * QueueEmptyException.java
 */
public class QueueEmptyException extends RuntimeException{
    public QueueEmptyException(String s){
	super(s);
    }
}