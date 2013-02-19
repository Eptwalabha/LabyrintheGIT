package jeu.labyrinthe;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.parser.Entity;

import jeu.Origin;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import utils.graph.GraphVertice;

public class Labyrinth {

	public static final int INSERER_DEPUIS_HAUT = 0;
	public static final int INSERER_DEPUIS_DROITE = 1;
	public static final int INSERER_DEPUIS_BAS = 2;
	public static final int INSERER_DEPUIS_GAUCHE = 3;

	private Wall[][] walls;
	private Wall additional_wall;
	private List<Entity> entities;
	
	private Origin origin;
	private int nbr_of_collumn, nbr_of_line;
		
	public Labyrinth(Origin origin) throws SlickException{
		this(7, 7, origin);
	}
	
	public Labyrinth(int nbr_of_collumn, int nbr_of_line, Origin origin) throws SlickException{
		
		this.origin = origin;
		if(nbr_of_collumn < 5) nbr_of_collumn = 5;
		if(nbr_of_collumn % 2 == 0) nbr_of_collumn++;
		if(nbr_of_line < 5) nbr_of_line = 5;
		if(nbr_of_line % 2 == 0) nbr_of_line++;
		
		this.nbr_of_collumn = nbr_of_collumn;
		this.nbr_of_line = nbr_of_line;
				
		this.labyrinthCreation();
		this.graphCreation();
	}
		
	private void labyrinthCreation() throws SlickException{

		this.walls = new Wall[this.nbr_of_collumn][this.nbr_of_line];
				
		for(int i = 0; i < this.nbr_of_collumn; i++){
			for(int j = 0; j < this.nbr_of_line; j++){
				
				if((i % 2 == 0) && (j % 2 == 0)){
					if(i == 0){
						if(j == 0) this.walls[i][j] = new Wall(Wall.TYPE_L, i, j, Wall.L_BAS_DROITE, i+"."+j, false);
						if(j == this.nbr_of_line - 1) this.walls[i][j] = new Wall(Wall.TYPE_L, i, j, Wall.L_HAUT_DROITE, i+"."+j, false);
						if(j > 0 && j < this.nbr_of_line - 1) this.walls[i][j] = new Wall(Wall.TYPE_T, i, j, Wall.T_VERS_LA_DROITE, i+"."+j, false);
					}else if(i == this.nbr_of_collumn - 1){
						if(j == 0) this.walls[i][j] = new Wall(Wall.TYPE_L, i, j, Wall.L_BAS_GAUCHE, i+"."+j, false);
						if(j == this.nbr_of_line - 1) this.walls[i][j] = new Wall(Wall.TYPE_L, i, j, Wall.L_HAUT_GAUCHE, i+"."+j, false);
						if(j > 0 && j < this.nbr_of_line - 1) this.walls[i][j] = new Wall(Wall.TYPE_T, i, j, Wall.T_VERS_LA_GAUCHE, i+"."+j, false);
					}else{
						if(j == 0) this.walls[i][j] = new Wall(Wall.TYPE_T, i, j, Wall.T_VERS_LE_BAS, i+"."+j, false);
						if(j == this.nbr_of_line - 1) this.walls[i][j] = new Wall(Wall.TYPE_T, i, j, Wall.T_VERS_LE_HAUT, i+"."+j, false);
						if(j > 0 && j < this.nbr_of_line - 1) this.walls[i][j] = new Wall(Wall.TYPE_T, i, j, (int)(Math.random() * 4), i+"."+j, false);
					}
				}else{
					int type = (int) (Math.random() * 2);
					int orientation = (int) (Math.random() * 4);
					
//					type = Wall.TYPE_T;
					
					this.walls[i][j] = new Wall(type, i, j, orientation, i + "." + j, true);
				}
			}
		}
	}
	
	private void graphCreation(){
		
		for(int i = 0; i < this.nbr_of_collumn; i++){
			for(int j = 0; j < this.nbr_of_line; j++){
				
				Wall nord = (j > 0) ? this.walls[i][j - 1] : null;
				Wall est = (i < this.nbr_of_collumn - 1) ? this.walls[i + 1][j] : null;
				Wall sud = (j < this.nbr_of_line - 1) ? this.walls[i][j + 1] : null;
				Wall ouest = (i > 0) ? this.walls[i - 1][j] : null;
				
				this.walls[i][j].linkWalls(nord, est, sud, ouest);
			}
		}
		
	}
	
	public GraphVertice pathBetweenAAndB(Wall a, Wall b){
		return a.getShortestPathRecursive(b);
	}
	
	public void render(GameContainer gc, Graphics g){
		
		int coox = this.origin.getOX();
		int cooy = this.origin.getOY();
		float unix = this.origin.getSizeX();
		float uniy = this.origin.getSizeY();
		for(int i = 0; i < this.walls.length; i++){
			for(int j = 0; j < this.walls[i].length; j++){
				this.walls[i][j].render(coox, cooy, unix, uniy);
			}
		}
	}
	
	public void update(GameContainer gc){
		
	}
	
	public void randomMap() throws SlickException{
		
		this.labyrinthCreation();
		for(int i = 0; i < this.nbr_of_collumn; i++){
			for(int j = 0; j < this.nbr_of_line; j++){
				this.walls[i][j].resetVerticeWeight();
			}
		}
		this.graphCreation();
		
	}
	
	public void shakeWall(){
		
		for(int i = 0; i < this.nbr_of_collumn; i++){
			for(int j = 0; j < this.nbr_of_line; j++){
				if(this.walls[i][j].isPushable()) this.walls[i][j].setRotation((int)(Math.random() * 4));
			}
		}
		
		for(int i = 0; i < this.nbr_of_collumn; i++){
			for(int j = 0; j < this.nbr_of_line; j++){
				this.walls[i][j].resetVerticeWeight();
			}
		}
		this.graphCreation();

	}
	
	public Wall insertWall(Wall additional_wall, int mode, int index){
		
		if(mode == INSERER_DEPUIS_BAS || mode == INSERER_DEPUIS_HAUT){
			if(index < this.nbr_of_collumn && index >= 0){
				Wall temp = (mode == INSERER_DEPUIS_BAS) ? this.walls[index][0] : this.walls[index][this.nbr_of_line - 1];
				for(int i = 0; i < this.nbr_of_line; i++){
					System.out.println("plop");
					if(mode == INSERER_DEPUIS_BAS){
						if(i < this.nbr_of_line - 1){
							this.walls[index][i] = this.walls[index][i + 1];
							this.walls[index][i].setCoordinates(index, i);
						}
					}else{
						if(nbr_of_line - 1 - i > 0){
							this.walls[index][nbr_of_line - 1 - i] = this.walls[index][nbr_of_line - 2 - i];
							this.walls[index][nbr_of_line - 1 - i].setCoordinates(index, nbr_of_line - 1 - i);
						}
					}
				}
				if(mode == INSERER_DEPUIS_BAS){
					this.walls[index][this.nbr_of_line - 1] = additional_wall;
					this.walls[index][nbr_of_line - 1].setCoordinates(index, nbr_of_line - 1);
				}else{
					this.walls[index][0] = additional_wall;
					this.walls[index][0].setCoordinates(index, 0);
				}
				additional_wall = temp;
			} 
		}else{
			if(index < this.nbr_of_line && index >= 0){
				Wall temp = (mode == INSERER_DEPUIS_DROITE) ? this.walls[0][index] : this.walls[this.nbr_of_collumn - 1][index];
				for(int i = 0; i < this.nbr_of_collumn; i++){
					if(mode == INSERER_DEPUIS_DROITE){
						if(i < this.nbr_of_collumn - 1){
							this.walls[i][index] = this.walls[i + 1][index];
							this.walls[i][index].setCoordinates(i, index);
						}
					}else{
						if(nbr_of_collumn - 1 - i > 0){
							this.walls[nbr_of_collumn - 1 - i][index] = this.walls[nbr_of_collumn - 2 - i][index];
							this.walls[nbr_of_collumn - 1 - i][index].setCoordinates(nbr_of_collumn - 1 - i, index);
						}
					}
				}
				if(mode == INSERER_DEPUIS_DROITE){
					this.walls[this.nbr_of_line - 1][index] = additional_wall;
					this.walls[this.nbr_of_line - 1][index].setCoordinates(this.nbr_of_line - 1, index);
				}else{
					this.walls[0][index] = additional_wall;
					this.walls[0][index].setCoordinates(0, index);
				}
				additional_wall = temp;
			} 
		}
		
		this.graphCreation();
		
		additional_wall.setCoordinates(nbr_of_collumn, 0);
		return additional_wall;
	}
	
	public Wall getWall(int x, int y){
		return this.walls[x][y];
	}
	
	public int getNumberOfCollumn(){
		return this.nbr_of_collumn;
	}
	
	public int getNumberOfLine(){
		return this.nbr_of_line;
	}
	
	public void resetWeightGraph(){
		
		for(int i = 0; i < this.nbr_of_collumn; i++){
			for(int j = 0; j < this.nbr_of_line; j++){
				this.walls[i][j].resetVerticeWeight();
			}
		}
	}
}