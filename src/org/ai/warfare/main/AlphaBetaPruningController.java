package org.ai.warfare.main;

import java.util.List;

import org.ai.warfare.types.BoardConfiguration;
import org.ai.warfare.types.Player;
import org.ai.warfare.types.Successor;

public class AlphaBetaPruningController extends Controller{

	private static int LOWER_BOUND = -10000;
	private static int UPPER_BOUND = 10000;
	private static int MAX_DEPTH = 4;
	
	public Successor getOptimalSuccessor(BoardConfiguration boardConfiguration){
		List<Successor> successors = computeSuccessors(boardConfiguration, player);
		if(successors.size() == 0)
			return null;
		Successor optimalSuccessor = successors.get(0);
		int alpha = LOWER_BOUND, beta = UPPER_BOUND;
		if(player == Player.GREEN){
			int maxMinMaxValue = LOWER_BOUND;
			for(Successor successor : successors){
				int temp = calculateMinMaxValueAlphaBetaPruning(successor.getBoardConfiguration(), 1, getOppositePlayer(player), alpha, beta);
				if(temp > alpha)
					alpha = temp;
				if(temp > maxMinMaxValue){
					maxMinMaxValue = temp;
					optimalSuccessor = successor;
				}
			}
		}else{
			int minMinMaxValue = UPPER_BOUND;
			for(Successor successor : successors){
				int temp = calculateMinMaxValueAlphaBetaPruning(successor.getBoardConfiguration(), 1, getOppositePlayer(player), alpha, beta);
				if(temp < beta)
					beta = temp;
				if(temp < minMinMaxValue){
					minMinMaxValue = temp;
					optimalSuccessor = successor;
				}
			}
		}
		return optimalSuccessor;
	}
	
	public int calculateMinMaxValueAlphaBetaPruning(BoardConfiguration boardConfiguration, int depth, Player player, int alpha, int beta){
		if(depth == MAX_DEPTH)
			return calculateUtilityFunction(boardConfiguration);
		if(isGameOver(boardConfiguration))
			return calculateUtilityFunction(boardConfiguration);
		List<Successor> successors = computeSuccessors(boardConfiguration, player);
		int bestMinMaxValue;
		if(player == Player.GREEN){
			bestMinMaxValue = LOWER_BOUND;
			for(Successor successor : successors){
				bestMinMaxValue = Math.max(bestMinMaxValue, calculateMinMaxValueAlphaBetaPruning(successor.getBoardConfiguration(), depth+1, getOppositePlayer(player), alpha, beta));
				if(bestMinMaxValue >= beta)
					return bestMinMaxValue;
				alpha = Math.max(alpha, bestMinMaxValue);
			}
			return bestMinMaxValue;
		}else{
			bestMinMaxValue = UPPER_BOUND;
			for(Successor successor : successors){
				bestMinMaxValue = Math.min(bestMinMaxValue, calculateMinMaxValueAlphaBetaPruning(successor.getBoardConfiguration(), depth+1, getOppositePlayer(player), alpha, beta));
				if(bestMinMaxValue <= alpha)
					return bestMinMaxValue;
				beta = Math.min(beta, bestMinMaxValue);
			}
			return bestMinMaxValue;
		}
	}

	@Override
	public String getName() {
		return "AB Pruning player";
	}
}
