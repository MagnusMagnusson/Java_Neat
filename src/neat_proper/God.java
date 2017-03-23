package neat_proper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class God {
	public static God me;
	public int input_size;
	public int output_size;
	public List<Integer> inputs;
	public List<Float> outputs;
	public int population;
	public float deltaDistjoint;
	public float deltaWeight;
	public float deltaThreshhold;
	public int stale_Species;
	public float mutate_connection_chance;
	public float mutate_peturb_chance;
	public float mutate_cross_chance;
	public float mutate_link_chance;
	public float mutate_node_chance;
	public float mutate_bias_chance;
	public float mutate_stepsize_chance;
	public float mutate_disable_chance;
	public float mutate_enable_chance;
	public float timeOut;
	public int max_nodes;
	Pool currentPool;
	
	public static God instance(){
		if(me == null){
			me = new God();
			me.init_pool();
		}
		return me;
	}
	
	public String test(){
		return "NO!";
	}
	
	public int current_innovation(Pool pool){
		return pool.innovation_number;
	}
	
	public int new_innovation(Pool pool){
		pool.innovation_number++;
		return pool.innovation_number;
	}
	
	public Pool new_pool(){
		return new Pool();
	}
	
	public Species new_species(){
		return new Species();
	}
	
	public Genome new_genome(){
		return new Genome();
	}
	
	public void init_pool(){
		Pool p = new_pool();
		for(int i = 0; i < population; i++){
			Genome g = Genome.basic_genome();
			p.addToSpecies(g);
		}
		currentPool = p;
		System.out.println("Generated basic pool with " + p.species.size() + " species");
	}
	
	
	public void evaluate(Genome genome){
		int sum = 0;
		for(int i = 0; i < 256; i++){
			int total = 0;
			inputs.clear();
			inputs.add(i & 1);
			inputs.add((i & 2) > 0 ? 1 : 0 );
			inputs.add((i & 4) > 0 ? 1 : 0 );
			inputs.add((i & 8) > 0 ? 1 : 0 );
			inputs.add((i & 16) > 0 ? 1 : 0);
			inputs.add((i & 32) > 0 ? 1 : 0);
			inputs.add((i & 64) > 0 ? 1 : 0);
			inputs.add((i & 128) > 0 ? 1 : 0);
			for(int j = 0; j < 8; j++){
				total += (i & (int)Math.pow(2, j)) > 0 ? 1 : 0;
			}
			outputs = genome.network.evaluate(inputs);
			float S = outputs.get(0) + outputs.get(1)*2 +  outputs.get(2)*4 + outputs.get(3)*8;
			System.out.println(outputs.toString());
			sum += Math.abs(S - total);
		}
		genome.fitness = sum;
		if(currentPool.max_fitness < sum){
			currentPool.max_fitness = sum;
		}
	}
	
	public void generate(){
		currentPool.current_species = 0;
		currentPool.current_genome = 0;
		currentPool.max_fitness = 0;
		while(true){
			while(currentPool.fitness_measured()){
				currentPool.nextGenome();
			}
			currentPool.run();
			currentPool.nextGenome();
		}
	}
	
	
	private God(){	
		input_size = 8;
		output_size = 4;

		inputs = new ArrayList<Integer>();
		outputs = new ArrayList<Float>();
		for(int i = 0; i < input_size;i++){
			inputs.add(0);
		}
		for(int i = 0; i < output_size;i++){
			outputs.add(0.0f);
		}
		population = 300;
		deltaDistjoint = 2;
		deltaWeight = 0.4f;
		deltaThreshhold = 1;
		stale_Species = 15;
		mutate_connection_chance = 0.25f;
		mutate_peturb_chance = 0.9f;
		mutate_cross_chance = 0.75f;
		mutate_link_chance = 2;
		mutate_node_chance = 0.5f;
		mutate_bias_chance = 0.4f;
		mutate_stepsize_chance = 0.1f;
		mutate_disable_chance = 0.4f;
		mutate_enable_chance = 0.2f;
		timeOut = 20;
		max_nodes = 1000000;
		currentPool = new Pool();
	}

	public static float sigma(float x){
		return (float)(2/(1+Math.exp(-4.9*x))-1);
    }
	

}
