package components;

import Tomasulo.Instruction;
import Tomasulo.Tomasulo;

public class ReservationStation {
	private boolean busy = false;
	private String name; 
	private String opCode;
	private String vj = null;
	private String vk = null;
	private String qj = null;
	private String qk = null;
	private String address = null;
	private int cycles;
	private Instruction instruction;
		
	// Arithmetic instructions
	public ReservationStation(boolean busy, String name, String opCode, String vj, String vk, String qj, String qk, int cycles, Instruction instruction) {
		this.busy = busy;
		this.name = name;
		this.opCode = opCode;
		this.vj = vj;
		this.vk = vk;
		this.qj = qj;
		this.qk = qk;
		this.cycles = cycles;
		this.instruction = instruction;
	}
	
	// Load instructions
	public ReservationStation(boolean busy, String name, String opCode, String address, int cycles, Instruction instruction) {
		super();
		this.busy = busy;
		this.name = name;
		this.opCode = opCode;
		this.address = address;
		this.cycles = cycles;
		this.instruction = instruction;
	}
	
	// Store instructions
	public ReservationStation(boolean busy, String name, String opCode, String vj,  String qj, String address, int cycles, Instruction instruction) {
		this.busy = busy;
		this.name = name;
		this.opCode = opCode;
		this.vj = vj;
		this.qj = qj;
		this.address = address;
		this.cycles = cycles;
		this.instruction = instruction;
	}

	public boolean isBusy() {
		return busy;
	}
	
	public void setBusy(boolean busy) {
		this.busy = busy;
	}
	
	public String getOpCode() {
		return opCode;
	}
		
	public String getVj() {
		return vj;
	}
	
	public void setVj(String vj) {
		this.vj = vj;
	}
	
	public String getVk() {
		return vk;
	}
	
	public void setVk(String vk) {
		this.vk = vk;
	}
	
	public String getQj() {
		return qj;
	}
	
	public void setQj(String qj) {
		this.qj = qj;
	}
	
	public String getQk() {
		return qk;
	}
	
	public void setQk(String qk) {
		this.qk = qk;
	}
	
	public String getAddress() {
		return address;
	}

	public Instruction getInstruction() {
		return instruction;
	}

	// decrements remaining cycles by one
	public void decCycles() {
		cycles--;
	}
	
	public int getCycles() {
		return cycles;
	}

	// returns name of the reservation station (M1,MM2)
	public String getName() {
		return name;
	}
	
	// checks if the instruction is ready to execute
	public boolean canExec() {
		return ( ((opCode=="LW") || (qj==null && qk==null) || (qj==null && opCode=="SW")) && cycles >= 0 && Tomasulo.currentCycle != instruction.getIssueCycle());
	}
	
	// checks if the instructor is ready to write
	public boolean isReadyWrite() {
		return (cycles==-1);
	}

	@Override
	public String toString() {
		String output = name + " " +opCode + " " + busy  +" " ;
		if (opCode=="ADD" || opCode == "SUB" || opCode == "MUL" || opCode == "DIV") {
			output += vj + " " + vk + " " + qj + " " + qk; 
		}
		else if (opCode == "LW") {
			output += address; 
		}
		else
			output += vj + " " + qj + " " + address;		
		return output;
	}	
}