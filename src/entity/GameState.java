package entity;

public class GameState {
	
	private int[][][] pieceOrdinalBoard;
	private int occurrences;
	
	public GameState() {
		
		this.pieceOrdinalBoard = new int[9][9][3];
		occurrences = 0;
		
	}
	
	public GameState(int[][][] pieceOrdinalBoard) {
		
		this.pieceOrdinalBoard = new int[9][9][3];
		
		for(int i = 0; i < 9; i ++)
			for(int j = 0; j < 9; j ++)
				for(int k = 0; k < 3; k ++)
					this.pieceOrdinalBoard[j][i][k] = pieceOrdinalBoard[j][i][k];
		
		this.occurrences = 1;
		
	}
	
	public GameState(Piece[][][] pieceBoard) {
		
		this.pieceOrdinalBoard = new int[9][9][3];
		
		for(int i = 0; i < 9; i ++)
			for(int j = 0; j < 9; j ++)
				for(int k = 0; k < 3; k ++)
					if(pieceBoard[j][i][k] != null)
						this.pieceOrdinalBoard[j][i][k] = pieceBoard[j][i][k].getOrdinal();
		
		this.occurrences = 1;
		
	}
	
	public int[][][] getPieceOrdinalBoard() {
		
		return this.pieceOrdinalBoard;
		
	}
	
	public int getOrdinal(int x, int y, int z) {
		
		return this.pieceOrdinalBoard[x][y][z];
		
	}
	
	public int getOccurrences() {
		
		return this.occurrences;
		
	}
	
	public void setPieceOrdinal(int i, int x, int y, int z) {
		
		this.pieceOrdinalBoard[x][y][z] = i;
		
	}
	
	public void setOccurences(int i) {
		
		this.occurrences = i;
		
	}
	
	public void incrementOccurrences() {
		
		this.occurrences ++;
		
	}
	
	public void equals(GameState gameState) {
		
		
		
	}
	
}
