package neat_proper;

import java.util.*;

public class Genome {
     List<Gene> genes;
     int fitness;
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
	
}
