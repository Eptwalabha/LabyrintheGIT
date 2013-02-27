package com.labyrinth.objective;

import com.labyrinth.game.maze.Wall;
import com.labyrinth.gui.SpriteGUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Objective {

	private Wall position;
	private SpriteGUI textures;
	private int scale;
	private boolean focus;
	
	public Objective(Wall position, SpriteGUI textures) {
		
		this.position = position;
		this.textures = textures;
		
	}
	
	public void active(boolean focus){
		this.focus = focus;
		if(!focus) this.scale = 0;
	}

	public void render(GameContainer gc, Graphics g) {
				
		int x = this.position.getPositionX();
		int y = this.position.getPositionY();
		int w = this.position.getWidth();
		
		this.textures.getSpriteAt(0, 0, (int) (w * this.getScale())).draw(x, y);
		
	}

	private float getScale(){
		
		double a = (this.scale * Math.PI) / 180.0;
		float result = (float) (Math.sin(a) * 0.4f);
		return (0.8f + result);
	}
	
	public void update() {
		// TODO Auto-generated method stub
		if(this.focus){
			this.scale = (this.scale + 1) % 180;
		}
	}

	public Wall getPosition(){
		return this.position;
	}
}
