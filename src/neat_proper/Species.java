package neat_proper;

import java.util.*;

public class Species {
    float top_fitness;
    int staleness;
    List<Genome> genomes;
    float average_fitness;
    
	public Species(){
    	top_fitness = 0;
    	staleness = 0;
    	genomes = new ArrayList<Genome>();
    	average_fitness = 0;
    }
	
	public void averageFitness(){
		float total = 0;
		for(Genome g: genomes){
			total += g.fitness;
		}
		if(genomes.size() > 0){
			average_fitness = total / genomes.size();
		}
	}
	public Genome breed(){
		Genome kid;
		God god = God.instance();
		if(Math.random() < god.mutate_cross_chance){
			Genome g1 = genomes.get((int)Math.random() * genomes.size());
			Genome g2 = genomes.get((int)Math.random() * genomes.size());
			kid = g1.crossover(g2);
		}
		else{
			Genome g1 = genomes.get((int)Math.random() * genomes.size());
			kid = g1.copy();
		}
		kid.mutate();
		return kid;
	}
}
