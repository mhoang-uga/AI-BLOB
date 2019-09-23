import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.Game;
import aima.core.search.framework.Metrics;

import java.util.Random;

public class RandomAgent<STATE, ACTION, PLAYER> implements AdversarialSearch<STATE, ACTION> {
	
	private Game<STATE, ACTION, PLAYER> game;
	public static <STATE, ACTION, PLAYER> RandomAgent<STATE, ACTION, PLAYER> 
		createFor(Game<STATE, ACTION, PLAYER> game) {
		return new RandomAgent<STATE, ACTION, PLAYER>(game);
	}
	public RandomAgent(Game<STATE, ACTION, PLAYER> game) {
		this.game = game;
	}
	@Override
	public ACTION makeDecision(STATE state) {
		Random r = new Random();
		int bound = game.getActions(state).size();
		ACTION result = game.getActions(state).get(r.nextInt(bound));
		return result;
	}
	@Override
	public Metrics getMetrics() {
		// TODO Auto-generated method stub
		return null;
	}

}
