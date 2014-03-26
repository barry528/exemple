package fr.upmc.simulator.tasks;
/**
 * this enum represents the profiles of request
 * @author mamads
 *
 */
public class Profil {
	/**
	 * number of quequest min
	 */
	private int minRequest;
	
	/**
	 * number of request max
	 */
	private int maxRequest;
	
	/**
	 * number max of instructions of requests
	 */
	private int instructions;
	
	/**
	 * interval arrival of Requests
	 */
	private long intervalle;
	
	public Profil(int max,int ins,long inter,int instructions){
		this.maxRequest=max;
		this.instructions=ins;
		this.intervalle=inter;	
		this.instructions=instructions;
	}
	
	public int getMinRequest() {
		return minRequest;
	}
	public void setMinRequest(int minRequest) {
		this.minRequest = minRequest;
	}
	public int getMaxRequest() {
		return maxRequest;
	}
	public void setMaxRequest(int maxRequest) {
		this.maxRequest = maxRequest;
	}
	public int getInstructions() {
		return instructions;
	}
	public void setInstructions(int instructions) {
		this.instructions = instructions;
	}
	public long getIntervalle() {
		return intervalle;
	}
	public void setIntervalle(long intervalle) {
		this.intervalle = intervalle;
	}

}
