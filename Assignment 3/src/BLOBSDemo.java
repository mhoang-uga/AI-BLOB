
import aima.core.agent.Action;
import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.adversarial.MinimaxSearch;
import aima.core.util.datastructure.XYLocation;

public class BLOBSDemo {

	public static void main(String[] args) {
		System.out.println("BLOBS DEMO");
		System.out.println("");
		startRandomDemo();
		startMinimaxDemo();
	}
	private static void startRandomDemo() {
		System.out.println("RANDOM DEMO\n");
		BLOBSGame game = new BLOBSGame();
		BLOBSBoard currState = game.getInitialState();
		RandomAgent<BLOBSBoard, BLOBSMove2, String> search = RandomAgent
				.createFor(game);
		System.out.println(currState);
		while (!(game.isTerminal(currState))) {
			System.out.println(game.getPlayer(currState) + "  playing ... ");
			BLOBSMove2 action = search.makeDecision(currState);
			currState = game.getResult(currState, action);
			System.out.println(action);

			System.out.println(currState);
		}
		System.out.println("RANDOM DEMO done");
		System.out.println("Winner: " + currState.winner);
	}
	private static void startMinimaxDemo() {
		System.out.println("MINI MAX DEMO\n");
		BLOBSGame game = new BLOBSGame();
		BLOBSBoard currState = game.getInitialState();
		
		AdversarialSearch<BLOBSBoard, BLOBSMove2> search;
		AdversarialSearch<BLOBSBoard, BLOBSMove2> search1 = RandomAgent.createFor(game);
		AdversarialSearch<BLOBSBoard, BLOBSMove2> search2 = DepthLimitMinimaxSearch
				.createFor(game,3);//Oplayer
		System.out.println(currState);
		while (!(game.isTerminal(currState))) {
			String player = game.getPlayer(currState);

			System.out.println(player + "  playing ... ");
			//BLOBSMove2 action = search1.makeDecision(currState);
			
			if (player == currState.O) {
				search = search2;
			}
			else {	
				search = search1;
			}
			BLOBSMove2 action = search.makeDecision(currState);

			currState = game.getResult(currState, action);
			System.out.println(currState);
		}
		System.out.println("MINI MAX DEMO done");
		System.out.println("Winner: " + currState.winner);
	}

}
