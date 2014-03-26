package fr.upmc.simulator.ports;

import fr.upmc.components.ComponentI;
import fr.upmc.components.ports.AbstractOutboundPort;
import fr.upmc.simulator.interfaces.EndTaskConsummerI;
import fr.upmc.simulator.interfaces.SubmissionConsumerI;
import fr.upmc.simulator.tasks.AbstractTask;

public class EndTaskOutBoundPort extends	AbstractOutboundPort 
									implements EndTaskConsummerI {
	
	public EndTaskOutBoundPort(String uri,
			ComponentI owner) {
		super(uri,EndTaskConsummerI.class, owner);
		
	}

	@Override
	public void notifyEndTask(AbstractTask task) {
		// TODO Auto-generated method stub
		((EndTaskConsummerI)this.connector).notifyEndTask(task) ;
	}

}
