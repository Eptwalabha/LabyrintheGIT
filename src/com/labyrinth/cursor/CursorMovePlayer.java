package com.labyrinth.cursor;

import org.newdawn.slick.Graphics;

import com.labyrinth.game.maze.Maze;
import com.labyrinth.game.player.PlayerEventListener;
import com.labyrinth.gui.SpriteGUI;

public class CursorMovePlayer extends Cursor{

	public CursorMovePlayer(SpriteGUI textures){
		this.textures = textures;
	}
	
	@Override
	public void invoke(int mouse_x, int mouse_y) {
		this.resetCursor();
		this.setPositionX(mouse_x);
		this.setPositionY(mouse_y);
		this.setEnable(true);
	}

	@Override
	public void drag(int mouse_x, int mouse_y) {
		this.setPositionX(mouse_x);
		this.setPositionY(mouse_y);
	}

	@Override
	public void release(int id, int mouse_x, int mouse_y) {
		if(hasChoosed()){
			for(PlayerEventListener p : this.listeners){
				p.playerClicksToMove(id, mouse_x, mouse_y);
			}
		}
		this.setEnable(false);
	}

	@Override
	public void render(Graphics g) {
		if(this.isEnable()){
			this.highLight();
		}
	}
	
	@Override
	public boolean hasChoosed(){
		return true;
	}
	
	@Override
	public void highLight() {
		for(PlayerEventListener p : this.listeners){
			p.highLight(this.getPositionX(), this.getPositionY(), Maze.MODE_DOT);
		}
	}

	@Override
	public int getType() {
		return Cursor.MOVE_PLAYER;
	}

	@Override
	public void resetCursor() {

		this.setEnable(false);
		this.setPositionX(0);
		this.setPositionY(0);
		
	}
}
