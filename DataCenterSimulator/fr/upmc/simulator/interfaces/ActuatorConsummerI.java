package fr.upmc.simulator.interfaces;

import fr.upmc.components.interfaces.RequiredI;
import fr.upmc.simulator.computers.devices.Core;
import fr.upmc.simulator.computers.devices.Processor;
import fr.upmc.simulator.tasks.AbstractTask;

public interface ActuatorConsummerI extends RequiredI{
	/**
	 * modify core frequency
	 * @param core
	 * @param frequence
	 */
	public void changeFrequency(Processor processor, int frequence);
	
	/**
	 * add a processor to the task
	 * @param task
	 */
	public void addProcessor(AbstractTask task,Processor processor);
	
	/**
	 * add a core to the task
	 * @param task
	 */
	public void addCore(AbstractTask task,Core core);
	
	/**
	 * remove a processor to the task
	 * @param task
	 */
	public void substractProcessor(AbstractTask task,Processor processor);
	
	/**
	 * remove a core to the task
	 * @param task
	 */
	public void substractCore(AbstractTask task,Core core);

}
