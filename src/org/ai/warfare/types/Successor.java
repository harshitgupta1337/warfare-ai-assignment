package org.ai.warfare.types;

public class Successor {

	private BoardConfiguration boardConfiguration;
	
	private Move move;

	public BoardConfiguration getBoardConfiguration() {
		return boardConfiguration;
	}

	public void setBoardConfiguration(BoardConfiguration boardConfiguration) {
		this.boardConfiguration = boardConfiguration;
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}
}
