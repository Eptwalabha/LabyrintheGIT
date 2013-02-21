package com.labyrinth.game.player;

import com.labyrinth.utils.graph.GraphVertex;

public interface PlayerListener {

	
	public boolean moveWall(int line, int column);
	
	public boolean movePlayer(GraphVertex vertex);
	
	public boolean nextPlayer();
	
	
}
