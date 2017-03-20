package neat_proper;

import java.util.*;

public class Neuron {
    List<Gene> incoming;
    float value;
    
    public Neuron(){
    	incoming = new ArrayList<Gene>();
    	value = 0;
    }
}
