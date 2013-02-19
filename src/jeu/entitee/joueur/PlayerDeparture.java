package jeu.entitee.joueur;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import jeu.entitee.Entity;
import jeu.labyrinthe.Wall;

public class PlayerDeparture extends Entity {

	private Color couleur;
	
	public PlayerDeparture(Color couleur, Wall position){
		super(position);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		// TODO Auto-generated method stub

		int x = (int) (this.position.getCoordinateX() * 64 * this.position.getScale());
		int y = (int) (this.position.getCoordinateY() * 64 * this.position.getScale());
		
		g.setColor(Color.black);
		
		g.drawOval(x + 32 - 10, y + 32 - 10, 20, 20);
		g.setColor(this.couleur);
		g.drawOval(x + 32 - 11, y + 32 - 11, 22, 22);
	}

	@Override
	public void update(GameContainer gc) {
		// TODO Auto-generated method stub
		
	}

	public Wall getPosition() {
		// TODO Auto-generated method stub
		return this.position;
	}


	
}
