package com.labyrinth.game.player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.labyrinth.game.maze.Wall;

public abstract class Player {

	protected Wall position;
	protected Objective player_objetive;
	protected int player_score = 0;
	protected String player_name;
	protected PlayerListener listener;
	
	private int player_id;
	
	protected int step;
	
	public Player(Wall position, String name, PlayerListener listener, int player_id){
		
		this.position = position;
		this.player_id = player_id;
		this.createNewObjective();
	}
	
	protected void createNewObjective(){
		this.player_objetive = null;
	}
	
	public void newRoundPlayer(){
		
	}
	
	public void render(GameContainer gc, Graphics g){
		
	}
	
	public abstract void update(GameContainer gc);
	
	protected abstract void moveWall();
	protected abstract void movePlayer();
	protected abstract void endRound();
	
	
}
