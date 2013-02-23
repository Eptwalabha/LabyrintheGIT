package com.labyrinth.game.player;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.labyrinth.game.Origin;
import com.labyrinth.game.maze.Wall;
import com.labyrinth.objective.Objective;
import com.labyrinth.utils.graph.GraphVertex;

public abstract class Player {

	private boolean playin;
	
	protected Wall position;
	private GraphVertex direction;
	protected Origin origin;
	
	private List<PlayerListener> listeners;
	
	private Objective player_objetive;
	private int player_score = 0;
	private String player_name;
	protected PlayerListener listener;
	
	private int player_id;
	
	protected int step;
	
	public Player(int player_id, String name, Origin origin, Wall start_position, PlayerListener listener){
		
		this.origin = origin;
		this.position = start_position;
		this.player_id = player_id;
		
		this.listeners = new ArrayList<PlayerListener>();
		this.listeners.add(listener);
		
		this.createNewObjective();
	}
	
	public Wall getPosition(){
		return this.position;
	}
	
	public void setNewDestination(GraphVertex path){
		if(this.direction != null){
			this.direction = path;
		}
	}
	
	public int getPlayerId(){
		return this.player_id;
	}
	
	protected void createNewObjective(){
		this.player_objetive = null;
	}
	
	public void itIsYourRound(boolean playing){
		this.playin = playing;
	}
	
	
	public abstract void render(GameContainer gc, Graphics g);
	public abstract void update(GameContainer gc);
	
	
}
