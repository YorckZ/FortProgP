package Codesammlung.Nebenlaeufig;

import java.util.ArrayList;

public class ExampleSemaphore {
	private int Capacity;
	private ArrayList<Object> Queue = new ArrayList<Object>(); // Warteschlange

	public ExampleSemaphore(int capacity) {
		this.Capacity = capacity;
	}
	
	public void P(Object o) { // Process o asks for access
		if (Capacity >= 1) {
			Capacity--;
		}else {
			Queue.add(o);
		}
	}
	
	public void V() {
		if (!Queue.isEmpty()) {
			// queue is not empty
			doSomething(Queue.get(0)); // call top position of queue
			Queue.remove(0); // remove top position from queue
		}else {
			// queue is empty
			Capacity++;
		}
	}
	
	private void doSomething (Object o) {
		// do something really important
	}
	
	
	
	
	public static void main (String[] args) {
		ExampleSemaphore s = new ExampleSemaphore(1);
		Object someProcess = new Object();
		
		s.P(someProcess);
			// do stuff
		s.V();
	}
	
	
	
}
