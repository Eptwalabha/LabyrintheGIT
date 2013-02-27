package com.labyrinth.menu.wheel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import com.labyrinth.game.maze.Maze;
import com.labyrinth.gui.SpriteGUI;

public class MenuWheel {
	
	private int old_pos_x, old_pos_y;
	private int pos_x, pos_y;

	private boolean visible = false;
	
	private SpriteGUI textures;
	
	public MenuWheel(SpriteGUI textures){
		this.textures = textures;
	}
	
	public void setClickOrigin(int x, int y){
		
		this.visible = true;
		this.old_pos_x = x;
		this.old_pos_y = y;
	}
	
	public void render(Graphics arg2){
		int tile = (int) (this.textures.getTileWidth() * 0.8);
		int choosen = -1;
		
		if(hasChoosedADirection()){
			choosen = this.getDestination();
		}
		
		for(int i = 0; i < 4; i++){
			if(i == choosen){
				tile = (int) (this.textures.getTileWidth() * 1.0);
				this.textures.getSpriteAt(1, 0, tile, 90f * i).draw(this.old_pos_x - tile / 2.0f, this.old_pos_y - tile * this.textures.getTileRatio());
				tile = (int) (this.textures.getTileWidth() * 0.8);
			}else{
				this.textures.getSpriteAt(0, 0, tile, 90f * i).draw(this.old_pos_x - tile / 2.0f, this.old_pos_y - tile * this.textures.getTileRatio());
			}
		}
	}
	
	public void update(GameContainer arg0){
		
		if(this.visible){
			Input i = arg0.getInput();
			this.pos_x = i.getMouseX();
			this.pos_y = i.getMouseY();
		}
		
	}
	
	public void selectIsDone(){
		this.visible = false;
	}
	
	public boolean isVisible(){
		return this.visible;
	}
	
	public boolean hasChoosedADirection(){
		
		int abs_x = Math.abs(this.pos_x - this.old_pos_x);
		int abs_y = Math.abs(this.pos_y - this.old_pos_y);

		int dist = (int) (Math.sqrt(abs_x * abs_x + abs_y * abs_y));
		
		if(dist >= 10){
			return true;
		}
		
		return false;
	}
	
	public int getPositionX(){
		return this.old_pos_x;
	}
	
	public int getPositionY(){
		return this.old_pos_y;
	}
	
	public int getDestination(){
		
		int abs_x = Math.abs(this.pos_x - this.old_pos_x);
		int abs_y = Math.abs(this.pos_y - this.old_pos_y);
		
		if(abs_x < abs_y){
			if(this.pos_y - this.old_pos_y > 0){
				return Maze.INSERT_FROM_TOP;
			}else{
				return Maze.INSERT_FROM_BOTTOM;
			}
		}else{
			if(this.pos_x - this.old_pos_x > 0){
				return Maze.INSERT_FROM_LEFT;
			}else{
				return Maze.INSERT_FROM_RIGHT;
			}
		}
	}

}
