package com.labyrinth.utils.graph;

public class Coordinate2D {

	private int coord_x = 0;
	private int coord_y = 0;
	
	public Coordinate2D(){
		this(0, 0);
	}
	
	public Coordinate2D(int coord_x, int coord_y){
		this.coord_x = coord_x;
		this.coord_y = coord_y;
	}
	
	public void setCoordinateXY(int coord_x, int coord_y){
		this.coord_x = coord_x;
		this.coord_y = coord_y;
	}
	
	public int getCoordinateX(){
		return this.coord_x;
	}
	
	public int getCoordinateY(){
		return this.coord_y;
	}
	
	public void setCoordinateX(int coord_x){
		this.coord_x  = coord_x;
	}
	
	public void setCoordinateY(int coord_y){
		this.coord_y  = coord_y;
	}
	
}
