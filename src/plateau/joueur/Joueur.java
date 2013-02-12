package plateau.joueur;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import plateau.nodes.Carte;

public class Joueur extends Item{

	private String nom_joueur;
	
	public Joueur(Carte position, String nom, Color couleur){
		super(position, couleur);
		this.nom_joueur = nom;
	}
	
	public String getNomJoueur(){
		return this.nom_joueur;
	}
	
	public int getPositionXJoueur(){
		return this.position.getPosX();
	}
	
	public int getPositionYJoueur(){
		return this.position.getPosY();
	}
	
	public void deplacerJoueur(ArrayList<Carte> chemin){
		
	}	
	
}
