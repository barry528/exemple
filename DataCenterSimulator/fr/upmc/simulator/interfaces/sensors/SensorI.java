package fr.upmc.simulator.interfaces.sensors;

import java.util.List;

import fr.upmc.components.interfaces.DataOfferedI;
import fr.upmc.simulator.computers.devices.Core;
import fr.upmc.simulator.computers.devices.Processor;
import fr.upmc.simulator.tasks.AbstractTask;
/**
 * This interface provides information on running tasks (computer, processor, core)
 * @author mamads
 *
 */
public interface SensorI  {
	
	/**
	 * current energy consumption
	 * @return int
	 */
	public int produceEnergy();
	
	/**
	 * tasks currently executed on the computer
	 * @return
	 */
	public List<AbstractTask> produceTasks();
	
	/**
	 * processors in the computer
	 * @return
	 */
	public List<Processor> produceProcessors();
	
	/**
	 * cores in the computer
	 * @return
	 */
	public List<Core> produceCores();
	
	/**
	 * processors used by the task
	 * @param task
	 * @return
	 */
	public List<Processor> produceProcessors(AbstractTask task);
	
	/**
	 * cores used by the task
	 * @param task
	 * @return
	 */
	public List<Core> produceCores(AbstractTask task);
	
	/**
	 * range of frequency of core
	 * @param core
	 * @return
	 */
	public int[] produceFrequency(Core core);
	
	/**
	 * current operating frequency
	 * @param core
	 * @return
	 */
	public int produceCurrentFrequency(Core core);

}
