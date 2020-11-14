package Codesammlung.Nebenlaeufig;

import java.util.ArrayList;
import java.util.Collections;

public class SyncCollections {
	

	public static void main (String[] args) {

		// List<> ist ein Interface und kann als solches nicht direkt instanziiert werden. Es können nur Objekte instanziiert werden, die es implementieren, z.B. ArrayList
		//List<Integer> Test = New List<Integer>();
		
		ArrayList<Integer> unsyncList = new ArrayList<Integer>();
		ArrayList<Integer>syncList = (ArrayList<Integer>) Collections.synchronizedList(unsyncList);
	
		// Umwandlungsmöglichkeit in Kurzform:
		@SuppressWarnings("unused") Integer[] a = syncList.toArray(new Integer[0]);
		
		// Alternative Langform:
		Integer[] b; // Größe 0
		b = new Integer[syncList.size()];
		// an dieser Stelle könnte ein anderer Thread die Liste bearbeiten, z.B. Elemente hinzu fügen. Daher im Anschluss eine synchronized-Version
		syncList.toArray(b); // b hat ja genau die richtige Größe
		
		// Synchronisiert
		Integer[] c;
		synchronized (syncList) {
			c = new Integer[syncList.size()];
			syncList.toArray(c);
		}
	}
}
