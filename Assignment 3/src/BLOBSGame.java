import java.util.List;


import aima.core.search.adversarial.Game;
import aima.core.util.datastructure.XYLocation;

public class BLOBSGame implements Game<BLOBSBoard, BLOBSMove2, String> {
	BLOBSBoard initialState = new BLOBSBoard(3);
	
	@Override
	public BLOBSBoard getInitialState() {
		return initialState;
	}

	@Override
	public String[] getPlayers() {
		return new String[] { BLOBSBoard.X, BLOBSBoard.O };
	}

	@Override
	public String getPlayer(BLOBSBoard state) {
		return state.getPlayerToMove();
	}

	@Override
	public List<BLOBSMove2> getActions(BLOBSBoard state) {
		return state.getValidAction();//
	}

	@Override
	public BLOBSBoard getResult(BLOBSBoard state, BLOBSMove2 action) {
		BLOBSBoard result = state.clone();
		result.mark(action);
		return result;
	}

	@Override
	public boolean isTerminal(BLOBSBoard state) {
		return state.isTerminal();
	}

	@Override
	public double getUtility(BLOBSBoard state, String player) {
		double result; 
		//if (isTerminal(state)) {
			result= state.getUtility();
		
		//} else {
			
		//}
		if (player == BLOBSBoard.O)
					result = 1 - result;
		return result;
	}
}
