package com.labyrinth.game.player.ai.strategy.moveplayer;

import java.util.List;

import com.labyrinth.game.maze.Maze;

public abstract class MovePlayerStrategy {

	public abstract List<Coordinate> getMoves(Maze m); 
}
