package com.labyrinth.game.player.ai.strategy.moveplayer;

import java.util.List;

import com.labyrinth.game.maze.Maze;

public abstract class MovePlayerDecorator extends MovePlayerStrategy{

	protected MovePlayerStrategy decorate_move;
	
	public MovePlayerDecorator(MovePlayerStrategy move){
		this.decorate_move = move;
	}
	
	public List<Coordinate> getMoves(Maze m){
		return decorate_move.getMoves(m); 
	}
}
