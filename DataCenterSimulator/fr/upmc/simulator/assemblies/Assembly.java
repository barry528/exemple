package fr.upmc.simulator.assemblies;

import fr.upmc.components.assemblies.AbstractAssembly;
import fr.upmc.components.connectors.ConnectionBuilder;
import fr.upmc.components.connectors.ConnectorI;
import fr.upmc.components.connectors.DataConnector;
import fr.upmc.simulator.components.AdmissionController;
import fr.upmc.simulator.components.Computer;



public class Assembly extends AbstractAssembly {

	protected static final String	SubOutboundPortURI = "suboport" ;
	protected static final String	SubInboundPortURI = "subiport" ;
	
	protected static final String	ActOutboundPortURI = "actoport" ;
	protected static final String	ActInboundPortURI = "actiport" ;
	
	protected static final String	ETOutboundPortURI = "etoport" ;
	protected static final String	ETInboundPortURI = "etiport" ;
	
	protected static final String	SensorOutboundPortURI = "senoport" ;
	protected static final String	SensorInboundPortURI = "seniport" ;
	
	
	protected ConnectorI 			subConnector ;
	protected ConnectorI 			actConnector ;
	protected ConnectorI 			endTaskConnector ;
	protected ConnectorI			senConnector;
	
	
	
	@Override
	public void			deploy() throws Exception
	{
		Computer	comp ;
		AdmissionController	ac ;

		// Creation phase

		// create the provider component
		comp =
			new Computer("myURI", SubInboundPortURI, ActInboundPortURI,ETOutboundPortURI,SensorInboundPortURI,false,2) ;
		// add it to the deployed components
		this.deployedComponents.add(comp) ;
		
		// create the consumer component
		ac = new AdmissionController(SubOutboundPortURI,ActOutboundPortURI,ETInboundPortURI,SensorOutboundPortURI,false) ;
		// add it to the deployed components
		this.deployedComponents.add(ac) ;
		
		// Connection phase

		// create the connector
		this.subConnector = new SubmissionServiceConnector() ;
		this.actConnector = new ActuatorServiceConnector() ;
		this.endTaskConnector = new EndTaskServiceConnector();
		this.senConnector = new DataConnector() ;
		
		
		// do the connection
		ConnectionBuilder.SINGLETON.connectWith(SubInboundPortURI, SubOutboundPortURI, subConnector) ;
		
		ConnectionBuilder.SINGLETON.connectWith(ActInboundPortURI,ActOutboundPortURI,actConnector) ;
		
		ConnectionBuilder.SINGLETON.connectWith(ETInboundPortURI,ETOutboundPortURI,endTaskConnector) ;
		
		ConnectionBuilder.SINGLETON.connectWith(SensorInboundPortURI,SensorOutboundPortURI,this.senConnector);
		
		

		// deployment done
		super.deploy();
	}
	
	public static void		main(String[] args)
	{
		Assembly a = new Assembly() ;
		try {
			a.deploy() ;
			System.out.println("starting...") ;
			a.start() ;
			Thread.sleep(25000L) ;
			System.out.println("shutting down...") ;
			a.shutdown() ;
			System.out.println("ending...") ;
			System.exit(0) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
