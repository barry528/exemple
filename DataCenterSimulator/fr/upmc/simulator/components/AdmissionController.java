package fr.upmc.simulator.components;

import java.util.concurrent.TimeUnit;

import fr.upmc.components.AbstractComponent;
import fr.upmc.components.ComponentI.ComponentTask;
import fr.upmc.components.assemblies.AbstractAssembly;
import fr.upmc.components.examples.basic_cs.components.URIConsumer;
import fr.upmc.components.examples.basic_cs.interfaces.URIConsumerI;
import fr.upmc.components.examples.basic_cs.ports.URIGetterOutboundPort;
import fr.upmc.components.interfaces.DataRequiredI.DataI;
import fr.upmc.components.ports.PortI;
import fr.upmc.simulator.computers.devices.Core;
import fr.upmc.simulator.computers.devices.Processor;
import fr.upmc.simulator.interfaces.ActuatorConsummerI;
import fr.upmc.simulator.interfaces.EndTaskProviderI;
import fr.upmc.simulator.interfaces.SubmissionConsumerI;
import fr.upmc.simulator.interfaces.sensors.AdminContSensorDataRequiredI;
import fr.upmc.simulator.interfaces.sensors.AdminContSensorDataRequiredI.ComputerDataI;
import fr.upmc.simulator.interfaces.sensors.ComputerData;
import fr.upmc.simulator.ports.ActuatorInBoundPort;
import fr.upmc.simulator.ports.ActuatorOutBoundPort;
import fr.upmc.simulator.ports.EndTaskInBoundPort;
import fr.upmc.simulator.ports.SensorDataOutboundPort;
import fr.upmc.simulator.ports.SubmissionInBoundPort;
import fr.upmc.simulator.ports.SubmissionOutBoundPort;
import fr.upmc.simulator.tasks.AbstractTask;
import fr.upmc.simulator.tasks.Mygenerator;

public class AdmissionController extends AbstractComponent {
	/**	the outbound port used to call the service.							*/
	protected SubmissionOutBoundPort	submissionOutPort ;
	protected ActuatorOutBoundPort      actuatorOutPort;
	protected SensorDataOutboundPort    sensorOutPort;
	
	
	Mygenerator gen;
	
	public AdmissionController(String 	subOutPort,String actOutPort, String etInPort,String sensorPortURI,boolean isDistributed
			) throws Exception{
		super(false,true);
		
		
		
		// to receive sensor data from a sensor type of component
				this.addRequiredInterface(AdminContSensorDataRequiredI.PullI.class) ;
				this.addOfferedInterface(AdminContSensorDataRequiredI.PushI.class) ;
				
				this.sensorOutPort = new SensorDataOutboundPort(sensorPortURI, this) ;
				if (AbstractAssembly.isDistributed) {
					this.sensorOutPort.publishPort() ;
				} else {
					this.sensorOutPort.localPublishPort() ;
				}
				this.addPort(this.sensorOutPort) ;
		
		
		// put the required interface in the set of interfaces required by
		// the component.
		this.addRequiredInterface(SubmissionConsumerI.class) ;
		this.addRequiredInterface(ActuatorConsummerI.class);
		
		this.addOfferedInterface(EndTaskProviderI.class);
		
		this.actuatorOutPort  =new ActuatorOutBoundPort(actOutPort,this);
		this.submissionOutPort = new SubmissionOutBoundPort(subOutPort, this) ;
		
		this.addPort(this.actuatorOutPort);
		this.addPort(this.submissionOutPort) ;
		
		
		// publish the port (an outbound port is always local)
		this.actuatorOutPort.localPublishPort();
		this.submissionOutPort.localPublishPort() ;
		
		PortI etPort;
		
		
		try {
			etPort = new SubmissionInBoundPort(etInPort, this);			
			
			
			// add the port to the set of ports of the component
			this.addPort(etPort) ;
			

			if (isDistributed) {
				// if distributed, publish on registry
				etPort.publishPort() ;
				
			} else {
				// if not distributed, a local publication is sufficient
				etPort.localPublishPort() ;
				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		gen=new Mygenerator();
	}
	
	/**
	 * receive task from task generator
	 */
	public void receiveTask(){
		AbstractTask task=gen.generate();
		final AbstractTask t=task;
		
		final AdmissionController ac=this;
		
		this.scheduleTask(new ComponentTask() {
						@Override
						public void run() {
							try {
								ac.executeTask(t);
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
		},
		   10, TimeUnit.MILLISECONDS) ;	}
	
	/**
	 * locate a free computer
	 * @return
	 */
	public int locateComputer(){
		return 0;
		}
	
	/**
	 * submit task execution
	 * @param task
	 */
	public void executeTask(AbstractTask task){
		final AbstractTask t=task;
		
		final AdmissionController ac=this;
		
		this.scheduleTask(new ComponentTask() {
						@Override
						public void run() {
							try {
								ac.submissionOutPort.submitTask(t);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
		},
		   10, TimeUnit.MILLISECONDS) ;
		
		this.scheduleTask(new ComponentTask() {
			@Override
			public void run() {
				try {
					ac.actuatorOutPort.addProcessor(t, new Processor(0, null, 2));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
},
1000, TimeUnit.MILLISECONDS) ;
		
	}
	///////////////////////////actuator
	public void changeFrequency(Processor processor, int frequence) {
		
		final AdmissionController ac=this;
		final Processor p=processor;
		final int f=frequence;
		
		this.scheduleTask(new ComponentTask() {
						@Override
						public void run() {
							try {
								ac.actuatorOutPort.changeFrequency(p, f);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
		},
		   10, TimeUnit.MILLISECONDS) ;	
	}
	
	/**
	 * 
	 * @param task
	 */
	public void stopTask(AbstractTask task) {
		
		final AdmissionController ac=this;
		final AbstractTask t=task;
		
		this.scheduleTask(new ComponentTask() {
			@Override
			public void run() {
				try {
					ac.submissionOutPort.stopTask(t);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
},
10, TimeUnit.MILLISECONDS) ;	
		
	}
	
	public void notifyEndTask(AbstractTask task) {
		System.out.println("fin de tâche");
	}
	
	public void addProcessor(AbstractTask task, Processor processor) {
		final AdmissionController ac=this;
		final Processor p=processor;
		final AbstractTask t=task;
		
		this.scheduleTask(new ComponentTask() {
						@Override
						public void run() {
							try {
								ac.actuatorOutPort.addProcessor(t, p);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
		},
		   10, TimeUnit.MILLISECONDS) ;	
	}
	
	public void addCore(AbstractTask task, Core core) {
		final AdmissionController ac=this;
		final Core c=core;
		final AbstractTask t=task;
		
		this.scheduleTask(new ComponentTask() {
						@Override
						public void run() {
							try {
								ac.actuatorOutPort.addCore(t, c);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
		},
		   10, TimeUnit.MILLISECONDS) ;	
	}
	
	public void substractProcessor(AbstractTask task, Processor processor) {
		final AdmissionController ac=this;
		final Processor p=processor;
		final AbstractTask t=task;
		
		this.scheduleTask(new ComponentTask() {
						@Override
						public void run() {
							try {
								ac.actuatorOutPort.substractProcessor(t, p);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
		},
		   10, TimeUnit.MILLISECONDS) ;	
	}
	
	public void substractCore(AbstractTask task, Core core) {
		final AdmissionController ac=this;
		final Core c=core;
		final AbstractTask t=task;
		
		this.scheduleTask(new ComponentTask() {
						@Override
						public void run() {
							try {
								ac.actuatorOutPort.substractCore(t, c);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
		},
		   10, TimeUnit.MILLISECONDS) ;	
	}
	
	
	///////////////////////////
	
	@Override
	public void			start(){
		try {
			super.start();
			AbstractTask task=gen.generate();
			
			final AdmissionController uc = this ;
			final AbstractTask t=task;
			this.scheduleTask(
				new ComponentTask() {
					@Override
					public void run() {
						try {
							uc.receiveTask(); 
							uc.testSensor();
							//uc.addProcessor(t, new Processor(0, null, 2));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				},
				100, TimeUnit.MILLISECONDS);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void testSensor(){
		final AdmissionController uc = this ;
		this.scheduleTask(
			new ComponentTask() {
				@Override
				public void run() {
					try {
						ComputerDataI d=((ComputerData) (uc.sensorOutPort.request())).getComputerData();
						System.out.println("NRJ"+d.getCurrentEnergy());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			},
			100, TimeUnit.MILLISECONDS);
	}
	
	public void receive(DataI d){
		System.out.println("donnée recues");
	}

}
