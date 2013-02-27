package com.labyrinth.game;

public class Origin{

	private int position_x =  0, position_y = 0;
	private int width;
	
	private int min_x, max_x, min_y, max_y;
	
	public void setOriginPosition(int x, int y){
		
		if(x < min_x) x = min_x;
		if(y < min_y) y = min_y;
		if(x > max_x) x = max_x;
		if(y > max_y) y = max_y;
		
		this.position_x = x;
		this.position_y = y;
	}
	
	public void setBounds(int origine_x, int origine_y, int frame_width, int frame_height, int maze_width, int maze_height){
		
		this.max_x = this.width + origine_x;
		this.max_y = this.width + origine_y;
		this.min_x = frame_width - this.width - maze_width + origine_x;
		this.min_y = frame_height - this.width - maze_height + origine_y;
		
		if(this.max_x < this.min_x){
			int moy_x = (this.max_x + this.min_x) / 2;
			this.max_x = moy_x;
			this.min_x = moy_x;
		}
		
		if(this.max_y < this.min_y){
			int moy_y = (this.max_y + this.min_y) / 2;
			this.max_y = moy_y;
			this.min_y = moy_y;
		}
		
		this.setOriginPosition(this.position_x, this.position_y);
		
	}
	
	public void setWidth(int width){
		
		if(width < 30) width = 30;
		if(width > 100) width = 100;
		this.width = width;
		
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getOX(){
		return this.position_x;
	}
	
	public int getOY(){
		return this.position_y;
	}
	
}
