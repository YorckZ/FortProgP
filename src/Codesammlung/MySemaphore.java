package Codesammlung;

import java.util.ArrayList;

public class MySemaphore {
	private int Capacity;
	private ArrayList<Object> Queue = new ArrayList<Object>(); // Warteschlange

	public MySemaphore(int capacity) {
		this.Capacity = capacity;
	}
	
	public void P(Object o) {
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
		MySemaphore s = new MySemaphore(1);
		
		Object someProcess = new Object();
		s.P(someProcess);
		// do stuff
		s.V();
	}
	
	
	
}
