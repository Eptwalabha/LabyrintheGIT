package com.labyrinth.game.player;

import com.labyrinth.game.maze.Maze;
import com.labyrinth.utils.graph.Coordinate2D;

public class WallMovement extends MoveEvaluation{

	private int direction = Maze.INSERT_FROM_BOTTOM;
	
	public WallMovement(Coordinate2D position, int direction) {
		super(position);
		this.direction = direction;
	}
	
	public WallMovement(int row, int line, int direction) {
		super(row, line);
		this.direction = direction;
	}
	
	public int getDirection(){
		return this.direction;
	}
	
}
