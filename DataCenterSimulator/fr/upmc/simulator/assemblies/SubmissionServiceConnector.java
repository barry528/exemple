package fr.upmc.simulator.assemblies;

import java.rmi.RemoteException;

import fr.upmc.components.connectors.AbstractConnector;
import fr.upmc.components.examples.basic_cs.interfaces.URIConsumerI;
import fr.upmc.components.examples.basic_cs.interfaces.URIProviderI;
import fr.upmc.simulator.interfaces.SubmissionConsumerI;
import fr.upmc.simulator.interfaces.SubmissionProviderI;
import fr.upmc.simulator.tasks.AbstractTask;

public class SubmissionServiceConnector
extends		AbstractConnector
implements	SubmissionConsumerI{


	@Override
	public void submitTask(AbstractTask task) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			((SubmissionProviderI)this.offering).submitTask(task) ;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void stopTask(AbstractTask task) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			((SubmissionProviderI)this.offering).stopTask(task) ;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void notifyEndTask(AbstractTask task) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
