package neat_proper;

import java.util.ArrayList;
import java.util.List;

public class God {
	public static God me;
	public int input_size;
	public int output_size;
	public List<Integer> inputs;
	public List<Integer> outputs;
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
	
	public static God instance(){
		if(me == null){
			me = new God();
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
	
	private God(){	
		input_size = 7;
		output_size = 3;

		inputs = new ArrayList<Integer>();
		outputs = new ArrayList<Integer>();
		for(int i = 0; i < input_size;i++){
			inputs.add(0);
		}
		for(int i = 0; i < output_size;i++){
			outputs.add(0);
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
	}

	public static float sigma(float x){
		return (float)(2/(1+Math.exp(-4.9*x))-1);
    }

}
