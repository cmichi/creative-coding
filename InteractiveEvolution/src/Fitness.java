
public class Fitness {

	private Main p;

	public Fitness(Main main) {
		this.p = main;
	}

	public int getFittest(int i, Form[] forms) {
		float max = 0;
		int bestF = -1, sndBestF = -1;
		
		for (int j = 0; j < forms.length; j++) {
			float valueF = rate(forms[j]);
			
			if (valueF > max) {
				if (bestF > -1)
					sndBestF = bestF;
				bestF = j;
			}
		}
		
		if (i == 0)
			return bestF;
		
		return sndBestF;
	}

	private float rate(Form f) {
		return 1;
	}
	
	

}
