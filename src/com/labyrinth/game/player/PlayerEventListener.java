package com.labyrinth.game.player;

public interface PlayerEventListener {

	public void playerClicksToPushWall(int id_player, int mouse_x, int mouse_y, int direction);
	
	public void playerClicksToMove(int id_player, int mouse_x, int mouse_y);

	public boolean playerWantsToPushWallAt(int id_player, int row, int line, int direction);
	
	public boolean playerWantsToMoveAt(int id_player, int row, int line);
	
	public void playerWantsToRotateAdditionalWall(int id_player, int mode);
	
	public void playerHasFinishedHisRound(int id_player);
	
	public void highLight(int mouse_x, int mouse_y, int mode);
	
	public void eventMousePressed(int id_player, int mouse_x, int mouse_y);

	public void eventMouseDrag(int id_player, int mouse_x, int mouse_y);
	
	public void eventMouseReleased(int id_player, int mouse_x, int mouse_y);
		
}
