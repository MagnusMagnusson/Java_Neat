package neat_proper;

import java.util.Comparator;

public class Gene implements  Comparator<Gene>, Comparable<Gene>{        
	
	int into; 
    int out;
    float weight;
    Boolean enabled;
	int innovation; 

	Gene(){
		into = 0;
		out = 0;
		weight = 0;
		enabled = true;
		innovation = 0;
	}
	
	public Gene copy(){
		Gene copy = new Gene();
		copy.into = into;
		copy.out = out;
		copy.weight = weight;
		copy.enabled = enabled;
		copy.innovation = innovation;
		return copy;
	}

	@Override
	public int compareTo(Gene arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compare(Gene o1, Gene o2) {
		return o1.out - o2.out;
	}
}
