package com.labyrinth.game.player;

public interface PlayerEventListener {

	public void playerClicksToPushWall(int id_player, int mouse_x, int mouse_y, int direction);
	
	public void playerClicksToMove(int id_player, int mouse_x, int mouse_y);

	public boolean playerWantsToPushWall(int id_player, WallMovement wall_movement);
	
	public boolean playerWantsToMove(int id_player, PlayerMovement player_movement);
	
	public void playerWantsToRotateAdditionalWall(int id_player, int mode);
	
	public void playerHasFinishedHisRound(int id_player);
	
	public void highLight(int mouse_x, int mouse_y, int mode);
	
	public void eventMousePressed(int id_player, int mouse_x, int mouse_y);

	public void eventMouseDrag(int id_player, int mouse_x, int mouse_y);
	
	public void eventMouseReleased(int id_player, int mouse_x, int mouse_y);
		
}
