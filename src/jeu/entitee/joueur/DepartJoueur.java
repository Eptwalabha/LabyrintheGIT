package jeu.entitee.joueur;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import jeu.entitee.Entitee;
import jeu.labyrinthe.Mur;

public class DepartJoueur extends Entitee {

	private Color couleur;
	
	public DepartJoueur(Color couleur, Mur position){
		super(position);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		// TODO Auto-generated method stub

		int x = (int) (this.position.getCoordonneeX() * 64 * this.position.getScale());
		int y = (int) (this.position.getCoordonneeY() * 64 * this.position.getScale());
		
		g.setColor(Color.black);
		
		g.drawOval(x + 32 - 10, y + 32 - 10, 20, 20);
		g.setColor(this.couleur);
		g.drawOval(x + 32 - 11, y + 32 - 11, 22, 22);
	}

	@Override
	public void update(GameContainer gc) {
		// TODO Auto-generated method stub
		
	}

	public Mur getPosition() {
		// TODO Auto-generated method stub
		return this.position;
	}


	
}
