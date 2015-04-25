package org.ai.warfare.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ai.warfare.types.Successor;

public class TestController {

	private static int LOWER_BOUND = -1000;
	private static int UPPER_BOUND = 1000;
	public static int MAX_DEPTH = 2;
	public static int count=0;
	public static int genCount = 0;
	public static Map<Integer, Integer> utilityValues = new HashMap<Integer, Integer>(){{
		put(0, 3);
		put(1, 12);
		put(2, 8);
		put(3, 6);
		put(4, 4);
		put(5, 10);
		put(6, 2);
		put(7, 5);
		put(8, 14);
	}};
	public int computeUtilityFunction(TestSuccessor testSuccessor){
		
		if(count > 8)
			return 0;
		int ret = utilityValues.get(count);
		System.out.println("Playoff : "+ret);
		count++;
		return ret;
	}
	public List<TestSuccessor> computeSuccessors(TestSuccessor parent){
		List<TestSuccessor> succ = new ArrayList<TestSuccessor>(){{
			add(new TestSuccessor());
			add(new TestSuccessor());
			add(new TestSuccessor());
		}};
		return succ;
	}
	public int calculateMinMaxValueAlphaBetaPruning(TestSuccessor parent, int depth, boolean maxNotMin, int alpha, int beta){
		if(depth == MAX_DEPTH)
			return computeUtilityFunction(parent);
		List<TestSuccessor> successors = computeSuccessors(parent);
		int bestMinMaxValue;
		if(maxNotMin){
			bestMinMaxValue = LOWER_BOUND;
			for(TestSuccessor successor : successors){
				bestMinMaxValue = Math.max(bestMinMaxValue, calculateMinMaxValueAlphaBetaPruning(successor, depth+1, !maxNotMin, alpha, beta));
				if(bestMinMaxValue >= beta)
					return bestMinMaxValue;
				alpha = Math.max(alpha, bestMinMaxValue);
			}
			return bestMinMaxValue;
		}else{
			bestMinMaxValue = UPPER_BOUND;
			for(TestSuccessor successor : successors){
				bestMinMaxValue = Math.min(bestMinMaxValue, calculateMinMaxValueAlphaBetaPruning(successor, depth+1, !maxNotMin, alpha, beta));
				if(bestMinMaxValue <= alpha){
					System.out.println("YES");
					return bestMinMaxValue;
				}
				beta = Math.min(beta, bestMinMaxValue);
			}
			return bestMinMaxValue;
		}
	}
	public int getOptimalSuccessorAplhaBetaPruning(){
		TestSuccessor root = new TestSuccessor();
		Successor optimalSuccessor = null;
		List<TestSuccessor> successors = computeSuccessors(root);
		int alpha = LOWER_BOUND, beta = UPPER_BOUND;
		int maxMinMaxValue = LOWER_BOUND;
		for(TestSuccessor successor : successors){
			int temp = calculateMinMaxValueAlphaBetaPruning(successor, 1, false, alpha, beta);
			if(temp > alpha)
				alpha = temp;
			if(temp > maxMinMaxValue){
				optimalSuccessor = successor;
				maxMinMaxValue = temp;
			}
		}
		return maxMinMaxValue;
	}

	public int getOptimalSuccessorMinMax(){
		TestSuccessor optimalSuccessor = null;
		List<TestSuccessor> successors = computeSuccessors(optimalSuccessor);
		int maxMinMaxValue = LOWER_BOUND;
		for(TestSuccessor successor : successors){
			int temp = calculateMinMaxValue(successor, 1, false);
			if(temp > maxMinMaxValue){
				maxMinMaxValue = temp;
				optimalSuccessor = successor;
			}
		}
		return maxMinMaxValue;
	}
	
	public int calculateMinMaxValue(TestSuccessor parent, int depth, boolean maxNotMin){
		if(depth == MAX_DEPTH)
			return computeUtilityFunction(parent);
		List<TestSuccessor> successors = computeSuccessors(parent);
		int bestMinMaxValue;
		if(maxNotMin){
			bestMinMaxValue = LOWER_BOUND;
			for(TestSuccessor successor : successors){
				int temp = calculateMinMaxValue(successor, depth+1, !maxNotMin);
				if(temp > bestMinMaxValue)
					bestMinMaxValue = temp;
			}
		}else{
			bestMinMaxValue = UPPER_BOUND;
			for(TestSuccessor successor : successors){
				int temp = calculateMinMaxValue(successor, depth+1, !maxNotMin);
				if(temp < bestMinMaxValue)
					bestMinMaxValue = temp;
			}
		}
		return bestMinMaxValue;
	}
	public static void main(String args[]){
		System.out.println(new TestController().getOptimalSuccessorAplhaBetaPruning());
	}
}
