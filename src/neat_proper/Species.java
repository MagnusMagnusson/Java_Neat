package neat_proper;

import java.util.*;

public class Species {
    int top_fitness;
    int staleness;
    List<Genome> genomes;
    int average_fitness;
	public Species(){
    	top_fitness = 0;
    	staleness = 0;
    	genomes = new ArrayList<Genome>();
    	average_fitness = 0;
    }
}
