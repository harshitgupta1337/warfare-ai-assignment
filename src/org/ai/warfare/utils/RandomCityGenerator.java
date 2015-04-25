package org.ai.warfare.utils;


public class RandomCityGenerator {

	public int[][] generateInitialBoardConfiguration(){
		int board[][] = new int[Utils.BOARD_SIZE][Utils.BOARD_SIZE];
		for(int i=0;i<Utils.BOARD_SIZE;i++){
			for(int j=0;j<Utils.BOARD_SIZE;j++){
				board[i][j] = (int) (Math.random()*99 + 1);
			}
		}
		return board;
	}
}
