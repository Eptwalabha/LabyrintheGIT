package com.labyrinth.game.player.ai.strategy;

import com.labyrinth.game.player.PlayerMovement;
import com.labyrinth.game.player.WallMovement;

public abstract class AIStrategy {
	
	public abstract void processSolutions();
	
	public abstract PlayerMovement getBestPlayerMove(int position);
	public abstract WallMovement getBestWallMove(int position);
	
	public abstract String  getInfo();
}
