package jeu.entitee;

import jeu.labyrinthe.Mur;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class Entitee {

	protected Mur position;
	
	public Entitee(Mur position){
		this.position = position;
	}
	
	public abstract void render(GameContainer gc, Graphics g);
	public abstract void update(GameContainer gc);
	
}
