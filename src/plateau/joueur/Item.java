package plateau.joueur;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import plateau.nodes.Carte;

public abstract class Item {
	
	protected Color couleur;
	protected Carte position;
	protected Image image;

	public Item(Carte position, Color couleur){
		this.position = position;
		this.couleur = couleur;
	}
	
	protected Carte getPosition(){
		return this.position;
	}
	
	protected Color getCouleur(){
		return this.couleur;
	}
	
}
