package neat_proper;

import java.util.*;

public class Network {
	Map<Integer,Neuron> neurons;
	public Network(){
		neurons = new TreeMap<Integer,Neuron>();
	}
	
	public List<Float> evaluate(List<Integer> inputs){
		inputs.add(1);
		God god = God.instance();
		if(inputs.size() != god.input_size + 1){
			System.out.println("Error: Neural network recieved the wrong number of inputs.");
			return new ArrayList<Float>();
		}
		
		for(int i = 0; i < god.input_size + 1; i++){
			if(neurons.get(i) != null){
				neurons.get(i).value = inputs.get(i);
			}
		}
		Set<Integer> keys = neurons.keySet();
		for(Integer k: keys){
			Neuron neuron = neurons.get(k);
			float sum = 0;
			for(int j = 0; j < neuron.incoming.size(); j++){
				Gene incoming = neuron.incoming.get(j);
				Neuron other = neurons.get(incoming.into);
				sum += incoming.weight * other.value;
			}
			if(neuron.incoming.size() > 0){
				neuron.value = God.sigma(sum);
			}
		}
		
		List<Float> out = new ArrayList<Float>();
		for(int i = 1; i <= god.output_size; i++){
			out.add(neurons.get(god.max_nodes + i).value);
		}
		return out;
	}
}
