package stages;

import Tomasulo.Instruction;
import Tomasulo.Tomasulo;
import components.ReservationStation;

public class Issue {
	private Instruction instruction;
	private int freeSpaceIndex;
	private String vj = null;
	private String vk = null;
	private String qj = null;
	private String qk = null;
	private int cycles = 0;

	public void run() {
		if (!Tomasulo.instructionQueue.isEmpty()) {
			
			instruction = Tomasulo.instructionQueue.fetchInstruction();
			if (instruction.getOpCode().equals("ADD") || instruction.getOpCode().equals("SUB")) {
				freeSpaceIndex = Tomasulo.fpAdders.containsFreeStation();
				if (freeSpaceIndex != -1) {
					String rsDependency = registerDependency(instruction.getRs());
					String rtDependency = registerDependency(instruction.getRt());

					if (rsDependency == null) 
						vj = instruction.getRs();
					else 
						qj = rsDependency;

					if (rtDependency == null)
						vk = instruction.getRt();
					else
						qk = rtDependency;

					if (instruction.getOpCode() == "ADD")
						cycles = Tomasulo.addCycles;
					else
						cycles = Tomasulo.subCycles;

					ReservationStation reservationStation = new ReservationStation(true, "A"+freeSpaceIndex, instruction.getOpCode(), vj, vk, qj, qk, cycles, instruction);
					Tomasulo.fpAdders.addReservation(freeSpaceIndex, reservationStation);
					Tomasulo.instructionQueue.incCounter();
					Tomasulo.fpRegisters.reserveAt(Integer.parseInt(instruction.getRd().substring(1, instruction.getRd().length())), "A"+freeSpaceIndex);
					instruction.setIssueCycle(Tomasulo.currentCycle);
				}
			}
			else if (instruction.getOpCode().equals("MUL") || instruction.getOpCode().equals("DIV")) {
				freeSpaceIndex = Tomasulo.fpMultipliers.containsFreeStation();
				if (freeSpaceIndex != -1) {
					String rsDependency = registerDependency(instruction.getRs());
					String rtDependency = registerDependency(instruction.getRt());
					
					
					if (rsDependency == null) 
						vj = instruction.getRs();
					else 
						qj = rsDependency;

					if (rtDependency == null)
						vk = instruction.getRt();
					else
						qk = rtDependency;

					if (instruction.getOpCode().equals("MUL"))
						cycles = Tomasulo.mulCycles;
					else
						cycles = Tomasulo.divCycles;
					
					ReservationStation reservationStation = new ReservationStation(true, "M"+freeSpaceIndex, instruction.getOpCode(), vj, vk, qj, qk, cycles, instruction);
					Tomasulo.fpMultipliers.addReservation(freeSpaceIndex, reservationStation);
					Tomasulo.instructionQueue.incCounter();
					Tomasulo.fpRegisters.reserveAt(Integer.parseInt(instruction.getRd().substring(1, instruction.getRd().length())), "M"+freeSpaceIndex);
					instruction.setIssueCycle(Tomasulo.currentCycle);
				}
			}
			else if (instruction.getOpCode().equals("SW")) {
				freeSpaceIndex = Tomasulo.memory.storeContainsFreeStation();
				if (freeSpaceIndex != -1) {
					String rtDependency = registerDependency(instruction.getRt());

					if (rtDependency == null)
						vj = instruction.getRt();
					else
						qj = rtDependency;

					cycles = Tomasulo.storeCycles;

					ReservationStation reservationStation = new ReservationStation(true, "S"+freeSpaceIndex, instruction.getOpCode(), vj,qj , instruction.getImmediate()+"",
							cycles, instruction);
					Tomasulo.memory.addReservationStore(freeSpaceIndex, reservationStation);
					Tomasulo.instructionQueue.incCounter();
					instruction.setIssueCycle(Tomasulo.currentCycle);
				}
			}
			else  {
				freeSpaceIndex = Tomasulo.memory.loadContainsFreeStation();
				if (freeSpaceIndex != -1) {
					ReservationStation reservationStation = new ReservationStation(true, "L"+freeSpaceIndex, instruction.getOpCode(), instruction.getImmediate()+"", 
							Tomasulo.loadCycles, instruction);
					Tomasulo.memory.addReservationLoad(freeSpaceIndex, reservationStation);
					Tomasulo.instructionQueue.incCounter();
					Tomasulo.fpRegisters.reserveAt(Integer.parseInt(instruction.getRt().substring(1, instruction.getRt().length())), "L"+freeSpaceIndex);
					instruction.setIssueCycle(Tomasulo.currentCycle);
				}
			}
		}
		reset();
	}

	private String registerDependency(String reigster) {
		int registerIndex = Integer.parseInt(reigster.substring(1, reigster.length()));
		return Tomasulo.fpRegisters.getAt(registerIndex);
	}

	private void reset() {
		vj = null;
		vk = null;
		qj = null;
		qk = null;
		cycles = 0;
	}
}

