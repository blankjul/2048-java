package de.julz.game.ai.evaluation;

import de.julz.game.model.GameState;

public class MultiEvaluation extends Evaluation {

	@Override
	public double getScore(GameState state) {
		return (double) heuristicScore(state.getScore(), state.getBoard().getEmptyFields().size(), calculateClusteringScore(state.getBoard().getArray()));
	}

	/**
	 * Estimates a heuristic score by taking into account the real score, the
	 * number of empty cells and the clustering score of the board.
	 * 
	 * @param actualScore
	 * @param numberOfEmptyCells
	 * @param clusteringScore
	 * @return
	 */
	private int heuristicScore(int actualScore, int numberOfEmptyCells, int clusteringScore) {
		int score = (int) (actualScore + Math.log(actualScore) * numberOfEmptyCells - clusteringScore);
		return Math.max(score, Math.min(actualScore, 1));
	}

	/**
	 * Calculates a heuristic variance-like score that measures how clustered
	 * the board is.
	 * 
	 * @param boardArray
	 * @return
	 */
	private int calculateClusteringScore(int[][] boardArray) {
		int clusteringScore = 0;

		int[] neighbors = { -1, 0, 1 };

		for (int i = 0; i < boardArray.length; ++i) {
			for (int j = 0; j < boardArray.length; ++j) {
				if (boardArray[i][j] == 0) {
					continue; // ignore empty cells
				}

				// clusteringScore-=boardArray[i][j];

				// for every pixel find the distance from each neightbors
				int numOfNeighbors = 0;
				int sum = 0;
				for (int k : neighbors) {
					int x = i + k;
					if (x < 0 || x >= boardArray.length) {
						continue;
					}
					for (int l : neighbors) {
						int y = j + l;
						if (y < 0 || y >= boardArray.length) {
							continue;
						}

						if (boardArray[x][y] > 0) {
							++numOfNeighbors;
							sum += Math.abs(boardArray[i][j] - boardArray[x][y]);
						}

					}
				}

				clusteringScore += sum / numOfNeighbors;
			}
		}

		return clusteringScore;
	}

}
