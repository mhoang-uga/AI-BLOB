import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Math;

import aima.core.util.datastructure.XYLocation;
public class BLOBSBoard implements Cloneable {

	public static final String O = "O";
	public static final String X = "X";
	public static final String EMPTY = "-"; 
	private String[] board;
	private String playerToMove = X;
	public String winner;
	private int numO=2;//counting 0 in the board
	private int numX=2;//counting X in the board
	private double utility = -1; // 1: win for X, 0: win for O, 0.5: draw
	int square;
	
	public  BLOBSBoard(int n) {
		this.board = new String[n*n];
		for (int i =0; i<n*n; i++) {
			this.board[i] = EMPTY;
		}
		this.square = n;
		this.board[0]=X;
		this.board[getAbsPosition(square-1,square-1)] =X;
		this.board[getAbsPosition(0, square-1)]=O;
		this.board[getAbsPosition(square-1, 0)]=O;
	}
	
	public String getPlayerToMove() {
		return playerToMove;
	}
	
	public boolean isEmpty(int col, int row) {
		return board[getAbsPosition(col, row)] == EMPTY;
	}

	public String getValue(int col, int row) {
		return board[getAbsPosition(col, row)];
	}

	public double getUtility() {
		return utility;
	}

	public void mark(BLOBSMove2 action) {
		if (action.pass != true) {
			if (action.curLoc != null) {
				Move(action);
			}
			else{
				Add(action);
			}
		}
		analyzeUtility();
		playerToMove = (playerToMove == X ? O : X);
	}
	
	public void Add(BLOBSMove2 move) {
			board[getAbsPosition(move.newLoc.getYCoOrdinate(), move.newLoc.getXCoOrdinate())] = playerToMove;
			if (playerToMove == X)
				numX++;
			else
				numO++;
	}
	
	public void Move(BLOBSMove2 move) {
			board[getAbsPosition(move.newLoc.getYCoOrdinate(), move.newLoc.getXCoOrdinate())] = playerToMove;
			board[getAbsPosition(move.curLoc.getYCoOrdinate(), move.curLoc.getXCoOrdinate())] = EMPTY;
	
	}
	private void analyzeUtility() {
			if (numO>numX) {
				utility = 0;
				winner = O;
			}
			else if (numO<numX) {
				utility = 1;
				winner = X;
			}
			else { 
				utility =0.5;
				winner = "Both tie";
			}
	}
	
	public int getNumberOfMarkedPositions() {
		int retVal = 0;
		for (int col = 0; col < square; col++) {
			for (int row = 0; row < square; row++) {
				if (getValue(col,row)!=EMPTY) {
					retVal++;
				}
			}
		}
		return retVal;
	}

	public List<BLOBSMove2> getValidAddAction(){
		List<BLOBSMove2> toAdd = new ArrayList<BLOBSMove2>();
		boolean isValid = false;
		BLOBSMove2 tmploc;
		int startCol, startRow, endCol, endRow;
		
		//Find valid squares for adding pieces
		for (int j=0; j <square; j++) {
			for (int i=0; i <square; i++) {
				if(this.board[getAbsPosition(j,i)] == EMPTY) {//if the piece is playable aka empty
					//determine the area to search for a piece. Using min, max to make sure we do not exceed the edge of the board
					isValid = false;
					startCol = Math.max(j-1, 0);
					startRow = Math.max ( i - 1 , 0 );
	                endCol = Math.min ( j + 1, square - 1 );
	                endRow = Math.min ( i + 1, square - 1);
	                for ( int y = startCol ; y <= endCol; y++ )
	                     for ( int x = startRow ; x <= endRow; x++ ){
	                        if ( this.board[getAbsPosition(y,x)] == playerToMove ){
	                           isValid = true; // if at least 1 piece is present the move is valid
	                           //System.out.println ("move valid: ");
	                        }
	                     }
	                                          
	                  if ( isValid == true ){// Create the move since it's valid
	                	  tmploc = new BLOBSMove2(null, new XYLocation(i,j));
	                     toAdd.add ( tmploc );
	                     //System.out.printf ("\nAdding move [%d,%d]: %s", i, j, tmpmove.toString() );
	                  }//if for create the move since it's valid
					}//if the piece is playable aka empty
				}//for:row
			}//for: column
		return toAdd;
	} 
	
	public List<BLOBSMove2> getValidMoveAction(){
		List<BLOBSMove2> toMove = new ArrayList<BLOBSMove2>();
		//boolean isValid = false;
		BLOBSMove2 tmploc;
		int startCol, startRow, endCol, endRow;
		//Find valid move for moving pieces
				for ( int j = 0 ; j < square ; j++ ){   
		           for ( int i = 0 ; i < square; i++ ){   
		              if ( this.board[getAbsPosition(j,i)] == playerToMove ) { // find player pieces
		                   // Create an area to search for a valid place to move to using again min max not to exceed edges   
		                  startCol = Math.max ( j - 2, 0 );
		                  startRow = Math.max ( i - 2 , 0 );
		                  endCol = Math.min ( j + 2, square - 1 );
		                  endRow = Math.min ( i + 2, square - 1);       
		                  for ( int y = startCol ; y <= endCol; y++ ) {
		                     for ( int x = startRow ; x <= endRow; x++ ){
		                        if ( this.board[getAbsPosition(y,x)] == EMPTY ){
		                           //If the absolute distance between source and destination is 2 for either X or Y
		                           //then it means the destination square is 2 space away
		                           if ( Math.abs ( j - y ) == 2 || Math.abs ( i - x ) == 2 ) {
		                           		tmploc = new BLOBSMove2( new XYLocation(i,j), new XYLocation (x,y));
		                           		toMove.add ( tmploc );
		                           }//if for adding result    
		                        }//if the position is playable;        
		                     }//for start and end row
		                  }//for start and end col
		               }//if to find player pieces
		            }//first for: row   
		         } //first for: col     
		return toMove;
	}
	
	//get valid move
	public List<BLOBSMove2> getValidAction() {
		List<BLOBSMove2> toAdd = getValidAddAction();
		List<BLOBSMove2> toMove = getValidMoveAction();
		List<BLOBSMove2> result = new ArrayList<BLOBSMove2>();

		result.addAll(toAdd);
		result.addAll(toMove);
		if (result.isEmpty()) {
			result.add(new BLOBSMove2());
		}
		return	result;
	}//get valid move
	
	
	@Override
	public BLOBSBoard clone() {
		BLOBSBoard copy = null;
		try {
			copy = (BLOBSBoard) super.clone();
			copy.board = Arrays.copyOf(board, board.length);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace(); // should never happen...
		}
		return copy;
	}

	@Override
	public boolean equals(Object anObj) {
		if (anObj != null && anObj.getClass() == getClass()) {
			BLOBSBoard anotherState = (BLOBSBoard) anObj;
			for (int i = 0; i < 9; i++) {
				if (board[i] != anotherState.board[i]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		// Need to ensure equal objects have equivalent hashcodes (Issue 77).
		return toString().hashCode();
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		for (int row = 0; row < square; row++) {
			for (int col = 0; col < square; col++) {
				strBuilder.append(getValue(col, row) + " ");
			}
			strBuilder.append("\n");
		}
		return strBuilder.toString();
	}
	
	public boolean isTerminal() {
		return getNumberOfMarkedPositions() == board.length;
	}
	//
	// PRIVATE METHODS
	//
	private int getAbsPosition(int col, int row) {
		//int square = (int) Math.sqrt(board.length);
		return row * square + col;
	}

}
