package org.ai.warfare.main;

import java.util.ArrayList;
import java.util.List;

import org.ai.warfare.types.BoardConfiguration;
import org.ai.warfare.types.Move;
import org.ai.warfare.types.MoveType;
import org.ai.warfare.types.Occupancy;
import org.ai.warfare.types.Player;
import org.ai.warfare.types.Successor;
import org.ai.warfare.utils.CityPointsHolder;
import org.ai.warfare.utils.Utils;

public abstract class Controller {

	protected Player player;
	protected long totalNumberOfNodesExpanded;
	protected long totalNumberOfMoves;
	protected long totalTimeForMoves;
	
	public Controller(){
		totalNumberOfNodesExpanded = 0;
		totalNumberOfMoves = 0;
		totalTimeForMoves = 0;
	}
	
	public long getTotalNumberOfNodesExpanded() {
		return totalNumberOfNodesExpanded;
	}

	public void setTotalNumberOfNodesExpanded(long totalNumberOfNodesExpanded) {
		this.totalNumberOfNodesExpanded = totalNumberOfNodesExpanded;
	}

	public long getTotalNumberOfMoves() {
		return totalNumberOfMoves;
	}

	public void setTotalNumberOfMoves(long totalNumberOfMoves) {
		this.totalNumberOfMoves = totalNumberOfMoves;
	}

	public long getTotalTimeForMoves() {
		return totalTimeForMoves;
	}

	public void setTotalTimeForMoves(long totalTimeForMoves) {
		this.totalTimeForMoves = totalTimeForMoves;
	}

	public Player getOppositePlayer(Player player){
		if(player == Player.BLUE)
			return Player.GREEN;
		else
			return Player.BLUE;
	}
	
	public abstract String getName();
	
	public Successor makeMove(BoardConfiguration boardConfiguration){
		totalNumberOfMoves++;
		long startTime = System.currentTimeMillis();
		Successor successor = getOptimalSuccessor(boardConfiguration);
		totalTimeForMoves += System.currentTimeMillis()-startTime;
		return successor;
	}
	
	public abstract Successor getOptimalSuccessor(BoardConfiguration boardConfiguration);
	
	protected boolean shouldBlitzing(BoardConfiguration boardConfiguration, int row, int column, Player player){
		Occupancy occupancy;
		if(player == Player.BLUE)
			occupancy = Occupancy.BLUE;
		else
			occupancy = Occupancy.GREEN;
		if(row-1 >= 0 && boardConfiguration.getBoardPositions()[row-1][column] == occupancy)
			return true;
		if(column-1 >= 0 && boardConfiguration.getBoardPositions()[row][column-1] == occupancy)
			return true;
		if(row+1 < Utils.BOARD_SIZE && boardConfiguration.getBoardPositions()[row+1][column] == occupancy)
			return true;
		if(column+1 < Utils.BOARD_SIZE && boardConfiguration.getBoardPositions()[row][column+1] == occupancy)
			return true;
		return false;
	}
	
	public Occupancy getOppositePlayerOccupancy(Player player){
		if(player == Player.BLUE)
			return Occupancy.GREEN;
		else
			return Occupancy.BLUE;
	}
	
	public Successor takeStep(BoardConfiguration _boardConfiguration, int row, int column, Player player){
		
		if(_boardConfiguration.getBoardPositions()[row][column] != Occupancy.UNOCCUPIED)
			return null;
		Occupancy occupancy;
		if(player == Player.BLUE)
			occupancy = Occupancy.BLUE;
		else
			occupancy = Occupancy.GREEN;
		BoardConfiguration boardConfiguration = new BoardConfiguration(_boardConfiguration);
		boardConfiguration.getBoardPositions()[row][column] = occupancy;
		Move move = new Move();
		move.setRow(row);
		move.setColumn(column);
		move.setPlayer(player);
		move.setMoveType(MoveType.CommandoParaDrop);
		if(shouldBlitzing(boardConfiguration, row, column, player)){
			if(row-1 >= 0 && boardConfiguration.getBoardPositions()[row-1][column] == getOppositePlayerOccupancy(player))
				boardConfiguration.getBoardPositions()[row-1][column] = occupancy;
			if(row+1 < Utils.BOARD_SIZE && boardConfiguration.getBoardPositions()[row+1][column] == getOppositePlayerOccupancy(player))
				boardConfiguration.getBoardPositions()[row+1][column] = occupancy;
			if(column-1 >= 0 && boardConfiguration.getBoardPositions()[row][column-1] == getOppositePlayerOccupancy(player))
				boardConfiguration.getBoardPositions()[row][column-1] = occupancy;
			if(column+1 < Utils.BOARD_SIZE && boardConfiguration.getBoardPositions()[row][column+1] == getOppositePlayerOccupancy(player))
				boardConfiguration.getBoardPositions()[row][column+1] = occupancy;
			move.setMoveType(MoveType.M1DeathBlitz);
		}
		Successor successor = new Successor();
		successor.setMove(move);
		successor.setBoardConfiguration(boardConfiguration);
		return successor;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Successor makeOptimalMove(BoardConfiguration boardConfiguration){
		return getOptimalSuccessor(boardConfiguration);
	}
	
	public List<Successor> computeSuccessors(BoardConfiguration boardConfiguration, Player player){
		totalNumberOfNodesExpanded++;
		List<Successor> successors = new ArrayList<Successor>();
		for(int i=0;i<Utils.BOARD_SIZE;i++){
			for(int j=0;j<Utils.BOARD_SIZE;j++){
				Successor successor = takeStep(boardConfiguration, i, j, player);
				if(successor != null)
					successors.add(successor);
			}
		}
		return successors;
	}
	
	public int calculateUtilityFunction(BoardConfiguration boardConfiguration){
		int blueScore = 0, greenScore =0;
		for(int i=0;i<Utils.BOARD_SIZE;i++){
			for(int j=0;j<Utils.BOARD_SIZE;j++){
				if(boardConfiguration.getBoardPositions()[i][j] == Occupancy.BLUE)
					blueScore+=CityPointsHolder.getInstance().getPoints()[i][j];
				else if(boardConfiguration.getBoardPositions()[i][j] == Occupancy.GREEN)
					greenScore+=CityPointsHolder.getInstance().getPoints()[i][j];
			}
		}
		return (greenScore - blueScore);
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
}
