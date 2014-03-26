package fr.upmc.simulator.interfaces;

import java.rmi.RemoteException;

import fr.upmc.components.interfaces.RequiredI;
import fr.upmc.simulator.tasks.AbstractTask;

public interface SubmissionConsumerI extends RequiredI{
	
		
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
