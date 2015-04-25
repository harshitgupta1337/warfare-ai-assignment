package org.ai.warfare.main;

import java.util.ArrayList;
import java.util.List;

import org.ai.warfare.types.BoardConfiguration;
import org.ai.warfare.types.Move;
import org.ai.warfare.types.Occupancy;
import org.ai.warfare.types.Player;
import org.ai.warfare.types.Successor;
import org.ai.warfare.utils.CityPointsHolder;
import org.ai.warfare.utils.Utils;

public class GameRunner {

	private Controller blueController;
	private Controller greenController;
	private BoardConfiguration boardConfiguration;
	private List<Move> moves;
	private String boardPoints;
	
	public void setMoves(List<Move> moves){
		this.moves = moves;
	}
	
	public GameRunner(Controller blueController, Controller greenController, String boardPoints){
		this.blueController = blueController;
		this.blueController.setPlayer(Player.BLUE);
		this.greenController = greenController;
		this.greenController.setPlayer(Player.GREEN);
		boardConfiguration = generateInitialOccupancies();
		moves = new ArrayList<Move>();
		
		this.boardPoints = boardPoints;
		
		switch(boardPoints){
		case "Keren":
			CityPointsHolder.getInstance().assignKerenPoints();;
			break;
		case "Narvik":
			CityPointsHolder.getInstance().assignNarvikPoints();
			break;
		case "Sevastopol":
			CityPointsHolder.getInstance().assignSevastopolPoints();
			break;
		case "Smolensk":
			CityPointsHolder.getInstance().assignSmolenskPoints();
			break;
		case "Westerplatte":
			CityPointsHolder.getInstance().assignWesterplattePoints();
			break;
		}
	}

	public void printResults(){
		System.out.println("\n\n\n");
		System.out.println("City layout :" + boardPoints);
		System.out.println("Blue player : " + blueController.getName());
		System.out.println("Green player : " + greenController.getName());
		printMoveSequence();
		printBoardConfiguration(boardConfiguration);
		printFinalScore();
		System.out.println("Total number of game tree nodes expanded.");
		System.out.println("Blue : " + blueController.getName() + " : " + blueController.getTotalNumberOfNodesExpanded());
		System.out.println("Green : " + greenController.getName() + " : " + greenController.getTotalNumberOfNodesExpanded());
		System.out.print("Average number of game tree nodes expanded per move : ");
		System.out.println((double)(blueController.getTotalNumberOfNodesExpanded() + greenController.getTotalNumberOfNodesExpanded())/
				(double)(blueController.getTotalNumberOfMoves() + greenController.getTotalNumberOfMoves()
						));
		System.out.print("Average time per move : ");
		System.out.println((double)(blueController.getTotalTimeForMoves() + greenController.getTotalTimeForMoves())/
				(double)(blueController.getTotalNumberOfMoves() + greenController.getTotalNumberOfMoves()
						));
	}
	
	public void printMoveSequence(){
		System.out.println("Move sequence : ");
		for(Move move : moves){
			System.out.println(move.getPlayer() + " : " + move.getMoveType() + " " + transformColumn(move.getColumn()) + (move.getRow()+1));
		}
		
	}
	
	private char transformColumn(int column){
		return (char)('A'+column);
	}
	
	public void printFinalScore(){
		int blueScore = 0, greenScore = 0;
		for(int i=0;i<Utils.BOARD_SIZE;i++){
			for(int j=0;j<Utils.BOARD_SIZE;j++){
				if(boardConfiguration.getBoardPositions()[i][j] == Occupancy.BLUE)
					blueScore += CityPointsHolder.getInstance().getPoints()[i][j];
				else if(boardConfiguration.getBoardPositions()[i][j] == Occupancy.GREEN)
					greenScore += CityPointsHolder.getInstance().getPoints()[i][j];
			}
		}
		System.out.println("Blue's score : "+blueScore+"\tGreen's score : "+greenScore);
	}
	public void printBoardConfiguration(BoardConfiguration boardConfiguration){
		System.out.println("THE FINAL BOARD LOOKS LIKE THE FOLLOWING : ");
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
	
	public BoardConfiguration generateInitialOccupancies(){
		Occupancy arr[][] = new Occupancy[Utils.BOARD_SIZE][Utils.BOARD_SIZE];
		for(int i=0;i<Utils.BOARD_SIZE;i++){
			for(int j=0;j<Utils.BOARD_SIZE;j++){
				arr[i][j] = Occupancy.UNOCCUPIED;
			}
		}
		BoardConfiguration boardConfiguration = new BoardConfiguration();
		boardConfiguration.setBoardPositions(arr);
		return boardConfiguration;
	}
	
	public boolean isGameOver(BoardConfiguration boardConfiguration){
		int count = 0;
		for(int i=0;i<Utils.BOARD_SIZE;i++){
			for(int j=0;j<Utils.BOARD_SIZE;j++){
				if(boardConfiguration.getBoardPositions()[i][j] == Occupancy.UNOCCUPIED)
					count++;
			}
		}
		return (count==0);
	}
	
	public void run(){
		Controller turn = blueController;
		while(!isGameOver(boardConfiguration)){
			Successor successor = turn.makeMove(boardConfiguration);
			boardConfiguration = successor.getBoardConfiguration();
			moves.add(successor.getMove());
			if(turn == blueController){
				turn = greenController;
				
			}
			else{
				turn = blueController;
			}
		}
		printResults();
	}

	public Controller getBlueController() {
		return blueController;
	}

	public void setBlueController(Controller blueController) {
		this.blueController = blueController;
	}

	public Controller getGreenController() {
		return greenController;
	}

	public void setGreenController(Controller greenController) {
		this.greenController = greenController;
	}

	public BoardConfiguration getBoardConfiguration() {
		return boardConfiguration;
	}

	public void setBoardConfiguration(BoardConfiguration boardConfiguration) {
		this.boardConfiguration = boardConfiguration;
	}

	public List<Move> getMoves() {
		return moves;
	}
	
	public static void main(String args[]){
		List<String> boardPoints = new ArrayList<String>(){{ add("Keren"); add("Narvik"); add("Sevastopol"); add("Smolensk"); add("Westerplatte");}};
		for(String boardPoint : boardPoints){
			GameRunner gameRunner1 = new GameRunner(new MinMaxController(), new MinMaxController(), boardPoint);
			gameRunner1.run();
			
			GameRunner gameRunner2 = new GameRunner(new AlphaBetaPruningController(), new AlphaBetaPruningController(), boardPoint);
			gameRunner2.run();
			
			GameRunner gameRunner3 = new GameRunner(new MinMaxController(), new AlphaBetaPruningController(), boardPoint);
			gameRunner3.run();
			
			GameRunner gameRunner4 = new GameRunner(new AlphaBetaPruningController(), new MinMaxController(), boardPoint);
			gameRunner4.run();
			
		}
		System.out.println("\n\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n");
	}
}
