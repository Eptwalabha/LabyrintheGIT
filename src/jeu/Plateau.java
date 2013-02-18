package jeu;

import java.util.ArrayList;
import java.util.List;

import jeu.entitee.joueur.DepartJoueur;
import jeu.entitee.joueur.Joueur;
import jeu.labyrinthe.Labyrinthe;
import jeu.labyrinthe.Mur;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.graph.Sommet;

public class Plateau extends BasicGameState{

	private boolean bouton_droit_souris;
	
	private Labyrinthe labyrinthe;
	private Mur mur_supplementaire;
	private Origine origine = new Origine();
	
	private List<Joueur> joueurs = new ArrayList<Joueur>();
	
	private int joueur_en_cours = 0;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		this.labyrinthe = new Labyrinthe(7, 7, this.origine);
		this.mur_supplementaire = new Mur((int)(Math.random() * 2), 11, 0, (int)(Math.random() * 2), "", true);
		this.creationJoueurPrincipal();
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		this.labyrinthe.render(arg0, arg2);
		for(Joueur j : joueurs){
			j.getDepart().render(arg0, arg2);
		}
		
		for(Joueur j : joueurs){
			j.render(arg0, arg2);
		}
		
		/*
		if(this.joueurs.get(this.joueur_en_cours).seDeplace()){
			
			boolean first = true;
			Sommet curseur = this.joueurs.get(this.joueur_en_cours).getChemin();
			int oldx = 0, oldy = 0, x, y;
			float scale = this.origine.getTailleX();
			int coox = this.origine.getOX();
			int cooy = this.origine.getOY();
			do{
				if(!first){
					curseur = curseur.getSommetFrere();
				}
				
				x = (int) (coox + ((Mur) curseur.getSommetFils()).getCoordonneeX() * 64 * scale + 64 * scale / 2);
				y = (int) (cooy + ((Mur) curseur.getSommetFils()).getCoordonneeY() * 64 * scale + 64 * scale / 2);
				
				if(first){
					oldx = x;
					oldy = y;
					first = false;
				}
				
				arg2.setColor(Color.blue);
				arg2.drawLine(oldx, oldy, x, y);
				oldx = x;
				oldy = y;
			}while (curseur.getSommetFrere() != null);
			
		}
		*/
		
		this.mur_supplementaire.render(0, 0, 1.0f, 1.0f);
		

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		this.labyrinthe.update(arg0);
		Input in = arg0.getInput();
		
		if(in.isKeyDown(Input.KEY_LEFT)){
			this.origine.setPositionOrigine(this.origine.getOX() - 5, this.origine.getOY());
		}
		if(in.isKeyDown(Input.KEY_RIGHT)){
			this.origine.setPositionOrigine(this.origine.getOX() + 5, this.origine.getOY());
		}
		if(in.isKeyDown(Input.KEY_UP)){
			this.origine.setPositionOrigine(this.origine.getOX(), this.origine.getOY() - 5);
		}
		if(in.isKeyDown(Input.KEY_DOWN)){
			this.origine.setPositionOrigine(this.origine.getOX(), this.origine.getOY() + 5);
		}
		
		if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON) || in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			
			float scale = this.origine.getTailleX(); 
			int x = (int) ((in.getMouseX() - this.origine.getOX()) / (64 * scale));
			int y = (int) ((in.getMouseY() - this.origine.getOY()) / (64 * scale));
			
			if(x >= 0 && x <= this.labyrinthe.getTailleX() - 1 && y >= 0 && y <= this.labyrinthe.getTailleY() - 1){			
				this.labyrinthe.resetPassageGraphe();
				this.joueurs.get(this.joueur_en_cours).goTo(this.labyrinthe.getMur(x, y));
			
			}
		}
		if(in.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
			
			
			
		}
		
		float taux = 0.01f;
		if(in.isKeyPressed(Input.KEY_P)) this.origine.setUniteeOrigine(this.origine.getTailleX() + taux , this.origine.getTailleX() + taux);
		if(in.isKeyPressed(Input.KEY_M)) this.origine.setUniteeOrigine(this.origine.getTailleX() - taux , this.origine.getTailleX() - taux);
		
		if(in.isKeyPressed(Input.KEY_TAB)){
			this.mur_supplementaire.rotateCarte();
		}
		
		if(in.isKeyPressed(Input.KEY_SPACE)) this.labyrinthe.shakeWall();

		if(in.isKeyPressed(Input.KEY_ENTER)){
			this.joueurs.get(this.joueur_en_cours).resetChemin();
			this.mur_supplementaire = this.labyrinthe.insererMur(this.mur_supplementaire, Labyrinthe.INSERER_DEPUIS_DROITE, 1);
		}

		if(in.isKeyPressed(Input.KEY_G)) this.afficherGraph();
	}

	@Override
	public int getID() {
		return 0;
	}

	private void creationJoueurPrincipal() throws SlickException {
		
		DepartJoueur d = new DepartJoueur(Color.blue, this.labyrinthe.getMur(0,0));
		Joueur j = new Joueur("test", d, this.origine);
		this.joueurs.add(j);
		
	}

	public void ajouterUnJoueur(Joueur joueur){
		this.joueurs.add(joueur);
	}

	private void afficherGraph() {
		if(this.joueurs.get(this.joueur_en_cours).seDeplace()){
			this.joueurs.get(this.joueur_en_cours).getChemin().printGraph(0, 0);
		}
		
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		
		if(this.bouton_droit_souris){
			int x = this.origine.getOX() - (arg0 - arg2);
			int y = this.origine.getOY() - (arg1 - arg3);
			
			this.origine.setPositionOrigine(x, y);
		}
		
	}
	
	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		if(arg0 == Input.MOUSE_RIGHT_BUTTON){
			this.bouton_droit_souris = true;
		}
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		if(arg0 == Input.MOUSE_RIGHT_BUTTON){
			this.bouton_droit_souris = false;
		}
		
	}
	
	@Override
	public void mouseWheelMoved(int move){
		
		float scale = this.origine.getTailleX();
		if(move < 0){
			this.origine.setUniteeOrigine(scale - 0.02f, scale - 0.02f);
		}
		if(move > 0){
			this.origine.setUniteeOrigine(scale + 0.02f, scale + 0.02f);
		}
		
	}

	
}
