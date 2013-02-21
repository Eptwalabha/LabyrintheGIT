package com.labyrinth.jeu.entitee.joueur;

import com.labyrinth.game.Origin;
import com.labyrinth.game.maze.Wall;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.labyrinth.utils.graph.GraphVertex;

import com.labyrinth.jeu.entitee.Entity;

public class Player extends Entity{

	private Origin origin;
	private String name = "<nom du joueur>";
	private GraphVertex destination = null;
	private PlayerDeparture departure;
	
	private Image im;
	
	public Player(String name, PlayerDeparture departure, Origin origin) throws SlickException{
		super(departure.getPosition());
		this.name = name;
		this.departure = departure;
		this.im = new Image("images/items/joueur.png");
		this.im.setFilter(Image.FILTER_NEAREST);
		this.origin = origin;
	}
	
	public String getNomJoueur(){
		return this.name;
	}
	
	public Wall getPosition(){
		return this.position;
	}
	
	public void setPosition(Wall new_position){
		this.position = new_position;
	}
	
	public PlayerDeparture getDepart(){
		return this.departure;
	}
	public boolean seDeplace(){
		return (destination != null);
	}
	
	public void goTo(Wall destination){
		this.destination = this.position.getShortestPathRecursive(destination);
		if(this.destination != null){
			this.position = destination;
		}
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		// TODO Auto-generated method stub
		
		int x = (int) (this.position.getCoordinateX() * 64 * this.origin.getSizeX() + this.origin.getOX());
		int y = (int) (this.position.getCoordinateY() * 64 * this.origin.getSizeX() + this.origin.getOY());
		
		im.getScaledCopy(this.origin.getSizeX()).draw(x, y);
	}

	@Override
	public void update(GameContainer gc) {
		// TODO Auto-generated method stub
		
	}

	public GraphVertex getPath() {
		return this.destination;
	}

	public void resetPath() {
		this.destination = null;
	}
	
}
