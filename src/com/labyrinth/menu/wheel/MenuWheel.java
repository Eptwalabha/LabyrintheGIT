package com.labyrinth.menu.wheel;

import org.newdawn.slick.Graphics;

import com.labyrinth.game.maze.Maze;
import com.labyrinth.gui.SpriteGUI;

public class MenuWheel {
	
	private int old_pos_x, old_pos_y;
	private int pos_x, pos_y;

	private boolean visible = false;
	private boolean top_enable = false;
	private boolean bottom_enable = false;
	private boolean left_enable = false;
	private boolean right_enable = false;
	
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
			if(this.isArrowEnable(i)){
				if(i == choosen){
					tile = (int) (this.textures.getTileWidth() * 1.0);
					this.textures.getSpriteAt(1, 0, tile, 90f * i).draw(this.old_pos_x - tile / 2.0f, this.old_pos_y - tile * this.textures.getTileRatio());
					tile = (int) (this.textures.getTileWidth() * 0.8);
				}else{
					this.textures.getSpriteAt(0, 0, tile, 90f * i).draw(this.old_pos_x - tile / 2.0f, this.old_pos_y - tile * this.textures.getTileRatio());
				}
			}else{
				this.textures.getSpriteAt(2, 0, tile, 90f * i).draw(this.old_pos_x - tile / 2.0f, this.old_pos_y - tile * this.textures.getTileRatio());
			}
		}
	}
	
	public void setLineEnable(boolean enable){
		this.left_enable = enable;
		this.right_enable = enable;
	}
	
	public void setRowEnable(boolean enable){
		this.top_enable = enable;
		this.bottom_enable = enable;
	}

	public void setArrowEnable(int arrow, boolean enable){
	
		switch (arrow) {
		case 0:
			this.top_enable = enable;
			break;
		case 1:
			this.right_enable = enable;
			break;
		case 2:
			this.bottom_enable = enable;
			break;
		case 3:
			this.left_enable = enable;
			break;
		default:
			break;
		}
	}
	
	public boolean isArrowEnable(int arrow){

		switch (arrow) {
		case 0:
			return this.top_enable;
		case 1:
			return this.right_enable;
		case 2:
			return this.bottom_enable;
		case 3:
			return this.left_enable;
		default:
			return false;
		}
	}
	
	public void update(int mouse_x, int mouse_y){
		
		this.pos_x = mouse_x;
		this.pos_y = mouse_y;
		
	}
	
	public void selectIsDone(){
		this.visible = false;
	}
	
	public boolean isVisible(){
		return this.visible;
	}

	public void setVisible(boolean visible){
		this.visible = visible;
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
