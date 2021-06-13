package Tomasulo;

public class Instruction {
	String opCode = "";
	String rd = "";
	String rs = "";
	String rt = "";
	int immediate = 0;
	int issueCycle = 0;
	int execStartCycle = 0;
	int execEndCycle = 0;
	int WriteCycle = 0;
	
	public Instruction(String opCode, String rd, String rs, String rt) {
		this.opCode = opCode;
		this.rd = rd;
		this.rs = rs;
		this.rt = rt;
	}

	public Instruction(String opCode, String rs, String rt, int immediate) {
		this.opCode = opCode;
		this.rs = rs;
		this.rt = rt;
		this.immediate = immediate;
	}

	@Override
	public String toString() {
		if (opCode == "LW" || opCode == "SW") {
			return opCode + " " + rt + " " + immediate + "($" + rs + ")" + " " + issueCycle + " " + execStartCycle + " " + execEndCycle + " " + WriteCycle;
		}
		return opCode + " " + rd + " " + rs + " " + rt + " " + issueCycle + " " + execStartCycle + " " + execEndCycle + " " + WriteCycle;
	}

	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	public String getRd() {
		return rd;
	}

	public void setRd(String rd) {
		this.rd = rd;
	}

	public String getRs() {
		return rs;
	}

	public void setRs(String rs) {
		this.rs = rs;
	}

	public String getRt() {
		return rt;
	}

	public void setRt(String rt) {
		this.rt = rt;
	}

	public int getImmediate() {
		return immediate;
	}

	public void setImmediate(int immediate) {
		this.immediate = immediate;
	}

	public int getIssueCycle() {
		return issueCycle;
	}

	public void setIssueCycle(int issueCycle) {
		this.issueCycle = issueCycle;
	}

	public int getExecStartCycle() {
		return execStartCycle;
	}

	public void setExecStartCycle(int execStartCycle) {
		this.execStartCycle = execStartCycle;
	}

	public int getExecEndCycle() {
		return execEndCycle;
	}

	public void setExecEndCycle(int execEndCycle) {
		this.execEndCycle = execEndCycle;
	}

	public int getWriteCycle() {
		return WriteCycle;
	}

	public void setWriteCycle(int writeCycle) {
		WriteCycle = writeCycle;
	}
	
	
}
