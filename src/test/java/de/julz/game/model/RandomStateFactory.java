package de.julz.game.model;

public class RandomStateFactory {
	
	public final static int NUM = 100;

	public static void main(String[] args) {
		
		for (int i = 0; i < NUM; i++) {
			Board b = new Board(randomArray(0.4, 10));
			
			
			String code = String.format("list.add(new Object[] {Board.fromJSON(\"%s\")});", Board.toJSON(b));
			System.out.println(code);
		}
		

	}
	
	
	public static int[][] randomArray(double emptyProb, int max) {
		
		int[][] result = new int[4][4];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result.length; j++) {
				if (emptyProb != -1 && Math.random() < emptyProb) {
					result[i][j] = 0;
				} else {
					result[i][j] = (int) (Math.random() * max);
				}
			}
		}
		return result;
		
	}

}
