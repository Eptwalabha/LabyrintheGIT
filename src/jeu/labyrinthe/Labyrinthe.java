package jeu.labyrinthe;

import java.util.ArrayList;
import java.util.List;

import jeu.entitee.joueur.DepartJoueur;
import jeu.entitee.joueur.Joueur;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import utils.graph.Sommet;

public class Labyrinthe {

	private Mur[][] murs;
	private int taille_x, taille_y;
	
	private List<Joueur> joueurs = new ArrayList<Joueur>();
	
	private int joueur_en_cours = 0;
	
	public Labyrinthe() throws SlickException{
		this(7, 7);
	}
	
	public Labyrinthe(int taille_x, int taille_y) throws SlickException{
		
		if(taille_x < 5) taille_x = 5;
		if(taille_x % 2 == 0) taille_x++;
		if(taille_y < 5) taille_y = 5;
		if(taille_y % 2 == 0) taille_y++;
		
		this.taille_x = taille_x;
		this.taille_y = taille_y;
		
		this.creationLabyrinthe();
		this.creationGraph();
		this.creationJoueurPrincipal();
	}
	
	private void creationJoueurPrincipal() throws SlickException {
		
		DepartJoueur d = new DepartJoueur(Color.blue, this.murs[0][0]);
		Joueur j = new Joueur("test", d);
		this.joueurs.add(j);
		
	}

	public void ajouterUnJoueur(Joueur joueur){
		this.joueurs.add(joueur);
	}
	
	private void creationLabyrinthe() throws SlickException{

		this.murs = new Mur[this.taille_x][this.taille_y];
				
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				
				if((i % 2 == 0) && (j % 2 == 0)){
					if(i == 0){
						if(j == 0) this.murs[i][j] = new Mur(Mur.TYPE_L, i, j, Mur.L_DROITE_BAS, i+"."+j, false);
						if(j == this.taille_y - 1) this.murs[i][j] = new Mur(Mur.TYPE_L, i, j, Mur.L_HAUT_DROITE, i+"."+j, false);
						if(j > 0 && j < this.taille_y - 1) this.murs[i][j] = new Mur(Mur.TYPE_T, i, j, Mur.T_VERS_LA_DROITE, i+"."+j, false);
					}else if(i == this.taille_x - 1){
						if(j == 0) this.murs[i][j] = new Mur(Mur.TYPE_L, i, j, Mur.L_BAS_GAUCHE, i+"."+j, false);
						if(j == this.taille_y - 1) this.murs[i][j] = new Mur(Mur.TYPE_L, i, j, Mur.L_GAUCHE_HAUT, i+"."+j, false);
						if(j > 0 && j < this.taille_y - 1) this.murs[i][j] = new Mur(Mur.TYPE_T, i, j, Mur.T_VERS_LA_GAUCHE, i+"."+j, false);
					}else{
						if(j == 0) this.murs[i][j] = new Mur(Mur.TYPE_T, i, j, Mur.T_VERS_LE_BAS, i+"."+j, false);
						if(j == this.taille_y - 1) this.murs[i][j] = new Mur(Mur.TYPE_T, i, j, Mur.T_VERS_LE_HAUT, i+"."+j, false);
						if(j > 0 && j < this.taille_y - 1) this.murs[i][j] = new Mur(Mur.TYPE_T, i, j, (int)(Math.random() * 4), i+"."+j, false);
					}
				}else{
					int type = (int) (Math.random() * 2);
					int orientation = (int) (Math.random() * 4);
					
					// à retirer, juste là pour créer plus de chemin.
					type = Mur.TYPE_T;
					
					this.murs[i][j] = new Mur(type, i, j, orientation, i + "." + j, true);
				}				
			}
		}
	}
	
	private void creationGraph(){
		
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				
				Mur nord = (j > 0) ? this.murs[i][j - 1] : null;
				Mur est = (i < this.taille_x - 1) ? this.murs[i + 1][j] : null;
				Mur sud = (j < this.taille_y - 1) ? this.murs[i][j + 1] : null;
				Mur ouest = (i > 0) ? this.murs[i - 1][j] : null;
				
				this.murs[i][j].connecterMurs(nord, est, sud, ouest);
			}
		}
	}
	
	public Sommet cheminEntreAEtB(Mur a, Mur b){
		return a.getPlusPetitCheminRecursif(b);
	}
	
	public void render(GameContainer gc, Graphics g){
		for(int i = 0; i < this.murs.length; i++){
			for(int j = 0; j < this.murs[i].length; j++){
				this.murs[i][j].render(10.0f);
			}
		}
		
		for(Joueur j : joueurs){
			j.getDepart().render(gc, g);
		}
		
		for(Joueur j : joueurs){
			j.render(gc, g);
		}
		
		if(this.joueurs.get(this.joueur_en_cours).seDeplace()){
			
			boolean first = true;
			Sommet curseur = this.joueurs.get(this.joueur_en_cours).getChemin();
			int oldx = 0, oldy = 0, x, y;
			do{
				if(!first){
					curseur = curseur.getSommetFrere();
				}
				x = ((Mur) curseur.getSommetFils()).getPosX() * 20 + 10;
				y = ((Mur) curseur.getSommetFils()).getPosY() * 20 + 10;
				
				if(first){
					oldx = x;
					oldy = y;
					first = false;
				}
				
				g.setColor(Color.blue);
				g.drawLine(oldx, oldy, x, y);
				oldx = x;
				oldy = y;
			}while (curseur.getSommetFrere() != null);
			
		}
	}
	
	public void update(GameContainer gc){

		Input in = gc.getInput();
		
		if(in.isMousePressed(Input.MOUSE_LEFT_BUTTON) || in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			int x = (in.getMouseX() / 20 > this.taille_x - 1) ? this.taille_x - 1 : in.getMouseX() / 20;
			int y = (in.getMouseY() / 20 > this.taille_y - 1) ? this.taille_y - 1 : in.getMouseY() / 20;
			
			for(int i = 0; i < this.taille_x; i++){
				for(int j = 0; j < this.taille_y; j++){
					this.murs[i][j].resetPassage();
				}
			}

			this.joueurs.get(this.joueur_en_cours).goTo(this.murs[x][y]);
			
			System.out.println("press (" + x + "." + y + ")" + this.joueurs.get(this.joueur_en_cours).seDeplace());
		}
		
		if(in.isKeyPressed(Input.KEY_SPACE)) this.shakeWall();
		if(in.isKeyPressed(Input.KEY_ENTER)) this.afficherGraph();
		
	}
	
	private void afficherGraph() {
		// TODO Auto-generated method stub
		if(this.joueurs.get(this.joueur_en_cours).seDeplace()){
			this.joueurs.get(this.joueur_en_cours).getChemin().printGraph(0, 0);
		}
		
	}

	public void randomMap() throws SlickException{
		
		this.creationLabyrinthe();
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				this.murs[i][j].resetPassage();
			}
		}
		this.creationGraph();
		
	}
	
	private void shakeWall(){
		
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				if(this.murs[i][j].estMobile()) this.murs[i][j].setRotation((int)(Math.random() * 4));
			}
		}
		
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				this.murs[i][j].resetPassage();
			}
		}
		this.creationGraph();

	}
}
