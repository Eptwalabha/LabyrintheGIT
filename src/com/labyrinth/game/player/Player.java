package com.labyrinth.game.player;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.labyrinth.game.Origin;
import com.labyrinth.game.maze.Wall;
import com.labyrinth.gui.SpriteGUI;
import com.labyrinth.objective.Objective;
import com.labyrinth.utils.graph.GraphVertex;

public abstract class Player {
	
	protected Wall position;
	private boolean playing = false;
	
	private GraphVertex path;
	
	protected SpriteGUI textures;
	protected Origin origin;
	
	private List<PlayerListener> listeners;
	
	private Objective player_objetive;
	private int player_score = 0;
	private String player_name;
	protected PlayerListener listener;
	
	private int player_id;
	
	protected int step;
	
	public Player(int player_id, String name, Origin origin, SpriteGUI textures, Wall start_position, PlayerListener listener){
		
		this.textures = textures;
		this.origin = origin;
		this.position = start_position;
		this.player_id = player_id;
		this.path = null;
		
		this.listeners = new ArrayList<PlayerListener>();
		this.listeners.add(listener);
	}
	
	public Wall getPosition(){
		return this.position;
	}
	
	public void setNewDestination(GraphVertex path){
		if(path != null){
			this.path = path;
		}
	}
	
	public boolean isMoving(){
		return (path != null);
	}
	
	public void move(){
		this.position = (Wall) path.getVerticeSon();
		if(path.getVerticeBrother() != null){
			path = path.getVerticeBrother();
		}else{
			path = null;
		}
	}
	
	public GraphVertex getPath() {
		return this.path;
	}
	
	public int getPlayerId(){
		return this.player_id;
	}
	
	public void beginOfRound(){
		this.playing = true;
		this.player_objetive.active(true);
	}
	
	public void endOfRound(){
		this.playing = false;
		this.player_objetive.active(false);
	}
	
	public boolean isPlaying(){
		return this.playing;
	}
	
	public void setPlayerObjective(Objective o){
		this.player_objetive = o;
	}
	
	public String getPlayerName(){
		return this.player_name;
	}
	
	public int getPlayerScore(){
		return this.player_score;
	}
	
	public void render(GameContainer gc, Graphics g) {

		this.player_objetive.update();
				
		int x = this.position.getPositionX();
		int y = this.position.getPositionY();
		
		this.textures.getSpriteAt(0, 0, this.origin.getWidth()).draw(x, y);
	}
	
	public abstract void update(GameContainer gc);
	
	public void playerWantsToMove(int mouse_x, int mouse_y){
		for(PlayerListener p : listeners){
			p.playerWantsToMove(mouse_x, mouse_y);
		}
	}
	
	public void playerWantsToPushWall(int mouse_x, int mouse_y, int direction){
		for(PlayerListener p : listeners){
			p.playerWantsToPushWall(mouse_x, mouse_y, direction);
		}
	}
	
	public void playerHasFinishedHisRound(){
		for(PlayerListener p : listeners){
			p.playerHasFinishedHisRound();
		}
		
	}
}
