package com.labyrinth.game;

public class Origin{

	private int position_x =  0, position_y = 0;
	private float unite_x = 1.0f, unite_y = 1.0f;
	
	public void setOriginPosition(int x, int y){
		this.position_x = x;
		this.position_y = y;
	}
	
	public void setOriginUnit(float x, float y){
		if(x < 0.2f) x = 0.2f;
		if(y < 0.2f) y = 0.2f;
		
		this.unite_x = x;
		this.unite_y = y;
	}
	
	public int getOX(){
		return this.position_x;
	}
	
	public int getOY(){
		return this.position_y;
	}

	public float getSizeX(){
		return this.unite_x;
	}
	
	public float getSizeY(){
		return this.unite_y;
	}

}
