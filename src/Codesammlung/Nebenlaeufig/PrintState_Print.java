package Codesammlung.Nebenlaeufig;

public class PrintState_Print extends Thread{
	private PrintState ps;
	
	public PrintState_Print(PrintState p) {
		this.ps = p;
	}
	
	public void run() {
		while(true) {
			ps.printNewState();
		}
	}

}
