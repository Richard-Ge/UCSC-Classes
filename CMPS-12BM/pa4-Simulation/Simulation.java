/**
 * Richard Ge
 * rwge
 * CS12B/M
 * 5/4/2019
 * This is the file that simulates customers at 
 * a checkout section at a store.
 * 
 * Simulation.java
 * 
 * What if one job has an early arrival time but a high finish time?
 * Then when it gets put into the done queue, the order would be different
 * 
 * - recreate original queue?
 * - immediately calculate finish time with 
 * other queue's saved total delay, - time
 * 
 * 
 */

import java.io.*;
import java.util.Scanner;

public class Simulation {

    // The following function may be of use in assembling the initial backup and/or
    // storage queues.  You may use it as is, alter it as you see fit, or delete it
    // altogether.

    public static Job getJob(Scanner in) {
	String[] s = in.nextLine().split(" ");
	int a = Integer.parseInt(s[0]);
	int d = Integer.parseInt(s[1]);
	return new Job(a, d);
    }

    // The following stub for function main contains a possible algorithm for this
    // project.  Follow it if you like.  Note that there are no instructions below
    // which mention writing to either of the output files.  You must intersperse
    // those commands as necessary.

    public static void main(String[] args) throws IOException{

	//    1.  check command line arguments
	  if(args.length != 1) {
	  System.out.println("Usage: Simulation <inputfile>");
	  System.exit(1);
	  }
	//ADD PART THAT CHECKS IF args[0] IS A FILE
	//    2.  open files for reading and writing
	System.out.println("Very beginning");
	Scanner in = new Scanner(new File(args[0]));
	//CREATE FILEWRITERS
	PrintWriter report = new PrintWriter(new FileWriter(args[0]+".rpt"));
	PrintWriter trace = new PrintWriter(new FileWriter(args[0]+".trc"));
	report.println("Report file: "+args[0]+".rpt");
	trace.println("Trace file: "+args[0]+".trc");
	//    3.  read in m jobs from input file
	int m = Integer.parseInt(in.nextLine());
	report.println(m+" Jobs:");
	trace.println(m+" Jobs:");
	Queue todo = new Queue();//storage
	Queue done = new Queue();
	Job curr;
	Job[] jobs = new Job[m];
	for(int i = 0; i < m; i++) {//ADDS CUSTOMERS TO STORAGE ARRAY
	    //or use for loop to m-1
	    curr = getJob(in);
	    report.print(curr+" ");
	    trace.print(curr+" ");
	    jobs[i] = curr;
	}
	report.println("\n\n***********************************************************");
	trace.println("\n");
	Queue[] checkers;
	//    4.  run simulation with n processors for n=1 to n=m-1  {
	//FOR LOOP N = NUMBER OF CHECKERS
	System.out.println("before main for loop");
	for(int n = 1; n < m; n++) {
	    checkers = new Queue[n];//MAKE ARRAY OF 'N' CHECKERS
	    //PRINT OUT HEADER OF # OF PROCESSORS
	    trace.print("*****************************\n"+n+" processor");
	    if(n > 1) {
		trace.println("s:\n*****************************");
	    } else {
		trace.println(":\n*****************************");
	    }
	    for(int i = 0; i < m; i++) {
		todo.enqueue(jobs[i]);
	    }
	    for(int i = 0; i < n; i++) {
		//    5.      declares and initializes an array of n processor Queues
			checkers[i] = new Queue();//PUT QUEUES IN ARRAY OF CHECKERS
	    }
	    //    6.      while unprocessed jobs remain  {
	    int time = 0;
	    //print out trace at beginning
	    trace.println("time="+time);
	    printState(trace, todo, done, checkers);
	    //   trace.println("0: "+todo);
	    //   for(int i = 1; i <= n; i++) {
	    //   trace.println(i+": "+checkers[i-1]);
	    //   }
	    //ADD TO SMALLEST QUEUE, COMPUTE FINISH TIME OF FRONT JOB, 
	    //FINISH ANY JOBS, COMPUTE FINISH TIME OF NEW FRONT JOB, 
	    //PRINT TO .trc FILE
	    while(done.length()!=m) {//WHILE NOT ALL JOBS ARE FINISHED
		// /*DEBUG*/System.out.println(todo.dequeue());
		//    7.          determine the time of the next arrival or finish event and 
		//                update time (W/peek() AND computeFinishTime() ON FIRST JOB)
		//   System.out.print(time+" ");
			boolean event = false;
			//    8.          complete all processes finishing now
			// LOOP: FINISH JOBS IN CHECKER QUEUES 
			   for(int i = 0; i < checkers.length; i++) {//LOOP THROUGH CHECKOUTS
				   if(checkers[i].length() != 0) {//IF QUEUE ISN'T EMPTY
					   Job lineJob = (Job)(checkers[i].peek());//JOB AT FRONT OF EACH CHECKER
					   if(lineJob.getFinish()==time) {//IF IT'S TIME TO FINISH
						   done.enqueue(checkers[i].dequeue());//PUT IT BACK IN TODO
						   event = true;
						   if(!checkers[i].isEmpty()) {//IF THERES JOBS LEFT
							   Job newFront = (Job)checkers[i].peek();
							   newFront.computeFinishTime(time);//GET FINISH TIME OF NEXT JOB
						   }
//						   System.out.println("Job done!");
//						   printState(todo, done, checkers);//PRINT TO TRACE
					   }
				   }
			   }//END FINISHING JOBS
		
		   
		//    9.          if there are any jobs arriving now, assign them to a processor 
		//                Queue of minimum length and with lowest(0?) index in the queue array.
		//THIS IF STATEMENT REPLACES THE CLODE BELOW
		if(!todo.isEmpty()) {
			Job front = (Job)(todo.peek());
			if(front.getArrival()==time) {
				enqueueAll(time, todo, done, checkers);
				event = true;
//				printState(/*trace,*/ todo, done, checkers);
			}
		}
		//   if(!todo.isEmpty()) {//IF THERE ARE ELEMENTS IN TODO
		//   // DO LOOP: GET ALL JOBS ARRIVING NOW!!!
		//   Job curJob = (Job)todo.peek();//GETS NEXT CUSTOMER
		//   if(curJob.getArrival()==time) {//IF ARRIVING NOW
		//   System.out.println("Arriving: "+curJob);
		//   //LOOP THROUGH CHECKERS AND FIND LOWEST NUMBER
		//   int lowest = 9999;
		//   for(int i = 0; i < checkers.length; i++) {
		//   if(checkers[i].length()<lowest) {
		//   lowest = checkers[i].length();
		//   }
		//   }
		//   //LOOP THROUGH AGAIN AND FIND INDEX OF THE LOWEST NUMBER
		//   int lineIndex = 0;
		//   while(checkers[lineIndex].length()!=lowest) {
		//   lineIndex++;
		//   }//NOW lineIndex IS POINTING AT LOWEST, SMALLEST INDEX
		//   //PUT THE FRONT JOB INTO THAT LINE
		//   checkers[lineIndex].enqueue(todo.dequeue());
		//   System.out.println("Job put in line: "+lineIndex);
		////   if(!todo.isEmpty()) {
		////   curJob = (Job)todo.peek();//UPDATES FRONT JOB
		////   }
		//   }
		//   }//END ADDING JOBS
		// LOOP: COMPUTE FINISH TIMES OF FRONT JOBS
		for(int i = 0; i < checkers.length; i++) {//LOOP THROUGH CHECKOUTS
		    if(checkers[i].length() != 0) {//IF QUEUE ISN'T EMPTY
			Job lineJob = (Job)(checkers[i].peek());//JOB AT FRONT OF EACH CHECKER
			if(lineJob.getFinish()==-1) {
			    lineJob.computeFinishTime(time);//COMPUTE FINISH TIME OF FIRST JOB
			    event = true;//not needed?
			}
		    }
		}//END COMPUTING FINISH TIMES
		if(event == true) {
		    trace.println("time="+time);
		    printState(trace, todo, done, checkers);
		}
		// System.out.println("end of while "+time);
		time++;
		//    10.     end WHILE loop
	    }
	    //    11.     compute the total wait, maximum wait, and average wait for 
	    //            all Jobs, then reset finish times
	    //AFTER EVERYTHING HAPPENS, GO THROUGH todo AND DEQUEUE/ENQUEUE 
	    //WHILE RESETTING FINISH TIMES
	    int totalWait = 0;
	    int maxWait = -1;
	    double avgWait;
	    Job cur;
	    //   // LOOP: calculate finish time while dequeue/enqueue-ing
	    for(int i = 0; i < m; i++) {
			cur = (Job)done.dequeue();//DEQUEUE/STORE FIRST FINISH
			totalWait = totalWait + cur.getWaitTime();//ADD TO TOTALWAIT
			if(cur.getWaitTime() > maxWait) {
			    maxWait = cur.getWaitTime();
			}
			cur.resetFinishTime();//RESET TO UNDEF
			todo.enqueue(jobs[i]);//PUT BACK IN TODO
			//   System.out.println("Moved: "+cur);
	    }
		if(!todo.isEmpty()) {
			todo.dequeueAll();
		}
		if(!done.isEmpty()) {
			done.dequeueAll();
		}

	    avgWait = (double)totalWait / m;
	    report.print(n+" processor");
	    if(n==1) {
		report.print(": ");
	    } else {
		report.print("s: ");
	    }
	    report.print("totalWait="+totalWait+", maxWait="+maxWait+", averageWait=");
	    report.printf ("%.2f\n", avgWait);
		// System.out.println("for loop iteration"+n);
	    //    12. end FOR loop
	}
	//    13. close input and output files
	in.close();//NOT ARGS[0]
	report.close();
	trace.close();
    }//END MAIN
   
       
    // PRINTS OUT CURRENT STATE OF SIMULATION
    public static void printState(PrintWriter trace, Queue todo, Queue done, Queue[] checkers) {
		trace.println("0: "+todo+done);
		for(int i = 1; i <= checkers.length; i++) {
		    trace.println(i+": "+checkers[i-1]);
		}
		trace.println();
    }
    //FUNCTION FOR ENQUEUEING: 
    public static void enqueueAll(int time, Queue todo, Queue done, Queue[] checkers) {
	if(((Job)todo.peek()).getArrival() == time) {
	    //could put the whole enqueueing thing in here?
	    if(!todo.isEmpty()) {//IF THERE ARE ELEMENTS IN TODO
		// DO LOOP: GET ALL JOBS ARRIVING NOW!!!
		Job curJob = (Job)todo.peek();//GETS NEXT CUSTOMER
		if(curJob.getArrival()==time) {//IF ARRIVING NOW
		    //   System.out.println("Arriving: "+curJob);
		    //LOOP THROUGH CHECKERS AND FIND LOWEST NUMBER
		    int lowest = 9999;
		    for(int i = 0; i < checkers.length; i++) {
			if(checkers[i].length()<lowest) {
			    lowest = checkers[i].length();
			}
		    }
		    //LOOP THROUGH AGAIN AND FIND INDEX OF THE LOWEST NUMBER
		    int lineIndex = 0;
		    while(checkers[lineIndex].length()!=lowest) {
			lineIndex++;
		    }//NOW lineIndex IS POINTING AT LOWEST, SMALLEST INDEX
		    //PUT THE FRONT JOB INTO THAT LINE
		    checkers[lineIndex].enqueue(todo.dequeue());
		    Job theJob = (Job)checkers[lineIndex].peek();
		    if(theJob.getFinish()==-1) {
			theJob.computeFinishTime(time);
		    }
		    //   printState(todo, done, checkers);
		    //   System.out.println("Job put in line: "+lineIndex);
		    //   if(!todo.isEmpty()) {
		    //   curJob = (Job)todo.peek();//UPDATES FRONT JOB
		    //   }
		}
	    }//END ADDING JOBS
	    //RECURSIVE CALL IF OTHER JOBS ALSO ARRIVING

		// System.out.print("recursion1");
	    if(!todo.isEmpty()) {enqueueAll(time, todo, done, checkers);
		// System.out.println("recursion2");
	    }
	}
    }
    //END ENQUEUEALL
}//END CLASS

// O L D   C O D E =====================
/*
 * while(in.hasNextLine()) {//ADDS CUSTOMERS TO TO-DO QUEUE
    //or use for loop to m-1
    //   int arriv = Integer.parseInt(in.next());
    //   int dur = Integer.parseInt(in.next());
    todo.enqueue(getJob(in));//new Job(arriv, dur)
    //   in.nextLine();
       
       }
       * */
