package fr.upmc.simulator.tasks;
/**
 * this class is responsible to generate Requests
 * @author mamads
 *
 */
public class RequestGenerator  {
	
	/**
	 * interactives task
	 */
	private InteractiveTask task;
	
	/**
	 * profile generation
	 */
	private Profil profile;
	


	public InteractiveTask getTask() {
		return task;
	}


	public void setTask(InteractiveTask task) {
		this.task = task;
	}


	public Profil getProfile() {
		return profile;
	}


	public void setProfile(Profil profile) {
		this.profile = profile;
	}


	public RequestGenerator(InteractiveTask task,Profil profile){
		this.task=task;
		this.profile=profile;
	}
	
//	
//	public void run(){
//		int cpt=(int) (Math.random()*this.getProfile().getMaxRequest());
//		int i=0;
//		while(i<cpt){
//			Request req=new Request();
//			req.setInstructions(this.profile.getInstructions());
//			
//			this.task.addRequest(req);
//			i++;
//			try {
//				Thread.sleep(this.profile.getIntervalle());
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	
	public Request generate(){
		
		return new  Request();
	}
}
