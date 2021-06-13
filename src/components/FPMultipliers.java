package components;

import java.util.ArrayList;

import Tomasulo.Tomasulo;

public class FPMultipliers {
	private ReservationStation [] stations;
	
	public FPMultipliers() {
		this.stations = new ReservationStation [Tomasulo.mulReserveSpace];
	}
	
	public boolean isEmpty() {
		for (ReservationStation instruction : stations) {
			if (instruction != null)
				return false;
		}
		return true;
	}
	
	public void run() {
		for (ReservationStation instruction : stations) {
			if (instruction != null && instruction.canExec()) {
				instruction.decCycles();
				if (instruction.getInstruction().getExecStartCycle() == 0)
					instruction.getInstruction().setExecStartCycle(Tomasulo.currentCycle);
				if (instruction.getCycles() == 0)
					instruction.getInstruction().setExecEndCycle(Tomasulo.currentCycle);
			}
		}
	}
	
	public int containsFreeStation() {
		for (int i = 0 ; i < stations.length ; i++) {
			ReservationStation instruction = stations[i];
			if (instruction == null)
				return i;
		}
		return -1;
	}
	
	public void addReservation(int index,ReservationStation reservationStation) {
		stations[index] = reservationStation;
	}
	
	public void clearReservation(ReservationStation instruction) {
		for (int i = 0 ; i < stations.length ; i++) {
			if (stations[i] != null && stations[i].equals(instruction)) {
				stations[i] = null;
			}
				
		}
	}
	
	public ArrayList<ReservationStation> readyToWrite(){
		ArrayList<ReservationStation> list = new ArrayList<ReservationStation>();
		
		for (ReservationStation instruction : stations) {
			if (instruction != null && instruction.getCycles() == -1)
				list.add(instruction);
		}	
		return list;
	}
	
	public ArrayList<ReservationStation> dependentOn(String nameRS){
		ArrayList<ReservationStation> list = new ArrayList<ReservationStation>();

		for (ReservationStation instruction : stations) {
			if (instruction != null && (( instruction.getQj() != null && instruction.getQj().equals(nameRS)) || (instruction.getQk() != null && instruction.getQk().equals(nameRS))))
				list.add(instruction);
		}	
		return list;
	}
	
	public ArrayList<ReservationStation> dependentOnOnlyOne(String nameRS){
		ArrayList<ReservationStation> list = new ArrayList<ReservationStation>();

		for (ReservationStation instruction : stations) {
			if ((instruction != null && instruction.getQj() != null && instruction.getQj().equals(nameRS) && instruction.getQk() == null) 
					|| (instruction != null && instruction.getQj() == null && instruction.getQk() != null && instruction.getQk().equals(nameRS)))
				list.add(instruction);
		}	
		return list;
	}
	
	public void print() {
		System.out.println("---------------- Multipliers Reservation Stations ---------------");
		System.out.println("name " + "op " + "Busy " + "VJ " + "VK " + "QJ " + "QK");
		for (ReservationStation instruction : stations) {
			if (instruction != null)
				System.out.println(instruction);
		}
	}
}
