package neat_proper;

import java.util.*;

public class Genome {
     List<Gene> genes;
     float fitness;
     int adjustedFitness;
     Network network;
     int maxneuron;
     int globalRank;
     float mutation_connection;
     float mutation_link;
     float mutation_bias;
     float mutation_node;
     float mutation_enable;
     float mutation_disable;
     float mutation_step;
     
    public Genome(){
    	 God god = God.instance();
    	 genes = new ArrayList<Gene>();
    	 fitness = 0;
    	 adjustedFitness = 0;
    	 network = new Network();
    	 maxneuron = 0;
    	 globalRank = 0;     
    	 mutation_connection = god.mutate_connection_chance;
         mutation_link = god.mutate_link_chance;
         mutation_bias = god.mutate_bias_chance;
         mutation_node = god.mutate_node_chance;
         mutation_enable = god.mutate_enable_chance;
         mutation_disable = god.mutate_disable_chance;
         mutation_step = god.mutate_stepsize_chance;
     }
     
    public Genome copy(){
    	 Genome copy = new Genome();
    	 for(Gene gene: genes){
    		 copy.genes.add(gene.copy());
    	 }     
    	 copy.maxneuron = this.maxneuron;
    	 copy.mutation_connection = mutation_connection;
    	 copy.mutation_link = mutation_link;
    	 copy.mutation_bias = mutation_bias;
    	 copy.mutation_node = mutation_node;
    	 copy.mutation_enable = mutation_enable;
    	 copy.mutation_disable = mutation_disable;
    	 copy.mutation_step = mutation_step;
    	 return copy;
     }
     
    public static Genome basic_genome(){
    	 Genome basic = new Genome();
    	 God god = God.instance();
    	 basic.maxneuron = god.input_size+1;
    	 basic.mutate();
    	 return basic;
     }
    public void mutate(){
    		mutation_connection *= (Math.random() < 0.5) ? 0.95 : 1.05;
    		mutation_link *= (Math.random() < 0.5) ? 0.95 : 1.05;
    		mutation_bias *= (Math.random() < 0.5) ? 0.95 : 1.05;
    		mutation_node *= (Math.random() < 0.5) ? 0.95 : 1.05;
    		mutation_enable *= (Math.random() < 0.5) ? 0.95 : 1.05;
    		mutation_disable *= (Math.random() < 0.5) ? 0.95 : 1.05;
    		mutation_step *= (Math.random() < 0.5) ? 0.95 : 1.05;
    		if(Math.random() < mutation_connection){
    			mutate_point();
    		}
    		float p = mutation_link;
    		while (p > 0){
    			if(Math.random() < p){
    				mutate_link(false);
    			}
    			p = p - 1;
    		}
    		p = mutation_bias;
    		while(p > 0){
    			if(Math.random() < p){
    				mutate_link(true);
    			}
    			p = p - 1;
    		}
    		p = mutation_node;
    		while(p > 0){
    			if(Math.random() < p){
    				mutate_node();
    			}
    			p = p - 1;
    		}
    		p = mutation_enable;
    		while(p > 0){
    			if(Math.random() < p){
    				mutate_enable_disable(true);
    			}
    			p = p - 1;
    		}
    		
    		p = mutation_disable;
    		while(p > 0){
    			if(Math.random() < p){
    				mutate_enable_disable(false);
    			}
    			p = p -1;
    		}
    }
    
    public void generateNetwork(){
		Network net = new Network();
		God god = God.instance();
		for(int i = 0; i < god.input_size;i++){
			net.neurons.put(i, new Neuron());
		}
		for(int i = 1; i < 1+god.output_size;i++){
			net.neurons.put(god.max_nodes + i, new Neuron());
		}
		
		genes.sort(null);
		
		for(Gene gene: genes){
			if(gene.enabled){
				if(net.neurons.get(gene.out) == null){
					net.neurons.put(gene.out, new Neuron());
				}
				Neuron n = net.neurons.get(gene.out);
				n.incoming.add(gene);
				if(net.neurons.get(gene.into) == null){
					net.neurons.put(gene.into, new Neuron());
				}
			}
		}
		network = net;
	};
	public Genome crossover(Genome partner){
		Genome g1;
		g1 = this;
		if(partner.fitness > g1.fitness){
			g1 = partner;
			partner = this;
		}
		Genome kid = new Genome();
		Map<Integer,Gene> inno = new TreeMap<Integer,Gene>();
		for(Gene gene: partner.genes){
			inno.put(gene.innovation,gene);
		}
		for(Gene gene: g1.genes){
			Gene gene2 = inno.get(gene.innovation);
			if(gene2 != null && Math.random() < .5 && gene2.enabled){
				kid.genes.add(gene2.copy());
			}
			else{
				kid.genes.add(gene.copy());
			}
		}
		kid.maxneuron = Math.max(g1.maxneuron,partner.maxneuron);
		kid.mutation_bias = g1.mutation_bias;
		kid.mutation_connection = g1.mutation_connection;
		kid.mutation_disable = g1.mutation_disable;
		kid.mutation_enable = g1.mutation_enable;
		kid.mutation_link = g1.mutation_link;
		kid.mutation_node = g1.mutation_node;
		kid.mutation_step = g1.mutation_step;
		
		return kid;
	}

	public Integer randomNeuron(boolean not_input){

		Map<Integer,Boolean> neurons = new TreeMap<Integer,Boolean>();
		God god = God.instance();
		if(!not_input){
			for(int i = 0; i < god.input_size; i++){
				neurons.put(i,true);
			}
		}
		for(int i = 1; i < god.output_size + 1; i++){
			neurons.put(god.max_nodes + i, true);
		}
		for(Gene gene: genes){
			if(!not_input || gene.into > god.input_size){
				neurons.put(gene.into,true);
			}
			if(!not_input || gene.out > god.input_size){
				neurons.put(gene.out, true);
			}
		}
		int count = neurons.size();
		int n = (int)( Math.random()*count);
		Set<Integer> keys = neurons.keySet();
		
		for(Integer k: keys)
		{
			n = n-1;
			if(n == 0){
				return k;
			}
		}
		return 0;
	}
	public Boolean contains_gene(Gene g){
		for(Gene gene: genes){
			if(gene.into == g.into && gene.out == g.out){
				return true;
			}
		}
		return false;
	}
	
	public void mutate_point(){
		float step = mutation_step;
		God god = God.instance();
		for(Gene gene: genes){
			if(Math.random() < god.mutate_peturb_chance){
				gene.weight += Math.random() * step*2 - step;
			}
		}
	}
	
	public void mutate_link(boolean bias){
		God god = God.instance();
		Integer neuron1 = randomNeuron(false);
		Integer neuron2 = randomNeuron(true);
		Gene newGene = new Gene();
		if(neuron1 <= god.input_size && neuron2 <= god.input_size){
			return;
		}
		if(neuron2 <= god.input_size){
			Integer t = neuron1;
			neuron1 = neuron2;
			neuron2 = t;
		}
		newGene.into = neuron1;
		newGene.out = neuron2;
		if(bias){
			newGene.into = god.input_size;
		}
		if(contains_gene(newGene)){
			return;
		}
		newGene.innovation = god.new_innovation(god.currentPool);
		newGene.weight = (float)(Math.random()*4 - 2);
		genes.add(newGene);
	}
	
	public void mutate_node(){
		God god = God.instance();
		if(genes.isEmpty()){
			return;
		}
		maxneuron++;
		Gene gene = genes.get((int)(Math.random() * genes.size()));
		if(!gene.enabled){
			return;
		}
		gene.enabled = false;
		Gene newGene = gene.copy();
		newGene.out = maxneuron;
		newGene.weight = 1;
		newGene.innovation = god.new_innovation(god.currentPool);
		newGene.enabled = true;
		genes.add(newGene);
		
		Gene newGene2 = gene.copy();
		newGene2.into = maxneuron;
		newGene2.innovation = god.new_innovation(god.currentPool);
		newGene2.enabled = true;
		genes.add(newGene2);
	}
	
	public void mutate_enable_disable(Boolean enabled){
		List<Gene> candidate = new ArrayList<Gene>();
		for(Gene gene: genes){
			if(gene.enabled == !enabled){
				candidate.add(gene);
			}
		}
		if(candidate.size() > 0){
			Gene G = candidate.get((int)(Math.random() * candidate.size()));
			G.enabled = !G.enabled;
		}
	}
	
	public float distjoint(Genome other){
		Integer distjoint = 0;
		
		Map<Integer, Boolean> dg1= new TreeMap<Integer,Boolean>();
		Map<Integer, Boolean> dg2= new TreeMap<Integer,Boolean>();
		
		for(Gene gene: other.genes){
			dg2.put(gene.innovation, true);
		}
		
		for(Gene gene: other.genes){
			dg1.put(gene.innovation, true);
		}
		for(Gene gene: genes){
			if(dg2.get(gene.innovation) == null){
				distjoint++;
			}
		}
		for(Gene gene: other.genes){
			if(dg1.get(gene.innovation) == null){
				distjoint++;
			}
		}
		float n = Math.max(genes.size(),other.genes.size());
		return ((float)distjoint)/n;
	}
	
	float weights(Genome other){
		Map<Integer, Gene> dg1= new TreeMap<Integer,Gene>();
		Map<Integer, Gene> dg2= new TreeMap<Integer,Gene>();
		for(Gene gene: other.genes){
			dg2.put(gene.innovation,gene);
		}
		float sum = 0;
		float coincident = 0;
		
		for(Gene gene: genes){
			if(dg1.get(gene.innovation) != null){
				Gene gene2 = dg1.get(gene.innovation);
				sum += Math.abs(gene.weight - gene2.weight);
				coincident++;
			}
		}
		if(coincident == 0){
			return 0;
		}
		return sum/coincident;
	}
	
	Boolean same_species(Genome other){
		God god = God.instance();
		float dd = god.deltaDistjoint * distjoint(other);
		float dw = god.deltaWeight * weights(other);
		return dd+dw < god.deltaThreshhold;
	}
}
