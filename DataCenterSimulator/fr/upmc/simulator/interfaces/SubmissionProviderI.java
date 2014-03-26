package fr.upmc.simulator.interfaces;

import java.rmi.RemoteException;

import fr.upmc.components.interfaces.OfferedI;
import fr.upmc.simulator.tasks.AbstractTask;
/**
 * Job submission interface
 * @author mamads
 *
 */

public interface SubmissionProviderI extends OfferedI {
	
	/**
	 * submit a task
	 * @param task
	 */
	public void submitTask(AbstractTask task)throws RemoteException;
	
	/**
	 * stop task execution
	 * @param task
	 */
	public void stopTask(AbstractTask task) throws RemoteException;
	
	/**
	 * notifies the end of a task
	 * @param task
	 */
	public void notifyEndTask(AbstractTask task)throws RemoteException;

}
