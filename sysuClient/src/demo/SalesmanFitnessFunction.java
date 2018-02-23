package demo;

import java.util.Date;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;

public class SalesmanFitnessFunction extends FitnessFunction {
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.10 $";

  private final Salesman m_salesman;

  public SalesmanFitnessFunction(final Salesman a_salesman) {
    m_salesman =  a_salesman;
  }

  protected double evaluate(final IChromosome a_subject) {
    double s = 0;
    Date Arrive = new Date();//当前时间
    
    Gene[] genes = a_subject.getGenes();
    for (int i = 0; i < genes.length - 1; i++) {
    	double cost = cost( ((TravellingSalesman) m_salesman).getCITIES().get(i), Arrive); //注意第一个task是出发点
    	long tmp = (long) (1000 * (Arrive.getTime() / 1000 + cost));
    	Arrive = new Date(tmp);
    	
    	s += m_salesman.distance(genes[i], genes[i + 1]);
    	s += cost;
    }
    // add cost of coming back:
    s += m_salesman.distance(genes[genes.length - 1], genes[0]);
    return Integer.MAX_VALUE / 2 - s;
  }
  
  public double cost(City task, Date s){

	  long diff = s.getTime() - task.getAppointmentTime().getTime();
	  
	  if(diff > 0) return task.getDelayFactor() * diff / 1000; //Seconds 
	  else return 0;
  }

  
}
