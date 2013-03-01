package com.labyrinth.game.player.ai.strategy.moveplayer;

public class Coordinate {

	private int x = 0, y = 0;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setCoord(int x, int y){
		this.x = x;
		this.y = y;
	}
}
