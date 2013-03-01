package com.labyrinth.objective;

import com.labyrinth.game.maze.Wall;
import com.labyrinth.game.player.Player;
import com.labyrinth.gui.SpriteGUI;

import org.newdawn.slick.Graphics;

public class Objective {

	private Wall position;
	private SpriteGUI textures;
	private int scale;
	private boolean focus;
	private boolean enable = true;
	private Player player;
	
	public Objective(Wall position, SpriteGUI textures, Player player) {
		
		this.position = position;
		this.textures = textures;
		this.player = player;
	}
	
	public void active(boolean focus){
		this.focus = focus;
		if(!focus) this.scale = 0;
	}
	
	public void setEnable(boolean enable){
		this.enable = enable;
	}

	public void render(Graphics g) {
		
		if(this.enable){
			int x = this.position.getPositionX();
			int y = this.position.getPositionY();
			int w = this.position.getWidth();
			
			this.textures.getSpriteAt(0, this.player.getPlayerPosition(), (int) (w * this.getScale())).draw(x, y);
		}
	}
	
	public void update() {
		if(this.focus && this.enable){
			this.scale = (this.scale + 1) % 180;
		}
	}

	public Wall getPosition(){
		return this.position;
	}

	private float getScale(){
		
		double a = (this.scale * Math.PI) / 180.0;
		float result = (float) (Math.sin(a) * 0.4f);
		return (0.8f + result);
	}
}
