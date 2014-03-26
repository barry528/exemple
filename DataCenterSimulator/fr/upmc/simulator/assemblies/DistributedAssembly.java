package fr.upmc.simulator.assemblies;

import fr.upmc.components.assemblies.AbstractDistributedAssembly;
import fr.upmc.components.connectors.ConnectionBuilder;
import fr.upmc.components.connectors.ConnectorI;
import fr.upmc.simulator.components.AdmissionController;
import fr.upmc.simulator.components.Computer;


public class			DistributedAssembly
extends		AbstractDistributedAssembly
{
	protected static String		PROVIDER_JVM_URI = "provider" ;
	protected static String		CONSUMER_JVM_URI = "consumer" ;
	
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
	

	protected Computer	comp ;
	protected AdmissionController	admincont ;
	

	public				DistributedAssembly(
		String[] args
		) throws Exception
	{
		super(args);
		// TODO Auto-generated constructor stub
	}

	/**
	 * do some initialisation before any can go on.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true				// no more preconditions.
	 * post	true				// no more postconditions.
	 * </pre>
	 * 
	 * @see fr.upmc.components.assemblies.AbstractDistributedAssembly#initialise()
	 */
	@Override
	public void			initialise() throws Exception
	{
		super.initialise();
	}

	/**
	 * instantiate components and publish their ports.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true				// no more preconditions.
	 * post	true				// no more postconditions.
	 * </pre>
	 * 
	 * @see fr.upmc.components.assemblies.AbstractDistributedAssembly#instantiateAndPublish()
	 */
	@Override
	public void			instantiateAndPublish() throws Exception
	{
		if (thisJVMURI.equals(PROVIDER_JVM_URI)) {

			// create the provider component
			this.comp =
					new Computer("myURI", SubInboundPortURI,ActInboundPortURI,ETOutboundPortURI,SensorInboundPortURI,false,2) ;
			// add it to the deployed components
			this.deployedComponents.add(comp) ;

		} else if (thisJVMURI.equals(CONSUMER_JVM_URI)) {

			// create the consumer component
			this.admincont = new AdmissionController(SubOutboundPortURI,ActOutboundPortURI,ETInboundPortURI,SensorOutboundPortURI,false) ;
			// add it to the deployed components
			this.deployedComponents.add(comp) ;

		} else {

			System.out.println("Unknown JVM URI... " + thisJVMURI) ;

		}

		super.instantiateAndPublish();
	}

	/**
	 * interconnect the components.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true				// no more preconditions.
	 * post	true				// no more postconditions.
	 * </pre>
	 * 
	 * @see fr.upmc.components.assemblies.AbstractDistributedAssembly#interconnect()
	 */
	@Override
	public void			interconnect() throws Exception
	{
		assert	this.instantiationAndPublicationDone ;

		if (thisJVMURI.equals(PROVIDER_JVM_URI)) {

		} else if (thisJVMURI.equals(CONSUMER_JVM_URI)) {

			// create the connector
			this.subConnector = new SubmissionServiceConnector() ;
			// do the connection
			ConnectionBuilder.SINGLETON.connectWith(SubInboundPortURI,
					SubOutboundPortURI,
													this.subConnector) ;

		} else {

			System.out.println("Unknown JVM URI... " + thisJVMURI) ;

		}

		super.interconnect();
	}

	/**
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true				// no more preconditions.
	 * post	true				// no more postconditions.
	 * </pre>
	 * 
	 * @see fr.upmc.components.assemblies.AbstractDistributedAssembly#shutdown()
	 */
	@Override
	public void			shutdown() throws Exception
	{
		if (thisJVMURI.equals(CONSUMER_JVM_URI)) {

			ConnectionBuilder.SINGLETON.disconnectWith(
					SubInboundPortURI,
					SubOutboundPortURI,
											this.subConnector) ;

		}

		super.shutdown();
	}

	public static void	main(String[] args)
	{
		System.out.println("Beginning") ;
		try {
			DistributedAssembly da = new DistributedAssembly(args) ;
			da.deploy() ;
			da.start() ;
			Thread.sleep(25000) ;
			da.shutdown() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Main thread ending") ;
		System.exit(0);
	}
}



