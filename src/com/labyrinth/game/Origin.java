package com.labyrinth.game;

public class Origin{

	private int position_x =  0, position_y = 0;
	private int width;
	
	public void setOriginPosition(int x, int y){
		this.position_x = x;
		this.position_y = y;
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
