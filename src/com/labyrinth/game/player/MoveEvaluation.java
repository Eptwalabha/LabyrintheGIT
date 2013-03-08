package com.labyrinth.game.player;

import com.labyrinth.utils.graph.Coordinate2D;

public abstract class MoveEvaluation {

	private Coordinate2D position;
	private int value = 0;
	
	public MoveEvaluation(int row, int line){
		this.position = new Coordinate2D(row, line);
	}
	
	public MoveEvaluation(Coordinate2D position){
		this.position = position;
	}

	public Coordinate2D getPosition(){
		return this.position;
	}
	
	public int getRowNumber(){
		return this.position.getCoordinateX();
	}
	
	public int getLineNumber(){
		return this.position.getCoordinateY();
	}

	public int getValue(){
		return this.value;
	}
	
	public void setValue(int value){
		this.value = value;
	}
	
	
	
}
