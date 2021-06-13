package stages;

import java.util.ArrayList;
import Tomasulo.Tomasulo;
import components.ReservationStation;

public class Write {

	private ReservationStation bestToBeWritten;
	private int maxNumberOfDependencies = 0;

	public void run() {
		ArrayList<ReservationStation> fpAddersReadyToBeWritten = Tomasulo.fpAdders.readyToWrite();
		ArrayList<ReservationStation> fpMultipliersReadyToBeWritten = Tomasulo.fpMultipliers.readyToWrite();
		ArrayList<ReservationStation> loadReadyToBeWritten = Tomasulo.memory.loadReadyToWrite();
		ArrayList<ReservationStation> storeReadyToBeWritten = Tomasulo.memory.storeReadyToWrite();
		ArrayList<ReservationStation> allReadyToBeWritten = new ArrayList<ReservationStation>();		
		
		allReadyToBeWritten.addAll(fpAddersReadyToBeWritten);
		allReadyToBeWritten.addAll(fpMultipliersReadyToBeWritten);
		allReadyToBeWritten.addAll(loadReadyToBeWritten);
		allReadyToBeWritten.addAll(storeReadyToBeWritten);
		
		for (ReservationStation readyRS : allReadyToBeWritten) {
			ArrayList<ReservationStation> adderDependencies = Tomasulo.fpAdders.dependentOnOnlyOne(readyRS.getName());
			ArrayList<ReservationStation> multiplierDependencies = Tomasulo.fpMultipliers.dependentOnOnlyOne(readyRS.getName());
			ArrayList<ReservationStation> storeDependencies = Tomasulo.memory.storeDependentOn(readyRS.getName());
			int numberOfDependencies = adderDependencies.size() + multiplierDependencies.size() + storeDependencies.size();
			if (numberOfDependencies > maxNumberOfDependencies) {
				maxNumberOfDependencies = numberOfDependencies;
				bestToBeWritten = readyRS;
			}
		}
		
		if (bestToBeWritten == null && allReadyToBeWritten.size() > 0) {
			bestToBeWritten = allReadyToBeWritten.get(0);
		}
		
		if (bestToBeWritten != null) {
			ArrayList<ReservationStation> addInstructionsToBeReleased = Tomasulo.fpAdders.dependentOn(bestToBeWritten.getName());
			ArrayList<ReservationStation> multiplierInstructionsToBeReleased = Tomasulo.fpMultipliers.dependentOn(bestToBeWritten.getName());
			ArrayList<ReservationStation> storeInstructionsToBeReleased = Tomasulo.memory.storeDependentOn(bestToBeWritten.getName());
		
			for(int i = 0; i < 32 ; i++) {
				if(Tomasulo.fpRegisters.getAt(i)!= null && Tomasulo.fpRegisters.getAt(i).equals(bestToBeWritten.getName())) {
					Tomasulo.fpRegisters.clearAt(i);
				}
			}
			
			for (ReservationStation instruction : addInstructionsToBeReleased) {
				if (instruction.getQj() != null && instruction.getQj().equals(bestToBeWritten.getName())) {
					instruction.setQj(null);
					instruction.setVj(instruction.getInstruction().getRs());
				}
				
				if (instruction.getQk() != null && instruction.getQk().equals(bestToBeWritten.getName())) {
					instruction.setQk(null);
					instruction.setVk(instruction.getInstruction().getRt());
				}
			}
			
			for (ReservationStation instruction : multiplierInstructionsToBeReleased) {
				if (instruction.getQj() != null && instruction.getQj().equals(bestToBeWritten.getName())) {
					instruction.setQj(null);
					instruction.setVj(instruction.getInstruction().getRs());
				}
				
				if (instruction.getQk() != null && instruction.getQk().equals(bestToBeWritten.getName())) {
					instruction.setQk(null);
					instruction.setVk(instruction.getInstruction().getRt());
				}
			}
			
			for (ReservationStation instruction : storeInstructionsToBeReleased) {
				if (instruction.getQj() != null && instruction.getQj().equals(bestToBeWritten.getName())) {
					instruction.setQj(null);
					instruction.setVj(instruction.getInstruction().getRt());
				}
			}
			bestToBeWritten.getInstruction().setWriteCycle(Tomasulo.currentCycle);
			Tomasulo.fpAdders.clearReservation(bestToBeWritten);
			Tomasulo.fpMultipliers.clearReservation(bestToBeWritten);
			Tomasulo.memory.clearReservationload(bestToBeWritten);
			Tomasulo.memory.clearReservationstore(bestToBeWritten);
			
		}
		reset();
	}
	
	private void reset() {
		bestToBeWritten = null;
		maxNumberOfDependencies = 0;
	}

	public boolean isDone() {
		ArrayList<ReservationStation> fpAddersReadyToBeWritten = Tomasulo.fpAdders.readyToWrite();
		ArrayList<ReservationStation> fpMultipliersReadyToBeWritten = Tomasulo.fpMultipliers.readyToWrite();
		ArrayList<ReservationStation> loadReadyToBeWritten = Tomasulo.memory.loadReadyToWrite();
		ArrayList<ReservationStation> storeReadyToBeWritten = Tomasulo.memory.storeReadyToWrite();
		ArrayList<ReservationStation> allReadyToBeWritten = new ArrayList<ReservationStation>();

		allReadyToBeWritten.addAll(fpAddersReadyToBeWritten);
		allReadyToBeWritten.addAll(fpMultipliersReadyToBeWritten);
		allReadyToBeWritten.addAll(loadReadyToBeWritten);
		allReadyToBeWritten.addAll(storeReadyToBeWritten);
		
		if (allReadyToBeWritten.size()  > 0)
			return false;
		return true;
	}

}
