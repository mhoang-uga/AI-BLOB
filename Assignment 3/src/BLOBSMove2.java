import aima.core.agent.Action;
import aima.core.util.datastructure.XYLocation;
public class BLOBSMove2 implements Action{
    
    //Instance Properties

    XYLocation curLoc;
    XYLocation newLoc;
    boolean pass = false;
    //public String player = board.getPlayerToMove();
    
    /**
     * Constructor to create a BLOBSMove that adds a piece to the board using both 
     * numeric coordinates.
     * 
     * @param x position of the placement
     * @param y position of the placement
     */
    
    public BLOBSMove2 ( XYLocation source, XYLocation dest ){
    	this.curLoc = source;
    	this.newLoc = dest;
    }
    public BLOBSMove2() {
    	pass=true;
    }
    public String toString() {
    	
    	return String.format("pass: %s, source: %s, dest: %s",pass, curLoc, newLoc);
    }
	@Override
	public boolean isNoOp() {
		return false;
	}
    
} // BLOBSMove
