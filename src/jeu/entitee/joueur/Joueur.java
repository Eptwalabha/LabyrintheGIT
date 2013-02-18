package jeu.entitee.joueur;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import utils.graph.Sommet;

import jeu.Origine;
import jeu.entitee.Entitee;
import jeu.labyrinthe.Mur;

public class Joueur extends Entitee{

	private Origine origine;
	private String name = "<nom du joueur>";
	private Sommet destination = null;
	private DepartJoueur depart;
	
	private Image im;
	
	public Joueur(String name, DepartJoueur depart, Origine origine) throws SlickException{
		super(depart.getPosition());
		this.name = name;
		this.depart = depart;
		this.im = new Image("images/items/joueur.png");
		this.im.setFilter(Image.FILTER_NEAREST);
		this.origine = origine;
	}
	
	public String getNomJoueur(){
		return this.name;
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
		if(this.destination != null){
			this.position = destination;
		}
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		// TODO Auto-generated method stub
		
		int x = (int) (this.position.getCoordonneeX() * 64 * this.origine.getTailleX() + this.origine.getOX());
		int y = (int) (this.position.getCoordonneeY() * 64 * this.origine.getTailleX() + this.origine.getOY());
		
		im.getScaledCopy(this.origine.getTailleX()).draw(x, y);
	}

	@Override
	public void update(GameContainer gc) {
		// TODO Auto-generated method stub
		
	}

	public Sommet getChemin() {
		return this.destination;
	}

	public void resetChemin() {
		this.destination = null;
	}
	
}
