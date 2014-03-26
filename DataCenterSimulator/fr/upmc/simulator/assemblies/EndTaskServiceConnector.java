package fr.upmc.simulator.assemblies;

import java.rmi.RemoteException;

import fr.upmc.components.connectors.AbstractConnector;
import fr.upmc.simulator.interfaces.EndTaskConsummerI;
import fr.upmc.simulator.interfaces.EndTaskProviderI;
import fr.upmc.simulator.tasks.AbstractTask;

public class EndTaskServiceConnector extends AbstractConnector 
										implements EndTaskConsummerI {

	@Override
	public void notifyEndTask(AbstractTask task) {
		// TODO Auto-generated method stub
		try {
			((EndTaskProviderI)this.offering).notifyEndTask(task);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
