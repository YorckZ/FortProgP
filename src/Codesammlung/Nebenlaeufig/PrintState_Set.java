package Codesammlung.Nebenlaeufig;

public class PrintState_Set extends Thread{
	private PrintState ps;
	
	public PrintState_Set(PrintState p) {
		this.ps = p;
	}
	
	public void run() {
		while(true) {
			ps.setValue(50);
		}
	}
}
