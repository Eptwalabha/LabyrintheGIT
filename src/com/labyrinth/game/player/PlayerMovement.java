package com.labyrinth.game.player;

import com.labyrinth.utils.graph.Coordinate2D;

public class PlayerMovement extends MoveEvaluation{

	public PlayerMovement(Coordinate2D position) {
		super(position);
	}
	
	public PlayerMovement(int row, int line) {
		super(row, line);
	}

}
