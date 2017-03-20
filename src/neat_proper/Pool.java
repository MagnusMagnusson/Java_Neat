package neat_proper;

import java.util.ArrayList;
import java.util.List;

public class Pool {
	public int innovation_number;
    List<Species> species;
    int generation;
    int current_species;
    int current_genome;
    int current_frame;
    int max_fitness;
    
    public Pool(){
    	species = new ArrayList<Species>();
    	innovation_number = 0;
    	generation = 0;
    	current_species = 1;
    	current_genome = 1;
    	current_frame = 0;
    	max_fitness = 0;
    }
}
