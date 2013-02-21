package com.labyrinth.game.player;

import org.newdawn.slick.GameContainer;

import com.labyrinth.utils.graph.GraphVertex;

import com.labyrinth.game.maze.Wall;

public class HumanPlayer extends Player {

	public HumanPlayer(Wall position, String name, PlayerListener listener, int player_id) {
		super(position, name, listener, player_id);
	}


	@Override
	public void update(GameContainer gc) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void moveWall() {
		// TODO Auto-generated method stub
		this.listener.moveWall(0, 0);
		this.step++;
	}

	@Override
	protected void movePlayer() {
		// TODO Auto-generated method stub
		GraphVertex path = this.position.getShortestPathRecursive(this.position);
		if(path != null);
	}

	@Override
	protected void endRound() {
		// TODO Auto-generated method stub
		this.listener.nextPlayer();
	}


}
