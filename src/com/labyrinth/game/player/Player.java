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
	
	public final static int HUMAN = 0;
	public final static int AI = 1;
	public final static int ONLINE = 2;
	
	
	protected SpriteGUI textures;
	protected List<PlayerEventListener> listeners;
	
	private Wall position;
	private GraphVertex path;
	private Origin origin;
	private List<Objective> objetive;
	private String player_name;
	private int player_score = 0;
	private int player_position = 0;
	private int player_id;
	private int step;
	private long time_next_move = 0;
	
	public Player(int player_id, String name, Origin origin, SpriteGUI textures, Wall start_position, PlayerEventListener listener){
		
		this.objetive = new ArrayList<Objective>();
		this.textures = textures;
		this.origin = origin;
		this.position = start_position;
		this.player_id = player_id;
		this.player_name = name;
		this.path = null;
		
		this.listeners = new ArrayList<PlayerEventListener>();
		this.listeners.add(listener);
	}
	
	public void addObjective(Objective objective){
		this.objetive.add(objective);
	}
	
	public void setPlayerPosition(int position){
		this.player_position = position;
	}
	
	public int getPlayerPosition(){
		return this.player_position;
	}
	
	public Wall getPosition(){
		return this.position;
	}
	
	public void setPosition(Wall position){
		this.position = position;
	}
	
	
	public Objective getPlayerObjective(){
		if(this.objetive.size() > 0){
			return this.objetive.get(0);
		}
		return null;
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
		long t = System.currentTimeMillis();
		if(t >= this.time_next_move){
			this.position = (Wall) path.getVerticeSon();
			if(path.getVerticeBrother() != null){
				path = path.getVerticeBrother();
				this.time_next_move = t + 200;
			}else{
				path = null;
			}
		}
	}
	
	public GraphVertex getPath() {
		return this.path;
	}
	
	public int getPlayerId(){
		return this.player_id;
	}
	
	public void setStep(int step){
		this.step = step;
	}
	
	public int getStep(){
		return this.step;
	}
	
	public void nextObjective(){
		
		this.incrementPlayerScore();
		
		if(this.objetive.size() > 0){
			
			Objective o = this.objetive.get(0);
			o.setEnable(false);
			
			this.objetive.remove(0);
		}
	}
	
	public String getPlayerName(){
		return this.player_name;
	}
	
	public int getPlayerScore(){
		return this.player_score;
	}
	
	private void incrementPlayerScore(){
		this.player_score++;
	}
	
	public void render(GameContainer gc, Graphics g) {

		if(this.objetive.size() > 0){
			this.objetive.get(0).update();
		}
		
		int x = this.position.getPositionX();
		int y = this.position.getPositionY();
		
		this.textures.getSpriteAt(0, this.player_position, this.origin.getWidth()).draw(x, y);
	}
	
	public abstract int getTypeOfPlayer();
	
	public abstract void update(GameContainer gc);
	
	public void playerHasFinishedHisRound(){
		for(PlayerEventListener p :this.listeners){
			p.playerHasFinishedHisRound(this.player_id);
		}
	}

	public boolean completeAllObjectTives() {
		return (this.objetive.size() == 0);
	}
}
