package org.ai.warfare.test;

import org.ai.warfare.main.AlphaBetaPruningController;
import org.ai.warfare.main.Controller;
import org.ai.warfare.main.MinMaxController;
import org.ai.warfare.types.BoardConfiguration;
import org.ai.warfare.types.Occupancy;
import org.ai.warfare.types.Successor;
import org.ai.warfare.utils.Utils;

public class TestSuccessorGeneration {

	public static BoardConfiguration generateRandomOccupancies(){
		Occupancy arr[][] = new Occupancy[Utils.BOARD_SIZE][Utils.BOARD_SIZE];
		for(int i=0;i<Utils.BOARD_SIZE;i++){
			for(int j=0;j<Utils.BOARD_SIZE;j++){
				double rand = Math.random();
				if(rand < 0.33)
					arr[i][j] = Occupancy.BLUE;
				else if(rand <0.66)
					arr[i][j] = Occupancy.GREEN;
				else
					arr[i][j] = Occupancy.UNOCCUPIED;
			}
		}
		BoardConfiguration boardConfiguration = new BoardConfiguration();
		boardConfiguration.setBoardPositions(arr);
		return boardConfiguration;
	}
	public static void printBoardConfiguration(BoardConfiguration boardConfiguration){
		for(int i=0;i<Utils.BOARD_SIZE;i++){
			for(int j=0;j<Utils.BOARD_SIZE;j++){
				if(boardConfiguration.getBoardPositions()[i][j] == Occupancy.BLUE)
					System.out.print("B ");
				if(boardConfiguration.getBoardPositions()[i][j] == Occupancy.GREEN)
					System.out.print("G ");
				if(boardConfiguration.getBoardPositions()[i][j] == Occupancy.UNOCCUPIED)
					System.out.print("O ");
			}
			System.out.println();
		}	
	}
	public static int calculateNumberOfUnoccupiedPositions(BoardConfiguration boardConfiguration){
		int count = 0;
		for(int i=0;i<Utils.BOARD_SIZE;i++){
			for(int j=0;j<Utils.BOARD_SIZE;j++){
				if(boardConfiguration.getBoardPositions()[i][j] == Occupancy.UNOCCUPIED)
					count++;
			}
		}
		return count;
	}
	public static void main(String args[]){
		Controller controller = new AlphaBetaPruningController();
		BoardConfiguration initial = generateRandomOccupancies();
		printBoardConfiguration(initial);
		System.out.println("-----------------------------------------");
		/*for(Successor successor : controller.computeSuccessors(initial)){
			if(calculateNumberOfUnoccupiedPositions(initial) != calculateNumberOfUnoccupiedPositions(successor.getBoardConfiguration())+1){
				printBoardConfiguration(successor.getBoardConfiguration());
				System.out.println();
				System.out.println();
			}
			
		}*/
		//printBoardConfiguration(controller.makeOptimalMove(initial));
	}
}
