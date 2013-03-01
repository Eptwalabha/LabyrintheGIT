package com.labyrinth.cursor;

import org.newdawn.slick.Graphics;

import com.labyrinth.game.maze.Maze;
import com.labyrinth.game.player.PlayerEventListener;
import com.labyrinth.gui.SpriteGUI;

public class CursorMoveWall extends Cursor{

	private int new_pos_x, new_pos_y;
	
	private boolean top_enable = false;
	private boolean bottom_enable = false;
	private boolean left_enable = false;
	private boolean right_enable = false;

	public CursorMoveWall(SpriteGUI textures){
		this.textures = textures;
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
	
	@Override
	public void invoke(int mouse_x, int mouse_y) {
		this.resetCursor();
		this.setPositionX(mouse_x);
		this.setPositionY(mouse_y);
		this.new_pos_x = mouse_x;
		this.new_pos_y = mouse_y;
		this.setEnable(true);
	}

	@Override
	public void drag(int mouse_x, int mouse_y) {
		this.new_pos_x = mouse_x;
		this.new_pos_y = mouse_y;
	}

	@Override
	public void release(int id, int mouse_x, int mouse_y) {
		if(hasChoosed()){
			if(this.isArrowEnable(this.getDestination())){
				for(PlayerEventListener p : this.listeners){
					p.playerClicksToPushWall(id, this.getPositionX(), this.getPositionY(), this.getDestination());
				}
			}
		}
		
		this.resetCursor();
	}

	@Override
	public void render(Graphics g) {
		if(this.isEnable()){
			int tile = (int) (this.textures.getTileWidth() * 0.8);
			int choosen = -1;
			
			if(this.hasChoosed()){
				choosen = this.getDestination();
			}
			
			for(int i = 0; i < 4; i++){
				int ox = this.getPositionX();
				int oy = this.getPositionY();
				
				if(this.isArrowEnable(i)){
					if(i == choosen){
						tile = (int) (this.textures.getTileWidth() * 1.0);
						this.textures.getSpriteAt(1, 0, tile, 90f * i).draw(ox - tile / 2.0f, oy - tile * this.textures.getTileRatio());
						tile = (int) (this.textures.getTileWidth() * 0.8);
					}else{
						this.textures.getSpriteAt(0, 0, tile, 90f * i).draw(ox - tile / 2.0f, oy - tile * this.textures.getTileRatio());
					}
				}else{
					this.textures.getSpriteAt(2, 0, tile, 90f * i).draw(ox - tile / 2.0f, oy - tile * this.textures.getTileRatio());
				}
			}
			
			this.highLight();
		}

	}
	
	@Override
	public boolean hasChoosed(){
		
		int abs_x = Math.abs(this.new_pos_x - this.getPositionX());
		int abs_y = Math.abs(this.new_pos_y - this.getPositionY());

		int dist = (int) (Math.sqrt(abs_x * abs_x + abs_y * abs_y));
		
		if(dist >= 10){
			return true;
		}
		
		return false;
	}
	
	public int getDestination(){
		
		int x = this.new_pos_x - this.getPositionX();
		int y = this.new_pos_y - this.getPositionY();
		int abs_x = Math.abs(x);
		int abs_y = Math.abs(y);
		
		if(abs_x < abs_y){
			if(y > 0){
				return Maze.INSERT_FROM_TOP;
			}else{
				return Maze.INSERT_FROM_BOTTOM;
			}
		}else{
			if(x > 0){
				return Maze.INSERT_FROM_LEFT;
			}else{
				return Maze.INSERT_FROM_RIGHT;
			}
		}
	}

	@Override
	public void highLight() {
		for(PlayerEventListener p : this.listeners){
			p.highLight(this.getPositionX(), this.getPositionY(), Maze.MODE_CROSS);
		}
	}

	@Override
	public int getType() {
		return Cursor.MOVE_WALL;
	}

	@Override
	public void resetCursor() {
		
		this.top_enable = false;
		this.right_enable = false;
		this.bottom_enable = false;
		this.left_enable = false;
		this.setEnable(false);
		this.setPositionX(0);
		this.setPositionY(0);
		this.new_pos_x = 0;
		this.new_pos_y = 0;
		
	}

}
