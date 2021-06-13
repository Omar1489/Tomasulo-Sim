package stages;

import Tomasulo.Tomasulo;

public class Exec {
	
	public void run() {
		Tomasulo.fpAdders.run();
		Tomasulo.fpMultipliers.run();
		Tomasulo.memory.runLoad();
		Tomasulo.memory.runStore();
	}
}
