package neat_proper;

import java.util.List;

public class God {
	public static God me;
	public int input_size;
	public List<Boolean> inputs;
	public List<Boolean> outputs;
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
	private int innovation_number;
	
	public static God instance(){
		if(me == null){
			me = new God();
		}
		return me;
	}
	
	public String test(){
		return "NO!";
	}
	
	public int current_innovation(){
		return innovation_number;
	}
	
	public int new_innovation(){
		innovation_number++;
		return innovation_number;
	}
	
	private God(){
		
	}
}
