package entity;

import java.util.ArrayList;
import java.util.List;

import entity.ColourBoard.Colour;
import entity.ExpansionBoard.Expansion;
import entity.Piece.Type;

public class GameBoard {
	
	public enum ImmobileStrike {
		
		UP,
		DOWN,
		BOTH
		
	}
	
	private Piece[][][] pieceBoard;
	private ExpansionBoard expansionBoard;
	private ColourBoard colourBoard;
	private Square whtCommSquare, blkCommSquare;
	private List<TierExchange> tierExchangeList;
	private boolean whtInCheck, blkInCheck, isInitialArrangement;
	
	public GameBoard() {
		
		this.pieceBoard = new Piece[9][9][3];
		this.expansionBoard = new ExpansionBoard();
		this.colourBoard = new ColourBoard();
		this.whtCommSquare = new Square();
		this.blkCommSquare = new Square();
		this.tierExchangeList = new ArrayList<TierExchange>();
		this.whtInCheck = this.blkInCheck = false;
		this.isInitialArrangement = true;
		
	}
	
	public GameBoard(GameBoard gameBoard) {
		
		this.pieceBoard = new Piece[9][9][3];
		
		for(int i = 0; i < 9; i ++)
			for(int j = 0; j < 9; j ++)
				for(int k = 0; k < 3; k ++)
					if(gameBoard.pieceBoard[j][i][k] != null)
						this.pieceBoard[j][i][k] = new Piece(gameBoard.pieceBoard[j][i][k]);
		
		this.expansionBoard = new ExpansionBoard(gameBoard.expansionBoard);
		this.colourBoard = new ColourBoard();
		this.whtCommSquare = new Square(gameBoard.whtCommSquare);
		this.blkCommSquare = new Square(gameBoard.blkCommSquare);
		
		this.tierExchangeList = new ArrayList<TierExchange>();
		this.tierExchangeList.addAll(gameBoard.tierExchangeList);
		
		this.whtInCheck = gameBoard.whtInCheck;
		this.blkInCheck = gameBoard.blkInCheck;
		this.isInitialArrangement = gameBoard.isInitialArrangement;
		
	}
	
	public Piece[][][] getPieceBoard() {
		
		return this.pieceBoard;
		
	}
	
	public ExpansionBoard getExpansionBoard() {
		
		return this.expansionBoard;
		
	}
	
	public ColourBoard getColourBoard() {
		
		return this.colourBoard;
		
	}
	
	public Square getCommSquare(boolean isWhite) {
		
		return (isWhite ? this.whtCommSquare : this.blkCommSquare);
		
	}
	
	public List<TierExchange> getTierExchangeList() {
		
		return this.tierExchangeList;
		
	}
	
	public boolean getInCheck(boolean isWhite) {
		
		return (isWhite ? this.whtInCheck : this.blkInCheck);
		
	}
	
	public boolean getIsInitialArrangement() {
		
		return this.isInitialArrangement;
		
	}
	
	public void setCommSquare(Piece p, Square s) {
		
		if(p.getType() == Type.COMMANDER) {
			
			if(p.getIsWhite())
				this.whtCommSquare.setCoords(s);
			
			else
				this.blkCommSquare.setCoords(s);
			
		}
		
	}
	
	public void setInCheck(boolean isWhite, boolean b) {
		
		if(isWhite)
			this.whtInCheck = b;
		
		else
			this.blkInCheck = b;
		
	}
	
	public void setIsInitialArrangement(boolean b) {
		
		this.isInitialArrangement = b;
		
	}
	
	public Piece getPiece(int x, int y, int z) {
		
		return this.pieceBoard[x][y][z];
		
	}
	
	public Piece getPiece(Square s, int z) {
		
		return this.pieceBoard[s.getX()][s.getY()][z];
		
	}
	
	public Piece getPiece(int x, int y) {
		
		for(int i = 2; i >= 0; i --)
			if(this.pieceBoard[x][y][i] != null)
				return this.pieceBoard[x][y][i];
		
		return null;
		
	}
	
	public Piece getPiece(Square s) {
		
		for(int i = 2; i >= 0; i --)
			if(this.pieceBoard[s.getX()][s.getY()][i] != null)
				return this.pieceBoard[s.getX()][s.getY()][i];
		
		return null;
		
	}
	
	public Piece getPiece(Type t, Square s) {
		
		for(int i = 0; i < 3; i ++)
			if(this.pieceBoard[s.getX()][s.getY()][i] != null)
				if(this.pieceBoard[s.getX()][s.getY()][i].getType() == t)
					return this.pieceBoard[s.getX()][s.getY()][i];
		
		return null;
		
	}
	
	public void setPiece(Piece p, int x, int y, int z) {
		
		this.pieceBoard[x][y][z] = p;
		
	}
	
	public void setPiece(Piece p, Square s, int z) {
		
		this.pieceBoard[s.getX()][s.getY()][z] = p;
		
	}
	
	public void setPiece(Piece p, int x, int y) {
		
		if(this.pieceBoard[x][y][0] == null)
			this.pieceBoard[x][y][0] = p;
		
		else if(this.pieceBoard[x][y][1] == null)
			this.pieceBoard[x][y][1] = p;
		
		else if(this.pieceBoard[x][y][2] == null)
			this.pieceBoard[x][y][2] = p;
		
	}
	
	public void setPiece(Piece p, Square s) {
		
		if(this.pieceBoard[s.getX()][s.getY()][0] == null)
			this.pieceBoard[s.getX()][s.getY()][0] = p;
		
		else if(this.pieceBoard[s.getX()][s.getY()][1] == null)
			this.pieceBoard[s.getX()][s.getY()][1] = p;
		
		else if(this.pieceBoard[s.getX()][s.getY()][2] == null)
			this.pieceBoard[s.getX()][s.getY()][2] = p;
		
	}
	
	public void removePiece(Square s, int z) {
		
		this.pieceBoard[s.getX()][s.getY()][z] = null;
		
	}
	
	public void removePiece(Square s) {
		
		for(int i = 2; i >= 0; i --)
			if(this.pieceBoard[s.getX()][s.getY()][i] != null) {
				
				this.pieceBoard[s.getX()][s.getY()][i] = null;
				return;
				
			}
		
	}
	
	public int getTowerHeight(int x, int y) {
		
		for(int i = 0; i < 3; i ++)
			if(this.pieceBoard[x][y][i] == null)
				return i;
		
		return 3;
		
	}
	
	public int getTowerHeight(Square s) {
		
		for(int i = 0; i < 3; i ++)
			if(this.pieceBoard[s.getX()][s.getY()][i] == null)
				return i;
		
		return 3;
		
	}
	
	public int getPieceHeight(Type t, Square s) {
		
		for(int i = 0; i < 3; i ++)
			if(this.pieceBoard[s.getX()][s.getY()][i] != null)
				if(this.pieceBoard[s.getX()][s.getY()][i].typeEquals(t))
					return i;
		
		return -1;
		
	}
	
	public boolean contains(Piece p, Square s) {
		
		for(int i = 0; i < 3; i ++)
			if(this.pieceBoard[s.getX()][s.getY()][i] != null)
				if(this.pieceBoard[s.getX()][s.getY()][i].pieceEquals(p))
					return true;
		
		return false;
		
	}
	
	public boolean contains(Type t, Square s) {
		
		for(int i = 0; i < 3; i ++)
			if(this.pieceBoard[s.getX()][s.getY()][i] != null)
				if(this.pieceBoard[s.getX()][s.getY()][i].getType() == t)
					return true;
		
		return false;
		
	}
	
	public boolean fullTower(int x, int y) {
		
		return (this.pieceBoard[x][y][2] != null);
		
	}
	
	public boolean fullTower(Square s) {
		
		return (this.pieceBoard[s.getX()][s.getY()][2] != null);
		
	}
	
	public boolean isOccupied(int x, int y) {
		
		return (this.pieceBoard[x][y][0] != null);
		
	}
	
	public boolean isOccupied(Square s) {
		
		return (this.pieceBoard[s.getX()][s.getY()][0] != null);
		
	}
	
	public void setExpansionRange(Piece p, Square s) {
		
		if(p.getType() == Type.CATAPULT) {
			
			if(p.getIsWhite())
				this.expansionBoard.setExpansionRange(Expansion.WHITE_CATAPULT, s.getX(), s.getY());
			
			else
				this.expansionBoard.setExpansionRange(Expansion.BLACK_CATAPULT, s.getX(), s.getY());
			
		}
		
		else if(p.getType() == Type.FORTRESS) {
			
			if(p.getIsWhite())
				this.expansionBoard.setExpansionRange(Expansion.WHITE_FORTRESS, s.getX(), s.getY());
			
			else
				this.expansionBoard.setExpansionRange(Expansion.BLACK_FORTRESS, s.getX(), s.getY());
			
		}
		
	}
	
	public void removeExpansionRange(Square s) {
		
		Piece p = getPiece(s, 0);
		
		if(p.getType() == Type.CATAPULT) {
			
			if(p.getIsWhite())
				this.expansionBoard.removeExpansionRange(Expansion.WHITE_CATAPULT);
			
			else
				this.expansionBoard.removeExpansionRange(Expansion.BLACK_CATAPULT);
			
		}
		
		else if(p.getType() == Type.FORTRESS) {
			
			if(p.getIsWhite())
				this.expansionBoard.removeExpansionRange(Expansion.WHITE_FORTRESS);
			
			else
				this.expansionBoard.removeExpansionRange(Expansion.BLACK_FORTRESS);
			
		}
		
	}
	
	//Placing a piece refers to adding an inactive piece onto the board
	public boolean isPlaceable(Hand whtHand, Hand blkHand, Piece p, Square s) {
		
		//A piece isn't placeable if it's not stackable at the square in question
		if(!isStackable(p, s))
			return false;
		
		if(this.isInitialArrangement) {
			
			//If a player already has a pawn in their territory's file, then they cannot place another one in that file
			if(alreadyInColumn(Type.PAWN_BRONZE, s.getX(), p.getIsWhite() ? 0 : 6)) {
				
				if(p.isPawn())
					return false;
				
			}
			
			//If there isn't a Pawn in the file and the piece attempting to be placed isn't a Pawn,...
			else if(!p.isPawn()){
				
				//If there's only one space left in the file then it must be reserved for a Pawn
				if(spacesLeftInColumn(s.getX(), 0) == 1)
					return false;
				
				//If the piece being placed is a Commander and there's already 2 full towers in their territory's file, then it cannot be placed
				if(p.getType() == Type.COMMANDER && fullTowersInColumn(s.getX(), p.getIsWhite() ? 0 : 6) == 2)
					return false;
				
			}
			
		}
		
		else {
			
			if(isOccupied(s)) {
				
				//Cannot drop on top of a piece that isn't yours
				if(p.getIsWhiteAligned() != getPiece(s).getIsWhiteAligned())
					return false;
				
				//Only Catapults, Fortresses, Spies, and Clandestinites can be dropped on
				if(getPiece(s).getType() != Type.CATAPULT && getPiece(s).getType() != Type.FORTRESS && getPiece(s).getType() != Type.SPY && getPiece(s).getType() != Type.CLANDESTINITE)
					return false;
				
				//Only front pieces can be dropped on Clandestinites
				if(p.getIsBack()) {
					
					if(getPiece(s).getType() == Type.CLANDESTINITE)
						return false;
					
				}
				
				//Only back pieces can be dropped on Spies
				else {
					
					if(getPiece(s).getType() == Type.SPY)
						return false;
					
				}
				
			}
			
			if(p.getType() == Type.BRONZE || p.isPawn()) {
				
				//Bronzes and Pawns cannot be dropped into a file that already contains one of the same alignment
				if(alreadyInColumn(p, s.getX()))
					return false;
				
				//Achieving checkmate through dropping a Bronze or Pawn is disallowed
				if(dropCheckmates(whtHand, blkHand, p, s))
					return false;
				
			}
			
			//Dropping a piece while in check is only possible if it gets out of check
			if(getInCheck(p.getIsWhiteAligned()))
				if(dropLeavesInCheck(p, s))
					return false;
			
		}
		
		return true;
		
	}
	
	//Stacking a piece is concerned strictly when placing a piece and is only restrained by the pieces present at the square in question
	public boolean isStackable(Piece p, Square s) {
		
		//Cannot stack a piece on top of a full tower
		if(fullTower(s))
			return false;
		
		//Cannot stack a piece on a tower that already contains that piece (same colour and type)
		if(contains(p, s))
			return false;
		
		//Cannot stack a piece on top of a Commander
		if(isOccupied(s) && getPiece(s).getType() == Type.COMMANDER)
			return false;
		
		//Cannot stack a Catapult or Fortress on top of other pieces
		if(p.getType() == Type.CATAPULT || p.getType() == Type.FORTRESS)
			if(isOccupied(s))
				return false;
		
		return true;
		
	}
	
	public boolean alreadyInColumn(Piece p, int x) {
		
		for(int i = 0; i < 9; i ++)
			for(int j = 0; j < 3; j ++)
				if(this.pieceBoard[x][i][j] != null)
					if(this.pieceBoard[x][i][j].pieceEquals(p))
						return true;
		
		return false;
		
	}
	
	//Concerned with individual territories
	public boolean alreadyInColumn(Type t, int x, int y) {
		
		for(int i = y; i < y + 3; i ++)
			for(int j = 0; j < 3; j ++)
				if(this.pieceBoard[x][i][j] != null)
					if(this.pieceBoard[x][i][j].typeEquals(t))
						return true;
		
		return false;
		
	}
	
	private int fullTowersInColumn(int x, int y) {
		
		int count = 0;
		
		for(int i = y; i < y + 3; i ++)
			if(fullTower(x, i))
				count ++;
		
		return count;
		
	}
	
	private int spacesLeftInColumn(int x, int y) {
		
		int count = 0;
		
		for(int i = y; i < y + 3; i ++)
			for(int j = 0; j < 3; j ++) {
				
				if(this.pieceBoard[x][i][j] == null)
					count ++;
				
				else if(this.pieceBoard[x][i][j].getType() == Type.COMMANDER)
					break;
				
			}
		
		return count;
		
	}
	
	public boolean fullTerritory(boolean isWhite) {
		
		for(int i = (isWhite ? 0 : 6); i < (isWhite ? 3 : 9); i ++)
			for(int j = 0; j < 9; j ++)
				if(!isOccupied(j, i))
					return false;
		
		return true;
		
	}
	
	//Upon reaching the end of the board, a piece that is unable to move next turn is forcibly returned to the moving player's hand
	public boolean forcedRecovery(Piece p, Square s2, boolean capture) {
		
		int x2 = s2.getX();
		int y2 = s2.getY();
		
		//Only Spies, Pawns, and Lances can experience Forced Recovery
		if(p.getType() != Type.SPY && !p.isPawn() && !p.isLance())
			return false;
		
		//The z coordinate of the topmost piece at (x2, y2), -1 if (x2, y2) is unoccupied
		int z2 = getTowerHeight(s2) - 1;
		
		boolean isWhiteAligned = p.getIsWhiteAligned();
		
		//If the piece lands on an enemy, it inherits Gold's move set and therefore would be able to move next turn
		if(z2 >= (capture ? 1 : 0) && this.pieceBoard[x2][y2][z2 - (capture ? 1 : 0)].getIsWhiteAligned() != isWhiteAligned)
			return false;
		
		//A spy landing in the last rank or on the lowest tier out of expansion range in the second to last rank cannot move next turn
		if(p.getType() == Type.SPY) {
			
			if(y2 == (isWhiteAligned ? 8 : 0))
				return true;
			
			if(y2 == (isWhiteAligned ? 7 : 1))
				if(z2 + 1 == (capture ? 1 : 0) && !this.expansionBoard.isInRange(s2, isWhiteAligned))
					return true;
			
		}
		
		//Pawns and Lances landing in the last rank on the lowest tier out of expansion range cannot move next turn
		else {
			
			if(y2 == (isWhiteAligned ? 8 : 0))
				if(z2 + 1 == (capture ? 1 : 0) && !this.expansionBoard.isInRange(s2, isWhiteAligned))
					return true;
			
		}
		
		return false;
		
	}
	
	//For immobile strikes
	public boolean forcedRecovery(Square s, int attacking, int attacked) {
		
		Piece p = getPiece(s, attacking);
		
		if(p.getType() != Type.SPY && !p.isPawn() && !p.isLance())
			return false;
		
		if(getTowerHeight(s) == 3) {
			
			if(attacking == 2 || (attacking == 1 && attacked == 2))
				if(getPiece(s, 0).getIsWhiteAligned() == p.getIsWhiteAligned())
					if(cannotMove(p, s, 1))
						return true;
			
		}
		
		else if(getTowerHeight(s) == 2) {
			
			if(cannotMove(p, s, 0))
				return true;
			
		}
		
		return false;
		
	}
	
	public boolean forcedRecovery(Square s) {
		
		int z = getTowerHeight(s) - 1;
		
		if(z == -1)
			return false;
		
		Piece p = getPiece(s, z);
		
		if(p.getType() != Type.SPY && !p.isPawn() && !p.isLance())
			return false;
		
		if(z > 0)
			if(getPiece(s, z - 1).getIsWhiteAligned() != p.getIsWhiteAligned())
				return false;
		
		if(!cannotMove(p, s, z))
			return false;
		
		return true;
		
	}
	
	public boolean cannotMove(Piece p, Square s, int z) {
		
		boolean isWhiteAligned = p.getIsWhiteAligned();
		
		int y = s.getY();
		
		//A spy in the last rank or on the lowest tier out of expansion range in the second to last rank cannot move
		if(p.getType() == Type.SPY) {
			
			if(y == (isWhiteAligned ? 8 : 0))
				return true;
			
			if(y == (isWhiteAligned ? 7 : 1))
				if(z == 0 && !this.expansionBoard.isInRange(s, isWhiteAligned))
					return true;
			
		}
		
		//Pawns and Lances in the last rank on the lowest tier out of expansion range cannot move
		else {
			
			if(y == (isWhiteAligned ? 8 : 0))
				if(z == 0 && !this.expansionBoard.isInRange(s, isWhiteAligned))
					return true;
			
		}
		
		return false;
		
	}
	
	public void setMoveColours(Hand whtHand, Hand blkHand, Square s1) {
		
		Piece p = getPiece(s1);
		
		for(Square s2 : getSquareList(s1)) {
			
			if(!isMoveable(s1, s2))
				continue;
			
			if(isOccupied(s2) && getPiece(s2).getIsWhiteAligned() != p.getIsWhiteAligned()) {
				
				if(!moveLeavesInCheck(s1, s2, true)) {
					
					if((p.getType() != Type.BRONZE || !moveCheckmates(whtHand, blkHand, s1, s2, true)))
						this.colourBoard.setColour(Colour.RED, s2);
					
					else if(getTowerHeight(s2) < 3) {
						
						if(!moveLeavesInCheck(s1, s2, false))
							if((p.getType() != Type.BRONZE || !moveCheckmates(whtHand, blkHand, s1, s2, false)))
								this.colourBoard.setColour(Colour.BLUE, s2);
						
					}
					
				}
				
				else if(getTowerHeight(s2) < 3) {
					
					if(!moveLeavesInCheck(s1, s2, false))
						if((p.getType() != Type.BRONZE || !moveCheckmates(whtHand, blkHand, s1, s2, false)))
							this.colourBoard.setColour(Colour.BLUE, s2);
					
				}
				
			}
			
			else {
				
				if(getTowerHeight(s2) == 3)
					continue;
				
				if(isOccupied(s2) && getPiece(s2).getType() == Type.COMMANDER)
					continue;
				
				if(!moveLeavesInCheck(s1, s2, false))
					if(p.getType() != Type.BRONZE || !moveCheckmates(whtHand, blkHand, s1, s2, false))
						this.colourBoard.setColour(Colour.BLUE, s2);
				
			}
			
		}
		
	}
	
	public List<Square> getSquareList(Square s1) {
		
		int x1 = s1.getX();
		int y1 = s1.getY();
		
		List<Square> squareList;
		
		int z1 = getTowerHeight(s1) - 1;
		
		Piece p = this.pieceBoard[x1][y1][z1];
		boolean isWhiteAligned = p.getIsWhiteAligned();
		
		if(z1 == 0 || this.pieceBoard[x1][y1][z1 - 1].getIsWhiteAligned() == isWhiteAligned)
			squareList = p.getSquareList(x1, y1, z1 + ((z1 < 2 && p.canReceiveMRE() && this.expansionBoard.isInRange(s1, isWhiteAligned)) ? 1 : 0));
		
		else
			squareList = p.getGoldSquareList(x1, y1);
		
		return squareList;
		
	}
	
	public boolean isMoveable(Square s1, Square s2) {
		
		Piece p = getPiece(s1);
		
		int x1 = s1.getX();
		int y1 = s1.getY();
		
		int x2 = s2.getX();
		int y2 = s2.getY();
		
		int deltaX = x2 - x1;
		int deltaY = y2 - y1;
		
		if((x2 < 0 || x2 > 8) || (y2 < 0 || y2 > 8))
			return false;
		
		if(contains(p, s2))
			return false;
		
		if(p.getType() == Type.BRONZE)
			if(x2 != x1)
				if(alreadyInColumn(p, x2))
					return false;
		
		if((deltaX == -1 && deltaY == -2) || (deltaX == 1 && deltaY == -2)) {
			
			if(isBlockedByMRE(x2, y2 + 1, p.getIsWhiteAligned()))
				return false;
			
		}
		
		else if((deltaX == -1 && deltaY == 2) || (deltaX == 1 && deltaY == 2)) {
			
			if(isBlockedByMRE(x2, y2 - 1, p.getIsWhiteAligned()))
				return false;
			
		}
		
		else if(!p.canJump() || (p.getType() == Type.BOW || p.getType() == Type.CLANDESTINITE)) {
			
			if(isBlocked(s1, s2))
				return false;
			
		}
		
		return true;
		
	}
	
	private boolean isBlocked(Square s1, Square s2) {
		
		Piece p = getPiece(s1);
		
		int x1 = s1.getX();
		int y1 = s1.getY();
		
		int x2 = s2.getX();
		int y2 = s2.getY();
		
		int deltaX = x2 - x1;
		int deltaY = y2 - y1;
		
		if(deltaX < 0 && deltaY < 0) {
			
			for(int i = 1; x2 + i < x1; i ++)
				if(p.canJump() ? isBlockedByMRE(x2 + i, y2 + i, p.getIsWhiteAligned()) : isOccupied(x2 + i, y2 + i))
					return true;
			
		}
		
		else if(deltaX == 0 && deltaY < 0) {
			
			for(int i = 1; y2 + i < y1; i ++)
				if(p.canJump() ? isBlockedByMRE(x2, y2 + i, p.getIsWhiteAligned()) : isOccupied(x2, y2 + i))
					return true;
			
		}
		
		else if(deltaX > 0 && deltaY < 0) {
			
			for(int i = 1; x2 - i > x1; i ++)
				if(p.canJump() ? isBlockedByMRE(x2 - i, y2 + i, p.getIsWhiteAligned()) : isOccupied(x2 - i, y2 + i))
					return true;
			
		}
		
		else if(deltaX < 0 && deltaY == 0) {
			
			for(int i = 1; x2 + i < x1; i ++)
				if(p.canJump() ? isBlockedByMRE(x2 + i, y2, p.getIsWhiteAligned()) : isOccupied(x2 + i, y2))
					return true;
			
		}
		
		else if(deltaX > 0 && deltaY == 0) {
			
			for(int i = 1; x2 - i > x1; i ++)
				if(p.canJump() ? isBlockedByMRE(x2 - i, y2, p.getIsWhiteAligned()) : isOccupied(x2 - i, y2))
					return true;
			
		}
		
		else if(deltaX < 0 && deltaY > 0) {
			
			for(int i = 1; x2 + i < x1; i ++)
				if(p.canJump() ? isBlockedByMRE(x2 + i, y2 - i, p.getIsWhiteAligned()) : isOccupied(x2 + i, y2 - i))
					return true;
			
		}
		
		else if(deltaX == 0 && deltaY > 0) {
			
			for(int i = 1; y2 - i > y1; i ++)
				if(p.canJump() ? isBlockedByMRE(x2, y2 - i, p.getIsWhiteAligned()) : isOccupied(x2, y2 - i))
					return true;
			
		}
		
		else if(deltaX > 0 && deltaY > 0) {
			
			for(int i = 1; x2 - i > x1; i ++)
				if(p.canJump() ? isBlockedByMRE(x2 - i, y2 - i, p.getIsWhiteAligned()) : isOccupied(x2 - i, y2 - i))
					return true;
			
		}
		
		return false;
		
	}
	
	private boolean isBlockedByMRE(int x, int y, boolean isWhiteAligned) {
		
		return (isOccupied(x, y) && getPiece(x, y).getIsWhiteAligned() != isWhiteAligned && this.expansionBoard.isInRange(x, y, !isWhiteAligned));
		
	}
	
	public boolean isUnderAttack(boolean isWhiteTurn) {
		
		Square s1 = new Square();
		
		for(int i = 0; i < 9; i ++)
			for(int j = 0; j < 9; j ++) {
				
				if(!isOccupied(j, i))
					continue;
				
				Piece p = getPiece(j, i);
				
				if(p.getIsWhiteAligned() == isWhiteTurn)
					continue;
				
				s1.setCoords(j, i);
				
				List<Square> squareList;
				
				int z = getTowerHeight(s1) - 1;
				
				if(z == 0 || getPiece(j, i, z - 1).getIsWhiteAligned() != isWhiteTurn)
					squareList = p.getSquareList(j, i, z + ((p.canReceiveMRE() && z < 2 && this.expansionBoard.isInRange(j, i, !isWhiteTurn)) ? 1 : 0));
				
				else
					squareList = p.getGoldSquareList(j, i);
				
				for(Square s2 : squareList)
					if(isMoveable(s1, s2))
						if(s2.coordsEqual(isWhiteTurn ? this.whtCommSquare : this.blkCommSquare))
							return true;
				
			}
		
		return false;
		
	}
	
	private boolean moveLeavesInCheck(Square s1, Square s2, boolean capture) {
		
		GameBoard tempGameBoard = new GameBoard(this);
		Piece p1 = tempGameBoard.getPiece(s1);
		
		int z2 = tempGameBoard.getTowerHeight(s2) - 1;
		
		tempGameBoard.removePiece(s1);
		
		if(p1.getType() == Type.COMMANDER) {
			
			if(z2 >= (capture ? 1 : 0))
				if(tempGameBoard.getPiece(s2, z2 - (capture ? 1 : 0)).getIsWhiteAligned() != p1.getIsWhite())
					if(tempGameBoard.getPiece(s2, z2 - (capture ? 1 : 0)).getType() != Type.FORTRESS)
						return true;
			
			tempGameBoard.setCommSquare(p1, s2);
			
		}
		
		if(!tempGameBoard.forcedRecovery(p1, s2, capture)) {
			
			if(capture)
				if(z2 == 0)
					tempGameBoard.removeExpansionRange(s2);
			
			tempGameBoard.setPiece(p1, s2, z2 + (capture ? 0 : 1));
			
			if(capture)
				if(p1.getType() == Type.BRONZE)
					if(tempGameBoard.betrayalLeavesInCheck(s2))
						return true;
			
		}
		
		else if(capture)
			if(!tempGameBoard.getPiece(s2).isCatapultOrFortress())
				tempGameBoard.removePiece(s2, z2);
		
		while(forcedRecovery(s1))
			tempGameBoard.removePiece(s1);
		
		if(tempGameBoard.isUnderAttack(p1.getIsWhiteAligned()))
			return true;
		
		return false;
		
	}
	
	//For checking if moving to a square would leave in check if attacking that square wouldn't (middle clicking on red)
	public boolean moveLeavesInCheck(Piece p, Square s) {
		
		GameBoard tempGameBoard = new GameBoard(this);
		
		if(p.getType() == Type.COMMANDER) {
			
			if(tempGameBoard.getPiece(s).getType() != Type.FORTRESS)
				return true;
			
			tempGameBoard.setCommSquare(p, s);
			
		}
		
		tempGameBoard.setPiece(p, s);
		
		if(tempGameBoard.isUnderAttack(p.getIsWhiteAligned()))
			return true;
		
		return false;
		
	}
	
	private boolean dropLeavesInCheck(Piece p, Square s) {
		
		if(!getInCheck(p.getIsWhiteAligned()))
			return false;
		
		GameBoard tempGameBoard = new GameBoard(this);
		
		tempGameBoard.setPiece(p, s);
		
		if(forcedRecovery(s))
			return true;
		
		if(tempGameBoard.isUnderAttack(p.getIsWhiteAligned()))
			return true;
		
		return false;
		
	}
	
	private boolean immobileStrikeLeavesInCheck(Square s, int attacking, int attacked) {
		
		GameBoard tempGameBoard = new GameBoard(this);
		
		boolean isWhiteTurn = tempGameBoard.getPiece(s, attacking).getIsWhiteAligned();
		
		if(tempGameBoard.getTowerHeight(s) == 3) {
			
			if(attacking == 2 && attacked == 1) {
				
				if(!tempGameBoard.forcedRecovery(s, 2, 1)) {
					
					tempGameBoard.setPiece(tempGameBoard.getPiece(s, 2), s, 1);
					tempGameBoard.removePiece(s, 2);
					
				}
				
				else {
					
					tempGameBoard.removePiece(s, 2);
					tempGameBoard.removePiece(s, 1);
					
				}
				
			}
			
			else if(attacking == 1 && attacked == 0) {
				
				tempGameBoard.removeExpansionRange(s);
				
				tempGameBoard.setPiece(tempGameBoard.getPiece(s, 1), s, 0);
				tempGameBoard.setPiece(tempGameBoard.getPiece(s, 2), s, 1);
				tempGameBoard.removePiece(s, 2);
				
			}
			
			else if(attacking == 1 && attacked == 2) {
				
				if(!tempGameBoard.forcedRecovery(s, 1, 2)) {
					
					tempGameBoard.removePiece(s, 2);
					
					if(tempGameBoard.getPiece(s, 1).getType() == Type.BRONZE && tempGameBoard.betrayalLeavesInCheck(s))
						return true;
					
				}
				
				else {
					
					tempGameBoard.removePiece(s, 2);
					tempGameBoard.removePiece(s, 1);
					
				}
				
			}
			
			else if(attacking == 0 && attacked == 1) {
				
				tempGameBoard.setPiece(tempGameBoard.getPiece(s, 2), s, 1);
				tempGameBoard.removePiece(s, 2);
				
			}
			
		}
		
		else if(tempGameBoard.getTowerHeight(s) == 2) {
			
			if(attacking == 1 && attacked == 0) {
				
				if(!tempGameBoard.forcedRecovery(s, 1, 0)) {
					
					tempGameBoard.removeExpansionRange(s);
					
					tempGameBoard.setPiece(tempGameBoard.getPiece(s, 1), s, 0);
					tempGameBoard.removePiece(s, 1);
					
				}
				
				else {
					
					tempGameBoard.removePiece(s, 1);
					
					if(!tempGameBoard.getPiece(s, 0).isCatapultOrFortress()) {
						
						tempGameBoard.removeExpansionRange(s);
						tempGameBoard.removePiece(s, 0);
						
					}
					
				}
				
			}
			
			else if(attacking == 0 && attacked == 1) {
				
				if(!tempGameBoard.forcedRecovery(s, 0, 1))
					tempGameBoard.removePiece(s, 1);
				
				else {
					
					tempGameBoard.removePiece(s, 1);
					tempGameBoard.removePiece(s, 0);
					
				}
				
			}
			
		}
		
		while(forcedRecovery(s))
			tempGameBoard.removePiece(s);
		
		if(tempGameBoard.isUnderAttack(isWhiteTurn))
			return true;
		
		return false;
		
	}
	
	private boolean betrayalLeavesInCheck(Square s) {
		
		GameBoard tempGameBoard = new GameBoard(this);
		Piece p = tempGameBoard.getPiece(s);
		
		int z = tempGameBoard.getTowerHeight(s) - 1;
		
		if(z == 0)
			return false;
		
		for(int i = 0; i < z; i ++)
			if(tempGameBoard.getPiece(s, i).getIsWhiteAligned() != p.getIsWhiteAligned()) {
				
				if(i == 0)
					tempGameBoard.removeExpansionRange(s);
				
				tempGameBoard.getPiece(s, i).flipPiece();
				
			}
		
		if(tempGameBoard.isUnderAttack(p.getIsWhiteAligned()))
			return true;
		
		return false;
		
	}
	
	private boolean substitutionLeavesInCheck(Square s1, Square s2) {
		
		GameBoard tempGameBoard = new GameBoard(this);
		Piece tempPiece = tempGameBoard.getPiece(s1, 0);
		
		tempGameBoard.setPiece(tempGameBoard.getPiece(s2), s1, 0);
		tempGameBoard.setPiece(tempPiece, s2, tempGameBoard.getTowerHeight(s2) - 1);
		
		tempGameBoard.setCommSquare(tempGameBoard.getPiece(s1, 0), s1);
		
		if(tempGameBoard.isUnderAttack(tempGameBoard.getPiece(s1, 0).getIsWhite()))
			return true;
		
		return false;
		
	}
	
	private boolean moveCheckmates(Hand whtHand, Hand blkHand, Square s1, Square s2, boolean capture) {
		
		GameBoard tempGameBoard = new GameBoard(this);
		Piece p1 = tempGameBoard.getPiece(s1);
		
		int z2 = tempGameBoard.getTowerHeight(s2) - 1;
		
		tempGameBoard.removePiece(s1);
		
		if(p1.getType() == Type.COMMANDER) {
			
			if(z2 >= (capture ? 1 : 0))
				if(tempGameBoard.getPiece(s2, z2 - (capture ? 1 : 0)).getIsWhiteAligned() != p1.getIsWhite())
					if(tempGameBoard.getPiece(s2, z2 - (capture ? 1 : 0)).getType() != Type.FORTRESS)
						return false;
			
			tempGameBoard.setCommSquare(p1, s2);
			
		}
		
		if(!tempGameBoard.forcedRecovery(p1, s2, capture)) {
			
			if(capture)
				if(z2 == 0)
					tempGameBoard.removeExpansionRange(s2);
			
			tempGameBoard.setPiece(p1, s2, z2 + (capture ? 0 : 1));
			
			if(capture)
				if(p1.getType() == Type.BRONZE)
					if(tempGameBoard.betrayalLeavesInCheck(s2))
						return false;
			
		}
		
		else if(capture)
			if(!tempGameBoard.getPiece(s2).isCatapultOrFortress())
				tempGameBoard.removePiece(s2, z2);
		
		while(forcedRecovery(s1))
			tempGameBoard.removePiece(s1);
		
		if(tempGameBoard.isUnderAttack(p1.getIsWhiteAligned()))
			return false;
		
		if(tempGameBoard.isUnderAttack(!p1.getIsWhiteAligned())) {
			
			tempGameBoard.setInCheck(!p1.getIsWhiteAligned(), true);
			
			if(tempGameBoard.checkmate(whtHand, blkHand, !p1.getIsWhiteAligned()))
				return true;
			
		}
		
		return false;
		
	}
	
	private boolean dropCheckmates(Hand whtHand, Hand blkHand, Piece p, Square s) {
		
		GameBoard tempGameBoard = new GameBoard(this);
		
		tempGameBoard.setPiece(p, s);
		
		if(forcedRecovery(s))
			return false;
		
		if(tempGameBoard.isUnderAttack(p.getIsWhiteAligned()))
			return false;
		
		if(tempGameBoard.isUnderAttack(!p.getIsWhiteAligned())) {
			
			tempGameBoard.setInCheck(!p.getIsWhiteAligned(), true);
			
			if(tempGameBoard.checkmate(whtHand, blkHand, !p.getIsWhiteAligned()))
				return true;
			
		}
		
		return false;
		
	}
	
	public boolean checkmate(Hand whtHand, Hand blkHand, boolean isWhiteTurn) {
		
		GameBoard tempGameBoard = new GameBoard(this);
		
		Hand tempWhtHand = new Hand(whtHand);
		Hand tempBlkHand = new Hand(blkHand);
		
		Square s = new Square();
		
		boolean handIsEmpty = false;
		boolean handOnlyContainsPawnAndOrBronze = false;
		boolean handOnlyContainsPawn = false;
		boolean handOnlyContainsBronze = false;
		
		if(isWhiteTurn) {
			
			if(tempWhtHand.isEmpty())
				handIsEmpty = true;
			
			else {
				
				if(tempWhtHand.onlyContainsPawnAndOrBronze()) {
					
					handOnlyContainsPawnAndOrBronze = true;
					
					if(tempWhtHand.onlyContainsPawn())
						handOnlyContainsPawn = true;
					
					else if(tempWhtHand.onlyContainsBronze())
						handOnlyContainsBronze = true;
					
				}
				
			}
			
		}
			
		
		else {
			
			if(tempBlkHand.isEmpty())
				handIsEmpty = true;
			
			else {
				
				if(tempBlkHand.onlyContainsPawnAndOrBronze()) {
					
					handOnlyContainsPawnAndOrBronze = true;
					
					if(tempBlkHand.onlyContainsPawn())
						handOnlyContainsPawn = true;
					
					else if(tempBlkHand.onlyContainsBronze())
						handOnlyContainsBronze = true;
					
				}
				
			}
			
		}
		
		for(int i = 0; i < 9; i ++)
			for(int j = 0; j < 9; j ++) {
				
				s.setCoords(j, i);
				
				if(tempGameBoard.isOccupied(s)) {
					
					if(tempGameBoard.getPiece(s).getIsWhiteAligned() == isWhiteTurn) {
						
						tempGameBoard.setMoveColours(tempWhtHand, tempBlkHand, s);
						
						if(!tempGameBoard.colourBoard.isEmpty())
							return false;
						
					}
					
					if(tempGameBoard.isOrange(s, isWhiteTurn))
						return false;
					
					if(tempGameBoard.isRed(s, isWhiteTurn) != null)
						return false;
					
				}
				
				else if(!handIsEmpty) {
					
					if(handOnlyContainsPawnAndOrBronze) {
						
						if(handOnlyContainsPawn) {
							
							Piece p = new Piece(Type.PAWN_BRONZE, isWhiteTurn, false);
							
							if(!tempGameBoard.alreadyInColumn(p, j)) {
								
								tempGameBoard.setPiece(p, j, i, 0);
								
								if(isWhiteTurn)
									tempWhtHand.removePawn();
								
								else
									tempBlkHand.removePawn();
								
								if(!tempGameBoard.checkmate(tempWhtHand, tempBlkHand, !isWhiteTurn))
									if(!tempGameBoard.dropLeavesInCheck(p, s))
										return false;
								
							}
							
						}
						
						else if(handOnlyContainsBronze) {
							
							Piece p = new Piece(Type.BRONZE, !isWhiteTurn, false);
							
							if(!tempGameBoard.alreadyInColumn(p, j)) {
								
								tempGameBoard.setPiece(p, j, i, 0);
								
								if(isWhiteTurn)
									tempWhtHand.removeSpecifiedPiece(p);
								
								else
									tempBlkHand.removeSpecifiedPiece(p);
								
								if(!tempGameBoard.checkmate(tempWhtHand, tempBlkHand, !isWhiteTurn))
									if(!tempGameBoard.dropLeavesInCheck(p, s))
										return false;
								
							}
							
						}
						
						else {
							
							Piece p1 = new Piece(Type.PAWN_BRONZE, isWhiteTurn, false);
							Piece p2 = new Piece(Type.BRONZE, !isWhiteTurn, false);
							
							if(!tempGameBoard.alreadyInColumn(p1, j)) {
								
								tempGameBoard.setPiece(p1, j, i, 0);
								
								if(isWhiteTurn)
									tempWhtHand.removePawn();
								
								else
									tempBlkHand.removePawn();
								
								if(!tempGameBoard.checkmate(tempWhtHand, tempBlkHand, !isWhiteTurn))
									if(!tempGameBoard.dropLeavesInCheck(p1, s))
										return false;
								
							}
							
							if(!tempGameBoard.alreadyInColumn(p2, j)) {
								
								tempGameBoard.setPiece(p2, j, i, 0);
								
								if(isWhiteTurn)
									tempWhtHand.removeSpecifiedPiece(p2);
								
								else
									tempBlkHand.removeSpecifiedPiece(p2);
								
								if(!tempGameBoard.checkmate(tempWhtHand, tempBlkHand, !isWhiteTurn))
									if(!tempGameBoard.dropLeavesInCheck(p2, s))
										return false;
								
							}
							
						}
						
					}
					
					else {
						
						Piece p = new Piece(Type.PISTOL, !isWhiteTurn, false);
						
						if(!tempGameBoard.dropLeavesInCheck(p, s))
							return false;
						
					}
						
				}
				
			}
		
		return true;
		
	}
	
	public ImmobileStrike isRed(Square s, boolean isWhiteTurn) {
		
		//In a tower of three...
		if(getTowerHeight(s) == 3) {
			
			//If the top piece is ours...
			if(getPiece(s, 2).getIsWhiteAligned() == isWhiteTurn) {
				
				//And the middle piece isn't ours, we can attack the middle piece regardless of the bottom piece's alignment
				if(getPiece(s, 1).getIsWhiteAligned() != isWhiteTurn) {
					
					if(!immobileStrikeLeavesInCheck(s, 2, 1))
						return ImmobileStrike.DOWN;
					
				}
				
				//And the middle piece is ours, but the bottom piece isn't ours, we can attack the bottom piece
				else if(getPiece(s, 0).getIsWhiteAligned() != isWhiteTurn){
					
					if(!immobileStrikeLeavesInCheck(s, 1, 0))
						return ImmobileStrike.DOWN;
					
				}
				
			}
			
			//If the top piece isn't ours, but the middle piece is ours...
			else if(getPiece(s, 1).getIsWhiteAligned() == isWhiteTurn) {
				
				//And the bottom piece isn't ours, we can attack either the top piece or the bottom piece
				if(getPiece(s, 0).getIsWhiteAligned() != isWhiteTurn) {
					
					boolean upLeavesInCheck = immobileStrikeLeavesInCheck(s, 1, 2);
					boolean downLeavesInCheck = immobileStrikeLeavesInCheck(s, 1, 0);
					
					if(!upLeavesInCheck && !downLeavesInCheck)
						return ImmobileStrike.BOTH;
					
					else if(!upLeavesInCheck)
						return ImmobileStrike.UP;
					
					else if(!downLeavesInCheck)
						return ImmobileStrike.DOWN;
					
				}
				
				//And the bottom piece is ours, we can attack the top piece
				else {
					
					if(!immobileStrikeLeavesInCheck(s, 1, 2))
						return ImmobileStrike.UP;
					
				}
				
			}
			
			//If the top piece isn't ours, and the middle piece isn't ours, but the bottom piece is ours, and it isn't a Fortress, we can attack the middle piece
			else if(getPiece(s, 0).getIsWhiteAligned() == isWhiteTurn && getPiece(s, 0).getType() != Type.FORTRESS) {
				
				if(!immobileStrikeLeavesInCheck(s, 0, 1))
					return ImmobileStrike.UP;
				
			}
			
		}
		
		//In a tower of two...
		else if(getTowerHeight(s) == 2) {
			
			//If the top piece is ours...
			if(getPiece(s, 1).getIsWhiteAligned() == isWhiteTurn) {
				
				//And the bottom piece isn't ours, we can attack the bottom piece
				if(getPiece(s, 0).getIsWhiteAligned() != isWhiteTurn) {
					
					if(!immobileStrikeLeavesInCheck(s, 1, 0))
						return ImmobileStrike.DOWN;
					
				}
				
			}
			
			//If the top piece isn't ours, but the bottom piece is ours, and it isn't a Fortress, we can attack the top piece
			else if(getPiece(s, 0).getIsWhiteAligned() == isWhiteTurn && getPiece(s, 0).getType() != Type.FORTRESS) {
				
				if(!immobileStrikeLeavesInCheck(s, 0, 1))
					return ImmobileStrike.UP;
				
			}
			
		}
		
		return null;
		
	}
	
	public boolean isOrange(Square s, boolean isWhiteTurn) {
		
		int x = s.getX();
		int y = s.getY();
		
		if(getInCheck(isWhiteTurn)) {
			
			if(getTowerHeight(s) == 1 && this.pieceBoard[x][y][0].getType() == Type.SAMURAI && this.pieceBoard[x][y][0].getIsWhite() == isWhiteTurn) {
				
				if(y - 1 >= 0 && isOccupied(x, y - 1) && getPiece(x, y - 1).getType() == Type.COMMANDER && getPiece(x, y - 1).getIsWhite() == isWhiteTurn) {
					
					if(!substitutionLeavesInCheck(s, new Square(x, y - 1)))
						return true;
					
				}
				
				else if(x + 1 <= 8 && isOccupied(x + 1, y) && getPiece(x + 1, y).getType() == Type.COMMANDER && getPiece(x + 1, y).getIsWhite() == isWhiteTurn) {
					
					if(!substitutionLeavesInCheck(s, new Square(x + 1, y)))
						return true;
					
				}
				
				else if(y + 1 <= 8 && isOccupied(x, y + 1) && getPiece(x, y + 1).getType() == Type.COMMANDER && getPiece(x, y + 1).getIsWhite() == isWhiteTurn) {
					
					if(!substitutionLeavesInCheck(s, new Square(x, y + 1)))
						return true;
					
				}
				
				else if(x - 1 >= 0 && isOccupied(x - 1, y) && getPiece(x - 1, y).getType() == Type.COMMANDER && getPiece(x - 1, y).getIsWhite() == isWhiteTurn) {
					
					if(!substitutionLeavesInCheck(s, new Square(x - 1, y)))
						return true;
					
				}
				
			}
			
		}
		
		else {
			
			for(TierExchange tierExchange : this.tierExchangeList)
				if(tierExchange.squareEquals(s))
					return false;
			
			if(getTowerHeight(s) == 3) {
				
				if((this.pieceBoard[x][y][2].getType() == Type.CAPTAIN && this.pieceBoard[x][y][2].getIsWhite() == isWhiteTurn) && ((this.pieceBoard[x][y][0].getType() != Type.CATAPULT && this.pieceBoard[x][y][0].getType() != Type.FORTRESS) && this.pieceBoard[x][y][0].getIsWhiteAligned() == isWhiteTurn))
					return true;
				
				if((this.pieceBoard[x][y][0].getType() == Type.CAPTAIN && this.pieceBoard[x][y][0].getIsWhite() == isWhiteTurn) && (this.pieceBoard[x][y][2].getType() != Type.COMMANDER && this.pieceBoard[x][y][2].getIsWhiteAligned() == isWhiteTurn))
					return true;
				
			}
			
		}
		
		return false;
		
	}
	
	public boolean isYellow(Square s, boolean isWhiteTurn) {
		
		return (isOccupied(s) && getPiece(s).getIsWhiteAligned() == isWhiteTurn);
		
	}
	
	public void equals(GameBoard gameBoard) {
		
		
		
	}
	
}
