package plateau;

import java.io.UnsupportedEncodingException;

import jeu.labyrinthe.Mur;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.graph.Sommet;

public class Plateau2 extends BasicGameState{

	private Input input;
	
	public static final int ID = 1;
	
	public static final int INSERTION_A_GAUCHE = 0;
	public static final int INSERTION_A_DROITE = 1;
	public static final int INSERTION_EN_HAUT = 2;
	public static final int INSERTION_EN_BAS = 3;
	
	private int taille_x, taille_y;
	private Mur[][] cartes;
	private Sommet chemin = null;
	private boolean render_graph = false;
//	private Mur carte_suplementaire;

	public void setPlateau(int taille_x, int taille_y) throws SlickException{
		
		if(taille_x < 5) taille_x = 5;
		if(taille_y < 5) taille_y = 5;
		if(taille_x % 2 == 0) taille_x++;
		if(taille_y % 2 == 0) taille_y++;
		this.taille_x = taille_x;
		this.taille_y = taille_y;
		
		this.randomMap();
	}
	
	private void initialisationPlateau() throws SlickException{
		
		this.cartes = new Mur[this.taille_x][this.taille_y];
		
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				
				if((i % 2 == 0) && (j % 2 == 0)){
					if(i == 0){
						if(j == 0) this.cartes[i][j] = new Mur(Mur.TYPE_L, i, j, Mur.L_DROITE_BAS, i+"."+j, false);
						if(j == this.taille_y - 1)	this.cartes[i][j] = new Mur(Mur.TYPE_L, i, j, Mur.L_HAUT_DROITE, i+"."+j, false);
						if(j > 0 && j < this.taille_y - 1)	this.cartes[i][j] = new Mur(Mur.TYPE_T, i, j, Mur.T_VERS_LA_DROITE, i+"."+j, false);
					}else if(i == this.taille_x - 1){
						if(j == 0) this.cartes[i][j] = new Mur(Mur.TYPE_L, i, j, Mur.L_BAS_GAUCHE, i+"."+j, false);
						if(j == this.taille_y - 1)	this.cartes[i][j] = new Mur(Mur.TYPE_L, i, j, Mur.L_GAUCHE_HAUT, i+"."+j, false);
						if(j > 0 && j < this.taille_y - 1)	this.cartes[i][j] = new Mur(Mur.TYPE_T, i, j, Mur.T_VERS_LA_GAUCHE, i+"."+j, false);
					}else{
						if(j == 0) this.cartes[i][j] = new Mur(Mur.TYPE_T, i, j, Mur.T_VERS_LE_BAS, i+"."+j, false);
						if(j == this.taille_y - 1) this.cartes[i][j] = new Mur(Mur.TYPE_T, i, j, Mur.T_VERS_LE_HAUT, i+"."+j, false);
						if(j > 0 && j < this.taille_y - 1) this.cartes[i][j] = new Mur(Mur.TYPE_T, i, j, (int) (Math.random() * 4), i+"."+j, false);
					}
				}else{
					int type = (int) (Math.random() * 2);
					int orientation;
					type = 2;
					if(type == 0){
						orientation = (int) (Math.random() * 4);
					}else{
						orientation = (int) (Math.random() * 2);
					}
					
					this.cartes[i][j] = new Mur(type, i, j, orientation, i+"."+j, true);
				}
			}
		}
	}
	
	private void creationGraph(){
		
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				
				Mur nord = (j >= 1) ? this.cartes[i][j - 1] : null;
				Mur est = (i <= this.taille_x - 2) ? this.cartes[i + 1][j] : null;
				Mur sud = (j <= this.taille_y - 2) ? this.cartes[i][j + 1] : null;
				Mur ouest = (i >= 1) ? this.cartes[i - 1][j] : null;
				
				this.cartes[i][j].connecterMurs(nord, est, sud, ouest);
				
			}
		}
	}
	
	
	
	public void insererMur(int type_insertion, int palier, Mur mur){
		
		
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		this.input = arg0.getInput();
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				this.cartes[i][j].render(10.0f);
			}
		}
		
		if(this.render_graph && this.chemin != null){
			
			boolean first = true;
			Sommet curseur = this.chemin;
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
				
				arg2.setColor(Color.blue);
				arg2.drawLine(oldx, oldy, x, y);
				oldx = x;
				oldy = y;
			}while (curseur.getSommetFrere() != null);
			
		}
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				this.cartes[i][j].update(this.input.getMouseX(), this.input.getMouseY());
			}
		}
		
		this.render_graph = false;
		if(this.input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || this.input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) this.testGraph(this.input.getMouseX(), this.input.getMouseY());
		if(this.input.isKeyPressed(Input.KEY_SPACE)) this.randomMap();
		if(this.input.isKeyPressed(Input.KEY_ENTER)) this.afficherGraph();
		if(this.input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) this.render_graph = true;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private void testGraph(int posx, int posy){
		
		int a = posx / 20;
		int b = posy / 20;
		
		if(a >= this.taille_x) a = taille_x - 1;
		if(b >= this.taille_y) b = taille_y - 1;
		
//		System.out.println("de 0-0 vers " + a + "-" + b);
		
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				this.cartes[i][j].resetPassage();
			}
		}
		this.chemin = this.cartes[0][0].getPlusPetitCheminRecursif(this.cartes[a][b]);
		
	}
	
	private void afficherGraph(){
		
		this.afficherGraph00();
		
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				this.cartes[i][j].resetPassage();
			}
		}
		
		if(this.chemin != null){
			this.chemin.printGraph(0, 0);
		}else{
			System.out.println("Rhoooo... pas de chemin");
		}
	}
	
	private void afficherGraph00(){
		
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				this.cartes[i][j].resetPassage();
			}
		}
			this.cartes[0][0].printGraph(0, 0);
		
	}
	
	private void randomMap() throws SlickException{
		
		this.initialisationPlateau();
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				this.cartes[i][j].resetPassage();
			}
		}
		this.creationGraph();
		
	}
}