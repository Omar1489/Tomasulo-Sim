package components;

public class FPRegisters {
	private String [] registers;
	
	public FPRegisters() {
		registers = new String [32];
	}
	
	public String getAt(int index) {
		return registers[index];
	}
	
	public void clearAt(int index) {
		registers[index] = null; 
	}
	
	public void reserveAt(int index,String reservationName) {
		registers[index] = reservationName;
	}
	
	public void print() {
		System.out.println("---------------- FP Registers---------------");
		for (int i = 0 ; i < registers.length ; i++) {
			if (registers[i] == null)
				System.out.println("F" +i + " : " );
			else 
				System.out.println("F" +i + " : " + registers[i]);
		}
	}
}
