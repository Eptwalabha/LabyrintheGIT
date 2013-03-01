package com.labyrinth.cursor;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;

import com.labyrinth.game.player.PlayerEventListener;
import com.labyrinth.gui.SpriteGUI;

public abstract class Cursor {
	
	
	public final static int MOVE_WALL = 0;
	public final static int MOVE_PLAYER = 1;

	protected List<PlayerEventListener> listeners = new ArrayList<PlayerEventListener>();
	protected SpriteGUI textures;
	
	private int old_x, old_y;
	private boolean enable = false;
		
	public boolean isEnable(){
		return this.enable;
	}
	
	public void setEnable(boolean enable){
		this.enable = enable;
	}
	
	public int getPositionX(){
		return this.old_x;
	}
	
	public int getPositionY(){
		return this.old_y;
	}
	
	public void setPositionX(int pos_x){
		this.old_x = pos_x;
	}
	
	public void setPositionY(int pos_y){
		this.old_y = pos_y;
	}
	
	public void addPlayerListener(PlayerEventListener p){
		this.listeners.add(p);
	}
	
	public abstract int getType();
	
	public abstract void resetCursor();

	public abstract void invoke(int mouse_x, int mouse_y);
	
	public abstract void drag(int mouse_x, int mouse_y);
	
	public abstract void release(int id, int mouse_x, int mouse_y);
	
	public abstract boolean hasChoosed();
	
	public abstract void render(Graphics g);
	
	public abstract void highLight();
	
}
