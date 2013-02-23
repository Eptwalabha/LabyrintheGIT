package com.labyrinth.game;

import com.labyrinth.game.maze.Maze;
import com.labyrinth.game.maze.Wall;
import com.labyrinth.game.player.PlayerListener;

import java.util.ArrayList;
import java.util.List;

import com.labyrinth.jeu.entitee.joueur.PlayerDeparture;
import com.labyrinth.jeu.entitee.joueur.Player;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameBoard extends BasicGameState implements PlayerListener{

	private boolean bouton_droit_souris;
	
	private Maze labyrinth;

	private Origin origin = new Origin();
	private List<Player> players = new ArrayList<Player>();
	private List<com.labyrinth.game.player.Player> players2 = new ArrayList<com.labyrinth.game.player.Player>();
	
	private int joueur_en_cours = 0;
	
	private Input input;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		this.labyrinth = new Maze(7, 7, this.origin);
		this.createMainPlayer();
		this.input = arg0.getInput();
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		this.labyrinth.render(arg0, arg2);
		
		for(Player j : players){
			j.render(arg0, arg2);
		}
		
		/*
		if(this.joueurs.get(this.joueur_en_cours).seDeplace()){
			
			boolean first = true;
			Sommet curseur = this.joueurs.get(this.joueur_en_cours).getChemin();
			int oldx = 0, oldy = 0, x, y;
			float scale = this.origine.getTailleX();
			int coox = this.origine.getOX();
			int cooy = this.origine.getOY();
			do{
				if(!first){
					curseur = curseur.getSommetFrere();
				}
				
				x = (int) (coox + ((Mur) curseur.getSommetFils()).getCoordonneeX() * 64 * scale + 64 * scale / 2);
				y = (int) (cooy + ((Mur) curseur.getSommetFils()).getCoordonneeY() * 64 * scale + 64 * scale / 2);
				
				if(first){
					oldx = x;
					oldy = y;
					first = false;
				}
				
				arg2.setColor(Color.blue);
				arg2.drawLine(oldx, oldy, x, y);
				oldx = x;
				oldy = y;
			}while (curseur.getSommetFrere() != null);
			
		}
		*/
		
		this.labyrinth.getAdditionalWall().render(0, 0, 1.0f, 1.0f);
		

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
		
		if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON) || in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			
			float scale = this.origin.getSizeX(); 
			int x = (int) ((in.getMouseX() - this.origin.getOX()) / (64 * scale));
			int y = (int) ((in.getMouseY() - this.origin.getOY()) / (64 * scale));
			
			if(x >= 0 && x <= this.labyrinth.getNumberOfCollumn() - 1 && y >= 0 && y <= this.labyrinth.getNumberOfLine() - 1){			
				this.labyrinth.resetWeightGraph();
				this.players.get(this.joueur_en_cours).goTo(this.labyrinth.getWall(x, y));
			
			}
		}
		if(in.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
			
			
			
		}
		
		float taux = 0.01f;
		if(in.isKeyPressed(Input.KEY_P)) this.origin.setOriginUnit(this.origin.getSizeX() + taux , this.origin.getSizeX() + taux);
		if(in.isKeyPressed(Input.KEY_M)) this.origin.setOriginUnit(this.origin.getSizeX() - taux , this.origin.getSizeX() - taux);
		
		if(in.isKeyPressed(Input.KEY_TAB)){
			this.labyrinth.rotateAdditionalWall();
		}
		
		if(in.isKeyPressed(Input.KEY_SPACE)) this.labyrinth.shakeWall();

		if(in.isKeyPressed(Input.KEY_ENTER)){
			this.players.get(this.joueur_en_cours).resetPath();
//			Wall temp = this.labyrinth.insertWall(this.additionnal_wall, Maze.INSERER_DEPUIS_DROITE, 1);
//			if(this.players.get(this.joueur_en_cours).getPosition() == temp){
//				this.players.get(this.joueur_en_cours).setPosition(this.additionnal_wall);
//				this.additionnal_wall = temp;
//			}
			
			this.labyrinth.insertWallHere(Maze.INSERER_DEPUIS_DROITE, 1);
		}

		if(in.isKeyPressed(Input.KEY_G)) this.printGraph();
	}

	@Override
	public int getID() {
		return 0;
	}

	private void createMainPlayer() throws SlickException {
		
		PlayerDeparture d = new PlayerDeparture(Color.blue, this.labyrinth.getWall(0,0));
		Player j = new Player("test", d, this.origin);
		this.players.add(j);
		
	}

	public void addPlayer(Player player){
		this.players.add(player);
	}

	private void printGraph() {
		if(this.players.get(this.joueur_en_cours).seDeplace()){
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
			float scale = this.origin.getSizeX();
			if(move < 0){
				this.origin.setOriginUnit(scale - 0.02f, scale - 0.02f);
			}
			if(move > 0){
				this.origin.setOriginUnit(scale + 0.02f, scale + 0.02f);
			}
			
			int mx = this.input.getMouseX();
			int my = this.input.getMouseY();
			int x2 = (int) (mx - (mx - this.origin.getOX()) * this.origin.getSizeX() / scale);
			int y2 = (int) (my - (my - this.origin.getOY()) * this.origin.getSizeY() / scale);
			
			this.origin.setOriginPosition(x2, y2);
			
		}
		
		
	}

	@Override
	public void playerWantsToPushWall(int mouse_x, int mouse_y, int direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerWantsToMove(int mouse_x, int mouse_y) {
		
		float scale = this.origin.getSizeX(); 
		int x = (int) ((mouse_x - this.origin.getOX()) / (64 * scale));
		int y = (int) ((mouse_y - this.origin.getOY()) / (64 * scale));
		
		if(x >= 0 && x <= this.labyrinth.getNumberOfCollumn() - 1 && y >= 0 && y <= this.labyrinth.getNumberOfLine() - 1){			
			this.labyrinth.resetWeightGraph();
			Wall dest = this.labyrinth.getWall(x, y);
			
			this.players2.get(this.joueur_en_cours).setNewDestination(this.players2.get(this.joueur_en_cours).getPosition().getShortestPathRecursive(dest));
			
		}
	}

	@Override
	public void playerWantsToRotateAdditionalWall() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerHasFinishedHisRound() {
		// TODO Auto-generated method stub
		
	}

	
}