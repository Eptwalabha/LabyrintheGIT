package com.labyrinth.game.player.ai.strategy;

import java.util.List;

import com.labyrinth.game.maze.Maze;
import com.labyrinth.game.maze.Wall;
import com.labyrinth.game.player.PlayerMovement;
import com.labyrinth.game.player.WallMovement;
import com.labyrinth.objective.Objective;

public class AIEasyStrategy extends AIStrategy{


	private Maze maze;
	private Wall player_position;
	private Objective objective;

	private PlayerMovement[] player_moves;
	private WallMovement[] wall_moves;
	
	public AIEasyStrategy(
					Maze maze,
					Wall player_position,
					Objective objective){

		this.maze = maze;
		this.player_position = player_position;
		this.objective = objective;
	}

	@Override
	public void processSolutions() {
		System.out.println("traitement des solutions en mode:\neasy");
	}

	@Override
	public PlayerMovement getBestPlayerMove(int position) {
		if(position < this.player_moves.length){
			return this.player_moves[position];
		}
		
		return null;
	}

	@Override
	public WallMovement getBestWallMove(int position) {
		if(position < this.wall_moves.length){
			return this.wall_moves[position];
		}
		return null;
	}

	@Override
	public String getInfo() {
		return "traitement des infos en mode:\nEasy";
	}

}
