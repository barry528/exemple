package fr.upmc.simulator.interfaces;

import java.rmi.RemoteException;

import fr.upmc.components.interfaces.OfferedI;
import fr.upmc.simulator.tasks.AbstractTask;

public interface EndTaskProviderI extends OfferedI {
	
	public void notifyEndTask(AbstractTask task) throws RemoteException;

}
