package fr.upmc.simulator.interfaces;

import fr.upmc.components.interfaces.OfferedI;
/**
 * Tasks generator interface
 * @author mamads
 *
 */

public interface TaskGeneratorI extends		OfferedI {
	
	/**
	 * generate a task
	 * @return
	 */
	public TaskI gernerate();

}
