package neat_proper;

import java.util.*;

public class Neuron {
    List<Gene> incoming;
    int value;
    
    public Neuron(){
    	incoming = new ArrayList<Gene>();
    	value = 0;
    }
}
