package entity;

import java.util.ArrayList;
import java.util.List;

public class ExpansionBoard {
	
	public enum Expansion {
		
		WHITE_CATAPULT,
		WHITE_FORTRESS,
		BLACK_CATAPULT,
		BLACK_FORTRESS
		
	}
	
	private List<List<List<Expansion>>> expansionBoard;
	
	public ExpansionBoard() {
		
		initialize();
		
	}
	
	public ExpansionBoard(ExpansionBoard eb) {
		
		initialize();
		
		for(int i = 0; i < 9; i ++)
			for(int j = 0; j < 9; j ++)
				this.expansionBoard.get(j).get(i).addAll(eb.expansionBoard.get(j).get(i));
		
	}
	
	public void initialize() {
		
		this.expansionBoard = new ArrayList<List<List<Expansion>>>(9);
		
		for(int i = 0; i < 9; i ++) {
			
			this.expansionBoard.add(new ArrayList<List<Expansion>>(9));
			
			for(int j = 0; j < 9; j ++)
				this.expansionBoard.get(i).add(new ArrayList<Expansion>());
			
		}
		
	}
	
	public boolean contains(Expansion e, int x, int y) {
		
		return this.expansionBoard.get(x).get(y).contains(e);
		
	}
	
	public boolean isInRange(int x, int y, boolean isWhiteAligned) {
		
		if(isWhiteAligned) {
			
			if(this.expansionBoard.get(x).get(y).contains(Expansion.WHITE_CATAPULT) || this.expansionBoard.get(x).get(y).contains(Expansion.WHITE_FORTRESS))
				return true;
			
		}
		
		else {
			
			if(this.expansionBoard.get(x).get(y).contains(Expansion.BLACK_CATAPULT) || this.expansionBoard.get(x).get(y).contains(Expansion.BLACK_FORTRESS))
				return true;
			
		}
		
		return false;
		
	}
	
	public boolean isInRange(Square s, boolean isWhiteAligned) {
		
		int x = s.getX();
		int y = s.getY();
		
		if(isWhiteAligned) {
			
			if(this.expansionBoard.get(x).get(y).contains(Expansion.WHITE_CATAPULT) || this.expansionBoard.get(x).get(y).contains(Expansion.WHITE_FORTRESS))
				return true;
			
		}
		
		else {
			
			if(this.expansionBoard.get(x).get(y).contains(Expansion.BLACK_CATAPULT) || this.expansionBoard.get(x).get(y).contains(Expansion.BLACK_FORTRESS))
				return true;
			
		}
		
		return false;
		
	}
	
	public void setExpansionRange(Expansion e, int x, int y) {
		
		switch(e) {
		
		case BLACK_CATAPULT:
			for(Square s : getCatapultSquareList(x, y))
				if((s.getX() >= 0 && s.getX() <= 8) && (s.getY() >= 6 && s.getY() <= 8))
					this.expansionBoard.get(s.getX()).get(s.getY()).add(e);
			
			break;
		
		case BLACK_FORTRESS:
			for(int i = y; i >= 0; i --)
				this.expansionBoard.get(x).get(i).add(e);
			
			break;
		
		case WHITE_CATAPULT:
			for(Square s : getCatapultSquareList(x, y))
				if((s.getX() >= 0 && s.getX() <= 8) && (s.getY() >= 0 && s.getY() <= 2))
					this.expansionBoard.get(s.getX()).get(s.getY()).add(e);
			
			break;
		
		case WHITE_FORTRESS:
			for(int i = y; i <= 8; i ++)
				this.expansionBoard.get(x).get(i).add(e);
			
			break;
		
		default:
			break;
		
		}
		
	}
	
	private List<Square> getCatapultSquareList(int x, int y) {
		
		List<Square> squareList = new ArrayList<Square>();
		
		squareList.add(new Square(x, y - 2));
		squareList.add(new Square(x - 1, y - 1));
		squareList.add(new Square(x, y - 1));
		squareList.add(new Square(x + 1, y - 1));
		squareList.add(new Square(x - 2, y));
		squareList.add(new Square(x - 1, y));
		squareList.add(new Square(x, y));
		squareList.add(new Square(x + 1, y));
		squareList.add(new Square(x + 2, y));
		squareList.add(new Square(x - 1, y + 1));
		squareList.add(new Square(x, y + 1));
		squareList.add(new Square(x + 1, y + 1));
		squareList.add(new Square(x, y + 2));
		
		return squareList;
		
	}
	
	public void removeExpansionRange(Expansion e) {
		
		for(int i = 0; i < 9; i ++)
			for(int j = 0; j < 9; j ++)
				if(this.expansionBoard.get(i).get(j).contains(e))
					this.expansionBoard.get(i).get(j).remove(e);
		
	}
	
	public void equals(ExpansionBoard eb) {
		
		
		
	}
	
}
