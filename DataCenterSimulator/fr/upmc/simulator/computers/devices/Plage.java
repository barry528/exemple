package fr.upmc.simulator.computers.devices;

/**
 * frequency range
 * @author mamads
 *
 */
public class Plage {
	/**
	 * min frequency
	 */
	private int minFreq;
	/**
	 * max frequency
	 */
	private int maxFreq;
	
	public Plage(){
		super();
	}
	
	public int getMinFreq() {
		return minFreq;
	}

	public void setMinFreq(int minFreq) {
		this.minFreq = minFreq;
	}

	public int getMaxFreq() {
		return maxFreq;
	}

	public void setMaxFreq(int maxFreq) {
		this.maxFreq = maxFreq;
	}

	
}
