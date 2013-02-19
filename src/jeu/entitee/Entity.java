package jeu.entitee;

import jeu.labyrinthe.Wall;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class Entity {

	protected Wall position;
	
	public Entity(Wall position){
		this.position = position;
	}
	
	public abstract void render(GameContainer gc, Graphics g);
	public abstract void update(GameContainer gc);
	
}