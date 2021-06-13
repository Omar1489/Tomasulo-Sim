package Tomasulo;

import java.util.ArrayList;

import components.FPAdders;
import components.FPMultipliers;
import components.FPRegisters;
import components.InstructionQueue;
import components.Memory;
import components.ReservationStation;
import stages.Exec;
import stages.Issue;
import stages.Write;
//import java.util.*;

public class Tomasulo {
	public static FPAdders fpAdders; // done
	public static FPMultipliers fpMultipliers; // done
	public static FPRegisters fpRegisters; // done
	public static InstructionQueue instructionQueue; // done
	public static Memory memory; // done
	public static Issue issueStage; // done
	public static Exec executeStage; // done
	public static Write writeStage; // done
	public static int addReserveSpace; // done
	public static int mulReserveSpace; // done
	public static int loadReserveSpace; // done
	public static int storeReserveSpace; // done
	public static int addCycles; // done
	public static int subCycles; // done
	public static int mulCycles; // done
	public static int divCycles; // done
	public static int loadCycles; // done
	public static int storeCycles; // done
	public static int currentCycle = 0; // done
	
	public static void run() {
		while(!Tomasulo.instructionQueue.Done()) {
			currentCycle++;
			System.out.println("--------------------------------Cycle " + currentCycle + " --------------------------------");
			issueStage.run();
			executeStage.run();
			writeStage.run();
			Tomasulo.fpAdders.print();
			Tomasulo.fpMultipliers.print();
			Tomasulo.memory.printLoad();
			Tomasulo.memory.printStore();
			Tomasulo.fpRegisters.print();
			System.out.println();
		}
		System.out.println("------------------- FINAL TRACING TABLE TABLE ---------------");
		instructionQueue.print();
	}
	
	public static void main(String[] args) {
		// Test Case 1
		addReserveSpace =3;
		mulReserveSpace =2;
		loadReserveSpace =3;
		storeReserveSpace = 0;
		fpRegisters = new FPRegisters();
		fpAdders = new FPAdders();
		fpMultipliers = new FPMultipliers();
		memory = new Memory();
		issueStage = new Issue();
		executeStage = new Exec();
		writeStage = new Write();
		addCycles = 2;
		subCycles = 2;
		mulCycles = 10;
		divCycles = 40;
		loadCycles = 2;
		storeCycles = 0;
		
//		Test Case 2 
//		addReserveSpace =3;
//		mulReserveSpace =2;
//		loadReserveSpace =0;
//		storeReserveSpace = 0;
//		fpRegisters = new FPRegisters();
//		fpAdders = new FPAdders();
//		fpMultipliers = new FPMultipliers();
//		memory = new Memory();
//		issueStage = new Issue();
//		executeStage = new Exec();
//		writeStage = new Write();
//		addCycles = 4;
//		subCycles = 0;
//		mulCycles = 6;
//		divCycles = 9;
//		loadCycles = 0;
//		storeCycles = 0;
		
		instructionQueue = new InstructionQueue();
		Tomasulo.run();
	}
	
	
	public static void printArray(ArrayList<ReservationStation> list) {
		for (ReservationStation i : list) {
			System.out.println(i);
		}
	}
	
	
}
