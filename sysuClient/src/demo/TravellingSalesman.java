package demo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jgap.*;
import org.jgap.impl.*;
//import org.jgap.impl.salesman.*;

import service.LBSService;

public class TravellingSalesman extends Salesman {
  /** String containing the CVS revision. Read out via reflection!*/
  private static final String CVS_REVISION = "$Revision: 1.14 $";

  private List<City> CITIES;
  public List<City> getCITIES() {
	return CITIES;
  }

  private double[][] DISTANCES;

  public TravellingSalesman(List<City> cities) {
	  CITIES = cities;
	  
	  DISTANCES = new double[CITIES.size()][CITIES.size()];
	  
	  LBSService lbsServ = new LBSService();
	  
	  for(int i = 0; i < CITIES.size(); i++)
		  for(int j = 0; j < CITIES.size(); j++) {
			  if( i == j) {
				  DISTANCES[i][j] = 0;

			  }else {
				  String lbsResult = lbsServ.getResult(CITIES.get(i).getLatitude(), CITIES.get(i).getLongitude(), CITIES.get(i).getRegion(), 
						  CITIES.get(j).getLatitude(), CITIES.get(j).getLongitude(), CITIES.get(j).getRegion());
				  
				  DISTANCES[i][j] = lbsServ.getDuration(lbsResult);
				  
				  System.out.println(i+","+j+":"+DISTANCES[i][j]);
			  }

		  }
  }
  
  /**
   * Create an array of the given number of integer genes. The first gene is
   * always 0, this is the city where the salesman starts the journey.
   *
   * @param a_initial_data ignored
   * @return Chromosome
   *
   * @author Audrius Meskauskas
   * @since 2.0
   */
  public IChromosome createSampleChromosome(Object a_initial_data) {
    try {
      Gene[] genes = new Gene[CITIES.size()];
      for (int i = 0; i < genes.length; i++) {
        genes[i] = new IntegerGene(getConfiguration(), 0, CITIES.size() - 1);
        genes[i].setAllele(new Integer(i));
      }
      IChromosome sample = new Chromosome(getConfiguration(), genes);
      return sample;
    }
    catch (InvalidConfigurationException iex) {
      throw new IllegalStateException(iex.getMessage());
    }
  }

  /**
   * Distance is equal to the difference between city numbers, except the
   * distance between the last and first cities that is equal to 1. In this
   * way, we ensure that the optimal solution is 0 1 2 3 .. n - easy to check.
   *
   * @param a_from first gene, representing a city
   * @param a_to second gene, representing a city
   * @return the distance between two cities represented as genes

   * @author Audrius Meskauskas
   * @since 2.0
   */
  public double distance(Gene a_from, Gene a_to) {
	  
	  double distance = 0;

	  IntegerGene geneA = (IntegerGene) a_from;
	  IntegerGene geneB = (IntegerGene) a_to;
	  int a = geneA.intValue();
	  int b = geneB.intValue();
	  
	  distance += CITIES.get(a).getConsuming(); //服务耗时
	  distance += DISTANCES[a][b]; //路程耗时
	  
	  return distance;
  }

  /**
   * Solve a sample task with the number of cities, defined in a CITIES
   * constant. Print the known optimal way, sample chromosome and found
   * solution.
   *
   * @param args not relevant here
   *
   * @author Audrius Meskauskas
   * @since 2.0
   */
  public static void main(String[] args) {
	  
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	  
	  List<City> Cities = new ArrayList<City>();
	  
	  try {
			  
		  Cities.add(new City(23.131482, 113.268771, "公园前","广州", 1, sdf.parse("2014-3-3 12:00:00"), 60));
		  Cities.add(new City(23.112338, 113.276245, "市二宫","广州", 1, sdf.parse("2014-3-3 13:00:00"), 60));
		  Cities.add(new City(23.100903, 113.262519, "凤凰新村","广州", 1, sdf.parse("2014-3-3 15:00:00"), 60));
		  Cities.add(new City(23.114864, 113.247643, "黄沙","广州", 1, sdf.parse("2014-3-3 16:00:00"), 60));
		  Cities.add(new City(23.124386, 113.248685, "长寿路","广州", 1, sdf.parse("2014-3-3 17:00:00"), 60));
		  Cities.add(new City(23.131698, 113.252566, "陈家祠","广州", 1, sdf.parse("2014-3-3 18:00:00"), 60));
		  Cities.add(new City(23.131366, 113.262483, "西门口","广州", 1, sdf.parse("2014-3-3 19:00:00"), 60));
		  
		  TravellingSalesman t = new TravellingSalesman(Cities);
		  IChromosome optimal = t.findOptimalPath(null);
		  System.out.println("Solution: ");
		  System.out.println(optimal);
		  System.out.println("Score " + (Integer.MAX_VALUE / 2 - optimal.getFitnessValue()));
		  
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
