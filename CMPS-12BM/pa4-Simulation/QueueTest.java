/**
 * Richard Ge
 * rwge
 * CS12B/M
 * 5/4/2019
 * This is a testing file to make sure that Queue.java works
 * 
 * QueueTest.java
 */
public class QueueTest {
    public static void main(String[] args) {
	Queue one = new Queue();
	System.out.println(one.isEmpty());
	one.enqueue(new Job(3, 4));
	System.out.println(one.isEmpty());
	one.enqueue(new Job(4, 1));
	one.enqueue(new Job(2, 6));
	System.out.println(one);
	one.peek();
	((Job)one.peek()).computeFinishTime(5);
	System.out.println(one);
	one.enqueue(new Job(5, 2));
	
	one.dequeueAll();
	System.out.println(one.isEmpty());
    }
}
