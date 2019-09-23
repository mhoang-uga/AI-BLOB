import aima.core.search.adversarial.*;
import aima.core.search.framework.Metrics;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 169.<br>
 * 
 * <pre>
 * <code>
 * function MINIMAX-DECISION(state) returns an action
 *   return argmax_[a in ACTIONS(s)] MIN-VALUE(RESULT(state, a))
 * 
 * function MAX-VALUE(state) returns a utility value
 *   if TERMINAL-TEST(state) then return UTILITY(state)
 *   v = -infinity
 *   for each a in ACTIONS(state) do
 *     v = MAX(v, MIN-VALUE(RESULT(s, a)))
 *   return v
 * 
 * function MIN-VALUE(state) returns a utility value
 *   if TERMINAL-TEST(state) then return UTILITY(state)
 *     v = infinity
 *     for each a in ACTIONS(state) do
 *       v  = MIN(v, MAX-VALUE(RESULT(s, a)))
 *   return v
 * </code>
 * </pre>
 * 
 * Figure 5.3 An algorithm for calculating minimax decisions. It returns the
 * action corresponding to the best possible move, that is, the move that leads
 * to the outcome with the best utility, under the assumption that the opponent
 * plays to minimize utility. The functions MAX-VALUE and MIN-VALUE go through
 * the whole game tree, all the way to the leaves, to determine the backed-up
 * value of a state. The notation argmax_[a in S] f(a) computes the element a of
 * set S that has the maximum value of f(a).
 * 
 * 
 * @author Ruediger Lunde
 * 
 * @param <STATE>
 *            Type which is used for states in the game.
 * @param <ACTION>
 *            Type which is used for actions in the game.
 * @param <PLAYER>
 *            Type which is used for players in the game.
 */
public class DepthLimitMinimaxSearch<STATE, ACTION, PLAYER> implements AdversarialSearch<STATE, ACTION> {

	public final static String METRICS_NODES_EXPANDED = "nodesExpanded";
	private Game<STATE, ACTION, PLAYER> game;
	private Metrics metrics = new Metrics();
	public int depth=5;
	/** Creates a new search object for a given game. */
	public static <STATE, ACTION, PLAYER> DepthLimitMinimaxSearch<STATE, ACTION, PLAYER> createFor(Game<STATE, ACTION, PLAYER> game, int depth) {
		return new DepthLimitMinimaxSearch<STATE, ACTION, PLAYER>(game, depth);
	}

	public DepthLimitMinimaxSearch(Game<STATE, ACTION, PLAYER> game, int depth) {
		this.depth = depth;
		this.game = game;
	}

	@Override
	public ACTION makeDecision(STATE state) {
		metrics = new Metrics();
		ACTION result = null;
		double resultValue = Double.NEGATIVE_INFINITY;
		PLAYER player = game.getPlayer(state);
		if (game.isTerminal(state)|| depth ==0) {
			return result;
		}
		else {
			for (ACTION action : game.getActions(state)) {
				double value = minValue(game.getResult(state, action), player, depth-1);
				if (value > resultValue) {
					result = action;
					resultValue = value;
				}
			}
		}
		depth--;
		return result;
	}

	public double maxValue(STATE state, PLAYER player, int depth) { // returns an utility
															// value
		metrics.incrementInt(METRICS_NODES_EXPANDED);
		if (game.isTerminal(state) || depth ==0)
			return game.getUtility(state, player);
		else {
			double value = Double.NEGATIVE_INFINITY;
			for (ACTION action : game.getActions(state))
				value = Math.max(value, minValue(game.getResult(state, action), player,depth-1));
			return value;
		}
	}

	public double minValue(STATE state, PLAYER player, int depth) { // returns an utility value
		metrics.incrementInt(METRICS_NODES_EXPANDED);
		if (game.isTerminal(state) || depth ==0)
			return game.getUtility(state, player);
		double value = Double.POSITIVE_INFINITY;
		for (ACTION action : game.getActions(state))
			value = Math.min(value, maxValue(game.getResult(state, action), player, depth-1));
		return value;
	}

	@Override
	public Metrics getMetrics() {
		return metrics;
	}
}
