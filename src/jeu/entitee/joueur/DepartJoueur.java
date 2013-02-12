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
		g.setColor(Color.black);
		
		g.drawOval(this.position.getPosX() * 20 + 10, this.position.getPosY() * 20 + 10, 7, 7);
		g.setColor(this.couleur);
		g.drawOval(this.position.getPosX() * 20 + 10, this.position.getPosY() * 20 + 10, 5, 5);
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
