package fr.upmc.simulator.computers.devices;

/**
 * the class Core represents the cores of processors.
 * @author mamads
 *
 */
public class Core {
	/**
	 * frequency
	 */
	private int frequncy;

	/**
	 * frequency range
	 */
	private Plage plage;

	/**
	 * energy consumption
	 */
	private int energy;




	/**
	 * core number
	 */
	private int number;

	/**
	 * the processor
	 */
	private Processor processor;


	public Core(Plage plage,int number,Processor processor){
		this.processor=processor;

		this.number=number;
		this.plage=plage;
		this.frequncy=(int)(Math.random()*this.plage.getMaxFreq());
		
		System.out.println("fre"+this.frequncy);
		
		

	}

	public Processor getProcessor() {
		return processor;
	}


	public void setProcessor(Processor processor) {
		this.processor = processor;
	}





	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}




	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}




	public int getFrequncy() {
		return frequncy;
	}

	public void setFrequncy(int frequncy) {
		this.frequncy = frequncy;
	}

	public Plage getPlage() {
		return plage;
	}

	public void setPlage(Plage plage) {
		this.plage = plage;
	}

	/**
	 * execute the task
	 * @param task
	 * @return
	 */
	public boolean execute(double nbInsc){
		double duree=nbInsc/this.frequncy;
		
		System.out.println("Processor : "+ this.getProcessor().getNumber()+" Core :"+this.getNumber());
		
//		try {
//			Thread.sleep((long)duree);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


		return false;

	}



}
