package fr.upmc.simulator.ports;

import java.rmi.RemoteException;

import fr.upmc.components.ComponentI;
import fr.upmc.components.examples.basic_cs.interfaces.URIConsumerI;
import fr.upmc.components.ports.AbstractOutboundPort;
import fr.upmc.simulator.interfaces.SubmissionConsumerI;
import fr.upmc.simulator.tasks.AbstractTask;

public class SubmissionOutBoundPort extends		AbstractOutboundPort
									implements SubmissionConsumerI {
	

	public SubmissionOutBoundPort(String uri,
			ComponentI owner) {
		super(uri,SubmissionConsumerI.class, owner);
		
	}

	@Override
	public void submitTask(AbstractTask task) throws RemoteException {
		// TODO Auto-generated method stub
		((SubmissionConsumerI)this.connector).submitTask(task) ;
		
	}

	@Override
	public void stopTask(AbstractTask task) throws RemoteException {
		// TODO Auto-generated method stub
		((SubmissionConsumerI)this.connector).stopTask(task) ;

		
	}

	@Override
	public void notifyEndTask(AbstractTask task) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
