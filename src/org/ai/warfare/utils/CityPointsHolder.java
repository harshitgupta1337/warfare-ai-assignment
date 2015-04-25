package org.ai.warfare.utils;

public class CityPointsHolder {

	private static CityPointsHolder instance = null;
	
	public static CityPointsHolder getInstance(){
		if(instance != null)
			return instance;
		instance = new CityPointsHolder();
		return instance;
	}
	
	private int[][] points;
	
	private CityPointsHolder(){
		int board[][] = new int[Utils.BOARD_SIZE][Utils.BOARD_SIZE];
		for(int i=0;i<Utils.BOARD_SIZE;i++){
			for(int j=0;j<Utils.BOARD_SIZE;j++){
				board[i][j] = (int) (Math.random()*99 + 1);
			}
		}
		points = board;
	}
	public int[][] getPoints() {
		return points;
	}
	public void setPoints(int[][] points) {
		this.points = points;
	}
	
	public void refreshPoints(){
		instance.points = assignRandomPoints();
	}
	
	private int[][] assignRandomPoints(){
		int board[][] = new int[Utils.BOARD_SIZE][Utils.BOARD_SIZE];
		for(int i=0;i<Utils.BOARD_SIZE;i++){
			for(int j=0;j<Utils.BOARD_SIZE;j++){
				board[i][j] = (int) (Math.random()*99 + 1);
			}
		}
		return board;
	}
	
	public void assignKerenPoints(){
		points = new int[][]{
				{1,	1,	1,	1,	1,	1},
				{1,	1,	1,	1,	1,	1},
				{1,	1,	1,	1,	1,	1},
				{1,	1,	1,	1,	1,	1},
				{1,	1,	1,	1,	1,	1},
				{1,	1,	1,	1,	1,	1}
				};
	}
	public void assignNarvikPoints(){
		points = new int[][]{
				{99,	1,	99,	1,	99,	1},
				{1,		99,	1,	99,	1,	99},
				{99,	1,	99,	1,	99,	1},
				{1,		99,	1,	99,	1,	99},
				{99,	1,	99,	1,	99,	1},
				{1,		99,	1,	99,	1,	99}};
	}
	public void assignSevastopolPoints(){
		points = new int[][]{
				{1,		1,	1,	1,	1,	1},
				{2,		2,	2,	2,	2,	2},
				{4,		4,	4,	4,	4,	4},
				{8,		8,	8,	8,	8,	8},
				{16,	16,	16,	16,	16,	16},
				{32,	32,	32,	32,	32,	32}
		};
		
	}
	public void assignSmolenskPoints(){
		points = new int[][]{
			{66,	76,	28,	66,	11,	9},
			{31,	39,	50,	8,	33,	14},
			{80,	76,	39,	59,	2,	48},
			{50,	73,	43,	3,	13,	3},
			{99,	45,	72,	87,	49,	4},
			{80, 	63,	92,	28,	61,	53}
		};
	}
	public void assignWesterplattePoints(){
		points = new int[][]{
				{1,	1,	1,	1,	1,	1},
				{1,	3,	4,	4,	3,	1},
				{1,	4,	2,	2,	4,	1},
				{1,	4,	2,	2,	4,	1},
				{1,	3,	4,	4,	3,	1},
				{1,	1,	1,	1,	1,	1}
		};
}
}
