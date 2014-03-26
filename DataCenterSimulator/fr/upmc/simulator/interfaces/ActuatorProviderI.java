package fr.upmc.simulator.interfaces;

import java.rmi.RemoteException;

import fr.upmc.components.interfaces.OfferedI;
import fr.upmc.simulator.computers.devices.Core;
import fr.upmc.simulator.computers.devices.Processor;
import fr.upmc.simulator.tasks.AbstractTask;

/**
 * This interface allows you to manipulate resources (processors and cores)
 * @author mamads
 *
 */

public interface ActuatorProviderI extends OfferedI {
	
	/**
	 * modify core frequency
	 * @param core
	 * @param frequence
	 */
	public void changeFrequency(Processor processor, int frequence) throws RemoteException;
	
	/**
	 * add a processor to the task
	 * @param task
	 */
	public void addProcessor(AbstractTask task,Processor processor) throws RemoteException;
	
	/**
	 * add a core to the task
	 * @param task
	 */
	public void addCore(AbstractTask task,Core core) throws RemoteException;
	
	/**
	 * remove a processor to the task
	 * @param task
	 */
	public void substractProcessor(AbstractTask task,Processor processor) throws RemoteException;
	
	/**
	 * remove a core to the task
	 * @param task
	 */
	public void substractCore(AbstractTask task,Core core) throws RemoteException;

}
