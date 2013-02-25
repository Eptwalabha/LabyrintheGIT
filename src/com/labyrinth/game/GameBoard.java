package com.labyrinth.game;

import com.labyrinth.game.maze.Maze;
import com.labyrinth.game.maze.Wall;
import com.labyrinth.game.player.Player;
import com.labyrinth.game.player.PlayerListener;
import com.labyrinth.game.player.ai.AIPlayer;
import com.labyrinth.game.player.human.HumanPlayer;
import com.labyrinth.gui.SpriteGUI;

import java.util.ArrayList;
import java.util.List;

import com.labyrinth.objective.Objective;
import com.labyrinth.utils.graph.GraphVertex;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameBoard extends BasicGameState implements PlayerListener{

	private boolean bouton_droit_souris;
	private SpriteGUI objective_textures;
	
	private Maze labyrinth;

	private Origin origin;
	private List<Player> players = new ArrayList<Player>();
	private List<Objective> objectives = new ArrayList<Objective>();
	
	private int joueur_en_cours = 0;
	
	private Input input;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		this.origin = new Origin();
		SpriteGUI wall_texture = new SpriteGUI("images/murs/wall.png", 12, 4);
		SpriteGUI player_texture = new SpriteGUI("images/items/player.png", 1, 1);
		this.objective_textures = new SpriteGUI("images/items/objective.png", 1, 1);
		this.origin.setWidth(wall_texture.getTileWidth());
		this.players = new ArrayList<Player>();		
		
		this.labyrinth = new Maze(21, 21, this.origin, wall_texture);
		
		Player p1 = new HumanPlayer(0, "test", this.origin, player_texture, this.labyrinth.getWall(0, 0), this);
		Player p2 = new HumanPlayer(0, "test2", this.origin, player_texture, this.labyrinth.getWall(this.labyrinth.getNumberOfCollumn() - 1, this.labyrinth.getNumberOfLine() - 1), this);
		Player p3 = new AIPlayer(0, this.origin, player_texture, this.labyrinth.getWall(this.labyrinth.getNumberOfCollumn() - 1, 0), this, this.labyrinth);

		this.players.add(p1);
		this.players.add(p2);
		this.players.add(p3);
		
		this.joueur_en_cours = 0;
		
		this.input = arg0.getInput();
		
		int mx = this.labyrinth.getNumberOfCollumn();
		int my = this.labyrinth.getNumberOfLine();
		
		for(int i = 0; i < this.players.size() * 5; i++){
			Wall w;
			boolean wall_already_used;
			
			do{
				wall_already_used = false;
				w = this.labyrinth.getWall((int)(Math.random() * mx), (int)(Math.random() * my));
				for(int j = 0; j < this.objectives.size(); j++){
					if(w == this.objectives.get(j).getPosition()) wall_already_used = true;
				}
				
			}while(wall_already_used);
			this.objectives.add(new Objective(this.origin, w, objective_textures));
		}
		
		p1.setPlayerObjective(this.objectives.get(0));
		p2.setPlayerObjective(this.objectives.get(1));
		p3.setPlayerObjective(this.objectives.get(2));

		this.players.get(this.joueur_en_cours).beginOfRound();

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		this.labyrinth.render(arg0, arg2);
		
		for(Objective o : objectives){
			o.render(arg0, arg2);
		}

		for(Player j : players){
			j.render(arg0, arg2);
		}
		
		this.labyrinth.getAdditionalWall().render(0, 0, 150);
		

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		this.labyrinth.update(arg0);
		Input in = arg0.getInput();
		
		if(in.isKeyDown(Input.KEY_LEFT)){
			this.origin.setOriginPosition(this.origin.getOX() - 5, this.origin.getOY());
		}
		if(in.isKeyDown(Input.KEY_RIGHT)){
			this.origin.setOriginPosition(this.origin.getOX() + 5, this.origin.getOY());
		}
		if(in.isKeyDown(Input.KEY_UP)){
			this.origin.setOriginPosition(this.origin.getOX(), this.origin.getOY() - 5);
		}
		if(in.isKeyDown(Input.KEY_DOWN)){
			this.origin.setOriginPosition(this.origin.getOX(), this.origin.getOY() + 5);
		}
		
		for(Player p: players){
			p.update(arg0);
			if(p.isMoving()) p.move();
		}
		
		if(in.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
			
			
			
		}
		
		int taux = 2;
		if(in.isKeyPressed(Input.KEY_P)) this.origin.setWidth(this.origin.getWidth() + taux);
		if(in.isKeyPressed(Input.KEY_M)) this.origin.setWidth(this.origin.getWidth() - taux);
		
		if(in.isKeyPressed(Input.KEY_TAB)){
			this.labyrinth.rotateAdditionalWall();
		}
		
		if(in.isKeyPressed(Input.KEY_SPACE)) this.labyrinth.shakeWall();

		if(in.isKeyPressed(Input.KEY_ENTER)){
			this.players.get(this.joueur_en_cours).setNewDestination(null);			
			this.labyrinth.insertWallHere(Maze.INSERER_DEPUIS_DROITE, 1);
		}

		if(in.isKeyPressed(Input.KEY_G)) this.printGraph();
	}

	@Override
	public int getID() {
		return 0;
	}

	private void printGraph() {
		if(this.players.get(this.joueur_en_cours).isMoving()){
			this.players.get(this.joueur_en_cours).getPath().printGraph(0, 0);
		}
		
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		
		if(this.bouton_droit_souris){
			int x = this.origin.getOX() - (arg0 - arg2);
			int y = this.origin.getOY() - (arg1 - arg3);
			
			this.origin.setOriginPosition(x, y);
		}
		
	}
	
	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		if(arg0 == Input.MOUSE_RIGHT_BUTTON){
			this.bouton_droit_souris = true;
		}
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		if(arg0 == Input.MOUSE_RIGHT_BUTTON){
			this.bouton_droit_souris = false;
		}
		
	}
	
	@Override
	public void mouseWheelMoved(int move){
		
		if(move != 0){
			int old_width = this.origin.getWidth();
			if(move < 0){
				this.origin.setWidth(old_width - 2);
			}
			if(move > 0){
				this.origin.setWidth(old_width + 2);
			}
			
			int mx = this.input.getMouseX();
			int my = this.input.getMouseY();
			int x2 = (int) (mx - (mx - this.origin.getOX()) * this.origin.getWidth() / old_width);
			int y2 = (int) (my - (my - this.origin.getOY()) * this.origin.getWidth() / old_width);
			
			this.origin.setOriginPosition(x2, y2);
			
		}
		
		
	}

	@Override
	public void playerWantsToPushWall(int mouse_x, int mouse_y, int direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerWantsToMove(int mouse_x, int mouse_y) {

		int x = (int) ((mouse_x - this.origin.getOX()) / (this.origin.getWidth()));
		int y = (int) ((mouse_y - this.origin.getOY()) / (this.origin.getWidth()));
		
		if(x >= 0 && x <= this.labyrinth.getNumberOfCollumn() - 1 && y >= 0 && y <= this.labyrinth.getNumberOfLine() - 1){			
			this.labyrinth.resetWeightGraph();
			Wall dest = this.labyrinth.getWall(x, y);
			GraphVertex path = this.players.get(this.joueur_en_cours).getPosition().getShortestPathRecursive(dest);
			if(path != null){
				this.players.get(this.joueur_en_cours).setNewDestination(path);
			}
		}
	}

	@Override
	public void playerWantsToRotateAdditionalWall() {
		this.labyrinth.rotateAdditionalWall();
	}

	@Override
	public void playerHasFinishedHisRound() {
		
		this.players.get(this.joueur_en_cours).endOfRound();
		this.joueur_en_cours = (this.joueur_en_cours + 1) % this.players.size() ;
		this.players.get(this.joueur_en_cours).beginOfRound();
		
	}

	@Override
	public Objective playerWantsNewObjective() {
		
		Objective obj = new Objective(this.origin, this.labyrinth.getWall((int)(Math.random() * this.labyrinth.getNumberOfCollumn()), (int)(Math.random() * this.labyrinth.getNumberOfLine())), this.objective_textures);
		
		return obj;
	}

	
}
