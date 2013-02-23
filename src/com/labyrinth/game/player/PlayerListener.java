package com.labyrinth.game.player;

public interface PlayerListener {

	public void playerWantsToPushWall(int mouse_x, int mouse_y, int direction);
	
	public void playerWantsToMove(int mouse_x, int mouse_y);
	
	public void playerWantsToRotateAdditionalWall();
	
	public void playerHasFinishedHisRound();
	
}
