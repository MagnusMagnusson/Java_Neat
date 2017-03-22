package neat_proper;

import java.util.ArrayList;
import java.util.Comparator;
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
    
    public void rankSpecies(){
    	List<Genome> rankList = new ArrayList<Genome>();
    	for(Species s: species){
    		for(Genome g: s.genomes){
    			rankList.add(g);
    		}
    	}
    	rankList.sort(new GenomeSort());
    	for(int i = 0; i < rankList.size(); i++ ){
    		rankList.get(i).fitness = i + 1;
    	}
    	
    }
    
    public float totalAverageFitness(){
    	float t = 0;
    	for(Species s: species){
    		t += s.average_fitness;
    	}
    	return t;
    }
    
    public void cullSpecies(Boolean cullToOne){
    	for(Species s: species){
    		s.genomes.sort(new GenomeSort());
    		int remaining =(int)Math.ceil(s.genomes.size()/2);
    		if(cullToOne){
    			remaining = 1;
    		}
    		while(s.genomes.size() > remaining){
    			s.genomes.remove(0);
    		}
    	}
    }
    
    public void removeStale(){

        List<Species> newSpecies;
        newSpecies = new ArrayList<Species>();
        
        for(Species s: species){
        	s.genomes.sort(new ReverseGenomeSort());
        	if(s.genomes.get(0).fitness > s.top_fitness){
        		s.top_fitness = s.genomes.get(0).fitness;
        		s.staleness = 0;
        	}
        	else{
        		s.staleness++;
        	}
        	if(s.staleness < God.instance().stale_Species || s.top_fitness >= max_fitness){
        		newSpecies.add(s);
        	}
        }
        species = newSpecies;
    }
    
    public void removeSpecies(){

        List<Species> newSpecies;
        newSpecies = new ArrayList<Species>();
        
        float sum = totalAverageFitness();
        for(Species s: species){
        	float breed = (float)Math.floor(s.average_fitness / (sum * God.instance().population));
        	if(breed > 1){
        		newSpecies.add(s);
        	}
        }
        species = newSpecies;
    }
    
    private class GenomeSort implements Comparator<Genome>{
		@Override
		public int compare(Genome o1, Genome o2) {
			return(int) Math.signum(o1.fitness - o2.fitness);
		}
    	
    }    
    private class ReverseGenomeSort implements Comparator<Genome>{
		@Override
		public int compare(Genome o1, Genome o2) {
			return (int)Math.signum(o2.fitness - o1.fitness);
		}
    	
    }
    /*
     * 
        table.sort(global, function (a,b)
                return (a.fitness < b.fitness)
        end)
       
        for g=1,#global do
                global[g].globalRank = g
        end
end*/
}
