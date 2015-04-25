package org.ai.warfare.types;

import org.ai.warfare.utils.Utils;

public class BoardConfiguration {

	private Occupancy boardPositions[][];
	
	public BoardConfiguration(){
		setBoardPositions(new Occupancy[Utils.BOARD_SIZE][Utils.BOARD_SIZE]);
	}
	
	public BoardConfiguration(BoardConfiguration boardConfiguration){
		setBoardPositions(new Occupancy[Utils.BOARD_SIZE][Utils.BOARD_SIZE]);
		for(int i=0;i<Utils.BOARD_SIZE;i++){
			for(int j=0;j<Utils.BOARD_SIZE;j++){
				boardPositions[i][j] = boardConfiguration.getBoardPositions()[i][j];
			}
		}
	}

	public Occupancy[][] getBoardPositions() {
		return boardPositions;
	}

	public void setBoardPositions(Occupancy[][] occupancies) {
		this.boardPositions = occupancies;
	}
}
