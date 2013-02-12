package jeu.entitee.joueur;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import utils.graph.Sommet;

import jeu.entitee.Entitee;
import jeu.labyrinthe.Mur;

public class Joueur extends Entitee{

	public String name = "<nom du joueur>";
	public Sommet destination = null;
	private DepartJoueur depart;
	private Image im;
	
	public Joueur(String name, DepartJoueur depart) throws SlickException{
		super(depart.getPosition());
		this.name = name;
		this.depart = depart;
		this.im = new Image("images/items/joueur.png").getScaledCopy(15, 15);
	}
	
	public Mur getPosition(){
		return this.position;
	}
	
	public DepartJoueur getDepart(){
		return this.depart;
	}
	public boolean seDeplace(){
		return (destination != null);
	}
	
	public void goTo(Mur destination){
		this.destination = this.position.getPlusPetitCheminRecursif(destination);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		// TODO Auto-generated method stub
		im.draw(this.position.getPosX(), this.position.getPosY());
	}

	@Override
	public void update(GameContainer gc) {
		// TODO Auto-generated method stub
		
	}

	public Sommet getChemin() {
		return this.destination;
	}
	
}
