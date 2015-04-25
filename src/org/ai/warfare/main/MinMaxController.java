package org.ai.warfare.main;

import java.util.List;

import org.ai.warfare.types.BoardConfiguration;
import org.ai.warfare.types.Player;
import org.ai.warfare.types.Successor;

public class MinMaxController extends Controller{

	private static int MAX_DEPTH = 4;
	private static int LOWER_BOUND = -10000;
	private static int UPPER_BOUND = 10000;
	
	public Successor getOptimalSuccessor(BoardConfiguration boardConfiguration){
		List<Successor> successors = computeSuccessors(boardConfiguration, player);
		if(successors.size() == 0)
			return null;
		Successor optimalSuccessor = successors.get(0);
		if(player == Player.GREEN){
			int maxMinMaxValue = LOWER_BOUND;
			for(Successor successor : successors){
				int temp = calculateMinMaxValue(successor.getBoardConfiguration(), 1, getOppositePlayer(player));
				if(temp > maxMinMaxValue){
					maxMinMaxValue = temp;
					optimalSuccessor = successor;
				}
			}
		}else{
			int minMinMaxValue = UPPER_BOUND;
			for(Successor successor : successors){
				int temp = calculateMinMaxValue(successor.getBoardConfiguration(), 1, getOppositePlayer(player));
				if(temp < minMinMaxValue){
					minMinMaxValue = temp;
					optimalSuccessor = successor;
				}
			}
		}

		return optimalSuccessor;
	}
	
	public int calculateMinMaxValue(BoardConfiguration boardConfiguration, int depth, Player player){
		if(depth == MAX_DEPTH)
			return calculateUtilityFunction(boardConfiguration);
		if(isGameOver(boardConfiguration))
			return calculateUtilityFunction(boardConfiguration);
		List<Successor> successors = computeSuccessors(boardConfiguration, player);
		int bestMinMaxValue;
		if(player == Player.GREEN){
			bestMinMaxValue = LOWER_BOUND;
			for(Successor successor : successors){
				int temp = calculateMinMaxValue(successor.getBoardConfiguration(), depth+1, getOppositePlayer(player));
				if(temp > bestMinMaxValue)
					bestMinMaxValue = temp;
			}
		}else{
			bestMinMaxValue = UPPER_BOUND;
			for(Successor successor : successors){
				int temp = calculateMinMaxValue(successor.getBoardConfiguration(), depth+1, getOppositePlayer(player));
				if(temp < bestMinMaxValue)
					bestMinMaxValue = temp;
			}
		}
		return bestMinMaxValue;
	}

	@Override
	public String getName() {
		return "Min Max player";
	}
}
