package neat_proper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Pool {
	public int innovation_number;
    List<Species> species;
    int generation;
    int current_species;
    int  current_genome;
    int current_frame;
    int max_fitness;
    
    public Pool(){
    	species = new ArrayList<Species>();
    	innovation_number = 0;
    	generation = 0;
    	current_species = 0;
    	current_genome = 0;
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
    
    public void addToSpecies(Genome Kid){
    	Boolean found;
    	found = false;
    	for(Species s: species){
    		if(!found && s.genomes.size() > 0 && s.genomes.get(0).same_species(Kid)){
    			s.genomes.add(Kid);
    			found = true;
    			break;
    		}
    	}    	
    	if(!found){
    		Species newSpecies =new Species();
    		newSpecies.genomes.add(Kid);
    		species.add(newSpecies);
    	}
    }
    
    public void newGeneration(){
    	cullSpecies(false);
    	rankSpecies();
    	removeStale();
    	rankSpecies();
    	for(Species s: species){
    		s.averageFitness();
    	}
    	removeSpecies();
    	
    	float average_fit = totalAverageFitness();
    	List<Genome> newBorns = new ArrayList<Genome>();
    	for(Species s: species){
    		float breed = (float)Math.floor(s.average_fitness / average_fit * God.instance().population) -1;
    		for(int i = 0; i < breed; i++){
    			newBorns.add(s.breed());
    		}
    	}
    	cullSpecies(true);
    	God god = God.instance();
    	while(newBorns.size() + species.size() < god.population){
    		Species s = species.get((int)(Math.random() * species.size()));
    		newBorns.add(s.breed());
    	}
    	for(Genome g: newBorns){
    		addToSpecies(g);
    	}
    	generation++;
    	System.out.println("generation " + generation + " has been born!");
    }
    
    public void run(){
		int si = current_species;
		int gi = current_genome;
		Species currentSpecies = species.get(si);
		Genome currentGenome = currentSpecies.genomes.get(gi);
		currentGenome.generateNetwork();
		God.instance().evaluate(currentGenome);
    }
    
    public void nextGenome(){		
		int si = current_species;
		Species currentSpecies = species.get(si);
	
		current_genome++;
		if(current_genome >= currentSpecies.genomes.size()){
			current_genome = 0;
			current_species++;
			if(current_species >= species.size()){
				current_species = 0;
				newGeneration();
			}
		}
    }
    
    public boolean fitness_measured(){
		int si = current_species;
		int gi = current_genome;
		Species currentSpecies = species.get(si);
		Genome currentGenome = currentSpecies.genomes.get(gi);
       
        return currentGenome.fitness != 0;
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
}
