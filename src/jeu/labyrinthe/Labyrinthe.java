package jeu.labyrinthe;

import jeu.Origine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import utils.graph.Sommet;

public class Labyrinthe {

	public static final int INSERER_DEPUIS_HAUT = 0;
	public static final int INSERER_DEPUIS_DROITE = 1;
	public static final int INSERER_DEPUIS_BAS = 2;
	public static final int INSERER_DEPUIS_GAUCHE = 3;

	private Mur[][] murs;
	
	private Origine origine;
	private int taille_x, taille_y;
		
	public Labyrinthe(Origine origine) throws SlickException{
		this(7, 7, origine);
	}
	
	public Labyrinthe(int taille_x, int taille_y, Origine origine) throws SlickException{
		
		this.origine = origine;
		if(taille_x < 5) taille_x = 5;
		if(taille_x % 2 == 0) taille_x++;
		if(taille_y < 5) taille_y = 5;
		if(taille_y % 2 == 0) taille_y++;
		
		this.taille_x = taille_x;
		this.taille_y = taille_y;
				
		this.creationLabyrinthe();
		this.creationGraphe();
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
					
//					type = Mur.TYPE_T;
					
					this.murs[i][j] = new Mur(type, i, j, orientation, i + "." + j, true);
				}
			}
		}
	}
	
	private void creationGraphe(){
		
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
		
		int coox = this.origine.getOX();
		int cooy = this.origine.getOY();
		float unix = this.origine.getTailleX();
		float uniy = this.origine.getTailleY();
		for(int i = 0; i < this.murs.length; i++){
			for(int j = 0; j < this.murs[i].length; j++){
				this.murs[i][j].render(coox, cooy, unix, uniy);
			}
		}
	}
	
	public void update(GameContainer gc){
		
	}
	
	public void randomMap() throws SlickException{
		
		this.creationLabyrinthe();
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				this.murs[i][j].resetPassage();
			}
		}
		this.creationGraphe();
		
	}
	
	public void shakeWall(){
		
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
		this.creationGraphe();

	}
	
	public Mur insererMur(Mur mur_supplementaire, int mode, int index){
		
		if(mode == INSERER_DEPUIS_BAS || mode == INSERER_DEPUIS_HAUT){
			if(index < this.taille_x && index >= 0){
				Mur temp = (mode == INSERER_DEPUIS_BAS) ? this.murs[index][0] : this.murs[index][this.taille_y - 1];
				for(int i = 0; i < this.taille_y; i++){
					System.out.println("plop");
					if(mode == INSERER_DEPUIS_BAS){
						if(i < this.taille_y - 1){
							this.murs[index][i] = this.murs[index][i + 1];
							this.murs[index][i].setCoordonnees(index, i);
						}
					}else{
						if(taille_y - 1 - i > 0){
							this.murs[index][taille_y - 1 - i] = this.murs[index][taille_y - 2 - i];
							this.murs[index][taille_y - 1 - i].setCoordonnees(index, taille_y - 1 - i);
						}
					}
				}
				if(mode == INSERER_DEPUIS_BAS){
					this.murs[index][this.taille_y - 1] = mur_supplementaire;
					this.murs[index][taille_y - 1].setCoordonnees(index, taille_y - 1);
				}else{
					this.murs[index][0] = mur_supplementaire;
					this.murs[index][0].setCoordonnees(index, 0);
				}
				mur_supplementaire = temp;
			} 
		}else{
			if(index < this.taille_y && index >= 0){
				Mur temp = (mode == INSERER_DEPUIS_DROITE) ? this.murs[0][index] : this.murs[this.taille_x - 1][index];
				for(int i = 0; i < this.taille_x; i++){
					if(mode == INSERER_DEPUIS_DROITE){
						if(i < this.taille_x - 1){
							this.murs[i][index] = this.murs[i + 1][index];
							this.murs[i][index].setCoordonnees(i, index);
						}
					}else{
						if(taille_x - 1 - i > 0){
							this.murs[taille_x - 1 - i][index] = this.murs[taille_x - 2 - i][index];
							this.murs[taille_x - 1 - i][index].setCoordonnees(taille_x - 1 - i, index);
						}
					}
				}
				if(mode == INSERER_DEPUIS_DROITE){
					this.murs[this.taille_y - 1][index] = mur_supplementaire;
					this.murs[this.taille_y - 1][index].setCoordonnees(this.taille_y - 1, index);
				}else{
					this.murs[0][index] = mur_supplementaire;
					this.murs[0][index].setCoordonnees(0, index);
				}
				mur_supplementaire = temp;
			} 
		}
		
		this.creationGraphe();
		
		mur_supplementaire.setCoordonnees(taille_x, 0);
		return mur_supplementaire;
	}
	
	public Mur getMur(int x, int y){
		return this.murs[x][y];
	}
	
	public int getTailleX(){
		return this.taille_x;
	}
	
	public int getTailleY(){
		return this.taille_y;
	}
	
	public void resetPassageGraphe(){
		
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				this.murs[i][j].resetPassage();
			}
		}
	}
}
