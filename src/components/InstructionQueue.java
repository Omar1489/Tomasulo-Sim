package components;

import java.util.ArrayList;
import Tomasulo.Instruction;

public class InstructionQueue {
	ArrayList<Instruction> instructions = new ArrayList<Instruction>();
	int nextInLine = 0;

	public InstructionQueue() {
		// Test Case 1 
		instructions.add(new Instruction("LW", "R2", "F6", 32));
		instructions.add(new Instruction("LW", "R3", "F2", 44));
		instructions.add(new Instruction("MUL", "F0", "F2", "F4"));
		instructions.add(new Instruction("SUB", "F8", "F2", "F6"));
		instructions.add(new Instruction("DIV", "F10", "F0", "F6"));
		instructions.add(new Instruction("ADD", "F6", "F8", "F2"));
		
		// Test Case 2
//		instructions.add(new Instruction("MUL", "F3", "F1", "F2"));
//		instructions.add(new Instruction("ADD", "F5", "F3", "F4"));
//		instructions.add(new Instruction("ADD", "F7", "F2", "F6"));
//		instructions.add(new Instruction("ADD", "F10", "F8", "F9"));
//		instructions.add(new Instruction("MUL", "F11", "F7", "F10"));
//		instructions.add(new Instruction("ADD", "F5", "F5", "F11"));
	}

	public Instruction fetchInstruction() {
		return instructions.get(nextInLine);
	}

	public void incCounter() {
		nextInLine++;
	}

	public boolean isEmpty() {
		return nextInLine == instructions.size();
	}

	public boolean Done() {
		for (Instruction instruction : instructions) {
			if (instruction.getWriteCycle() == 0)
				return false;
		}
		return true;
	}

	public void print() {
		for (Instruction instruction : instructions) {
			System.out.println(instruction.toString());
		}
		System.out.println();
	}
}
