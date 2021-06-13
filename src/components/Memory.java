package components;

import java.util.ArrayList;

import Tomasulo.Tomasulo;

public class Memory {
	private ReservationStation [] loadBuffers;
	private ReservationStation [] storeBuffers;
	
	public Memory() {
		loadBuffers = new ReservationStation [Tomasulo.loadReserveSpace];
		storeBuffers = new ReservationStation [Tomasulo.storeReserveSpace];
	}
	
	public boolean isEmpty() {
		for (ReservationStation instruction : loadBuffers) {
			if (instruction != null)
				return false;
		}
		for (ReservationStation instruction : storeBuffers) {
			if (instruction != null)
				return false;
		}
		return true;
	}
	
	public void runLoad() {
		for (ReservationStation instruction : loadBuffers) {
			if (instruction != null && instruction.canExec()) {
				instruction.decCycles();
				if (instruction.getInstruction().getExecStartCycle() == 0)
					instruction.getInstruction().setExecStartCycle(Tomasulo.currentCycle);
				if (instruction.getCycles() == 0)
					instruction.getInstruction().setExecEndCycle(Tomasulo.currentCycle);
			}
		}
	}
	
	public void runStore() {
		for (ReservationStation instruction : storeBuffers) {
			if (instruction != null && instruction.canExec()) {
				instruction.decCycles();
				if (instruction.getInstruction().getExecStartCycle() == 0)
					instruction.getInstruction().setExecStartCycle(Tomasulo.currentCycle);
				if (instruction.getCycles() == 0)
					instruction.getInstruction().setExecEndCycle(Tomasulo.currentCycle);
			}
		}
	}
	
	public int loadContainsFreeStation() {
		for (int i = 0 ; i < loadBuffers.length ; i++) {
			ReservationStation instruction = loadBuffers[i];
			if (instruction == null)
				return i;
		}
		return -1;
	}
	
	public int storeContainsFreeStation() {
		for (int i = 0 ; i < storeBuffers.length ; i++) {
			ReservationStation instruction = storeBuffers[i];
			if (instruction == null)
				return i;
		}
		return -1;
	}
	
	public void addReservationLoad(int index ,ReservationStation reservationStation) {
		loadBuffers[index] = reservationStation;
	}
	
	public void addReservationStore(int index,ReservationStation reservationStation) {
		storeBuffers[index] = reservationStation;
	}
	
	public void clearReservationload(ReservationStation instruction) {
		for (int i = 0 ; i < loadBuffers.length ; i++) {
			if (loadBuffers[i] != null && loadBuffers[i].equals(instruction)) {
				loadBuffers[i] = null;
			}
		}
	}
	
	public void clearReservationstore(ReservationStation instruction) {
		for (int i = 0 ; i < storeBuffers.length ; i++) {
			if (storeBuffers[i] != null && storeBuffers[i].equals(instruction)) {
				storeBuffers[i] = null;
			}
		}
	}
	
	public ArrayList<ReservationStation> loadReadyToWrite(){
		ArrayList<ReservationStation> list = new ArrayList<ReservationStation>();
		
		for (ReservationStation instruction : loadBuffers) {
			if (instruction != null && instruction.getCycles() == -1)
				list.add(instruction);
		}	
		return list;
	}
	
	public ArrayList<ReservationStation> storeReadyToWrite(){
		ArrayList<ReservationStation> list = new ArrayList<ReservationStation>();
		
		for (ReservationStation instruction : storeBuffers) {
			if (instruction != null && instruction.getCycles() == -1)
				list.add(instruction);
		}	
		return list;
	}
	
	public ArrayList<ReservationStation> storeDependentOn(String nameRS){
		ArrayList<ReservationStation> list = new ArrayList<ReservationStation>();

		for (ReservationStation instruction : storeBuffers) {
			if (instruction != null && instruction.getQj() != null && instruction.getQj().equals(nameRS))
				list.add(instruction);
		}	
		return list;
	}
	
	public void printLoad() {
		System.out.println("---------------- Load Reservation Stations ---------------");
		System.out.println("name " + "op " + "Busy " + "Address");
		for (ReservationStation instruction : loadBuffers) {
			if (instruction != null)
				System.out.println(instruction);
		}
	}
	
	public void printStore() {
		System.out.println("---------------- Store Reservation Stations ---------------");
		System.out.println("name " + "op " + "Busy " + "VJ " + "QJ " + "Address");
		for (ReservationStation instruction : storeBuffers) {
			if (instruction != null)
				System.out.println(instruction);
		}
	}
}
