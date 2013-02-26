package com.labyrinth.game.maze;

import com.labyrinth.game.Origin;
import com.labyrinth.gui.SpriteGUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.labyrinth.utils.graph.GraphVertex;

public class Maze {

	public static final int INSERER_DEPUIS_HAUT = 0;
	public static final int INSERER_DEPUIS_DROITE = 1;
	public static final int INSERER_DEPUIS_BAS = 2;
	public static final int INSERER_DEPUIS_GAUCHE = 3;
	
	public static final int MODE_CROSS = 0;
	public static final int MODE_DOT = 1;

	private Wall[][] walls;
	private Wall additional_wall;
	
	private SpriteGUI textures;
	
	private Origin origin;
	private int nbr_of_collumn, nbr_of_line;
		
	public Maze(Origin origin, SpriteGUI textures){
		this(7, 7, origin, textures);
	}
	
	public Maze(int nbr_of_collumn, int nbr_of_line, Origin origin, SpriteGUI textures){
		
		this.textures = textures;
		this.origin = origin;
		if(nbr_of_collumn < 5) nbr_of_collumn = 5;
		if(nbr_of_collumn % 2 == 0) nbr_of_collumn++;
		if(nbr_of_line < 5) nbr_of_line = 5;
		if(nbr_of_line % 2 == 0) nbr_of_line++;
		
		this.nbr_of_collumn = nbr_of_collumn;
		this.nbr_of_line = nbr_of_line;
		
		this.additional_wall = new Wall((int)(Math.random() * 2), 11, 0, (int)(Math.random() * 2), "", this.textures, true);
		
		this.labyrinthCreation();
		this.graphCreation();
		
	}
		
	private void labyrinthCreation(){

		this.walls = new Wall[this.nbr_of_collumn][this.nbr_of_line];
				
		for(int i = 0; i < this.nbr_of_collumn; i++){
			for(int j = 0; j < this.nbr_of_line; j++){
				
				if((i % 2 == 0) && (j % 2 == 0)){
					if(i == 0){
						if(j == 0) this.walls[i][j] = new Wall(Wall.TYPE_L, i, j, Wall.L_BAS_DROITE, i+"."+j, this.textures, false);
						if(j == this.nbr_of_line - 1) this.walls[i][j] = new Wall(Wall.TYPE_L, i, j, Wall.L_HAUT_DROITE, i+"."+j, this.textures, false);
						if(j > 0 && j < this.nbr_of_line - 1) this.walls[i][j] = new Wall(Wall.TYPE_T, i, j, Wall.T_VERS_LA_DROITE, i+"."+j, this.textures, false);
					}else if(i == this.nbr_of_collumn - 1){
						if(j == 0) this.walls[i][j] = new Wall(Wall.TYPE_L, i, j, Wall.L_BAS_GAUCHE, i+"."+j, this.textures, false);
						if(j == this.nbr_of_line - 1) this.walls[i][j] = new Wall(Wall.TYPE_L, i, j, Wall.L_HAUT_GAUCHE, i+"."+j, this.textures, false);
						if(j > 0 && j < this.nbr_of_line - 1) this.walls[i][j] = new Wall(Wall.TYPE_T, i, j, Wall.T_VERS_LA_GAUCHE, i+"."+j, this.textures, false);
					}else{
						if(j == 0) this.walls[i][j] = new Wall(Wall.TYPE_T, i, j, Wall.T_VERS_LE_BAS, i+"."+j, this.textures, false);
						if(j == this.nbr_of_line - 1) this.walls[i][j] = new Wall(Wall.TYPE_T, i, j, Wall.T_VERS_LE_HAUT, i+"."+j, this.textures, false);
						if(j > 0 && j < this.nbr_of_line - 1) this.walls[i][j] = new Wall(Wall.TYPE_T, i, j, (int)(Math.random() * 4), i+"."+j, this.textures, false);
					}
				}else{
					int type = (int) (Math.random() * 2);
					int orientation = (int) (Math.random() * 4);
					
					type = Wall.TYPE_T;
					
					this.walls[i][j] = new Wall(type, i, j, orientation, i + "." + j, this.textures, true);
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
	
	public GraphVertex pathBetweenAAndB(Wall a, Wall b){
		return a.getShortestPathRecursive(b);
	}
	
	public void render(GameContainer gc, Graphics g){
		
		int coox = this.origin.getOX();
		int cooy = this.origin.getOY();
		int width = this.origin.getWidth();
		for(int i = 0; i < this.walls.length; i++){
			for(int j = 0; j < this.walls[i].length; j++){
				this.walls[i][j].render(coox, cooy, width);
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
	
	public void insertWallHere(int mode, int index){
		
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
					this.walls[index][this.nbr_of_line - 1] = this.additional_wall;
					this.walls[index][nbr_of_line - 1].setCoordinates(index, nbr_of_line - 1);
				}else{
					this.walls[index][0] = this.additional_wall;
					this.walls[index][0].setCoordinates(index, 0);
				}
				this.additional_wall = temp;
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
					this.walls[this.nbr_of_line - 1][index] = this.additional_wall;
					this.walls[this.nbr_of_line - 1][index].setCoordinates(this.nbr_of_line - 1, index);
				}else{
					this.walls[0][index] = this.additional_wall;
					this.walls[0][index].setCoordinates(0, index);
				}
				this.additional_wall = temp;
			} 
		}
		
		this.graphCreation();
		
		this.additional_wall.setCoordinates(nbr_of_collumn, 0);
	}
	
	public Wall getWall(int x, int y){
		return this.walls[x][y];
	}
	
	public Wall getWallAt(int mouse_x, int mouse_y){
		
		int x = (int) ((mouse_x - this.origin.getOX()) / (this.origin.getWidth()));
		int y = (int) ((mouse_y - this.origin.getOY()) / (this.origin.getWidth()));
		
		if(x >= 0 && x < this.nbr_of_collumn && y >=0 && y < this.nbr_of_line){
			return this.walls[x][y];
		}
		
		return null;
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

	public Wall getAdditionalWall(){
		return this.additional_wall;
	}
	
	public void rotateAdditionalWall(){
		this.additional_wall.rotateWall();
	}
	
	public Maze getCopyForAI(){
		
		Maze maze = new Maze(this.nbr_of_collumn, this.nbr_of_line, this.origin, this.textures);
		
		Wall[][] walls_copy = new Wall[this.nbr_of_collumn][this.nbr_of_line];
		for(int i = 0; i < this.nbr_of_collumn; i++){
			for(int j = 0; j < this.nbr_of_line; j++){
				walls_copy[i][j] = walls[i][j].makeCopy();
			}
		}
		
		maze.setMaze(walls_copy, this.additional_wall.makeCopy());
		
		return maze;
	}

	public void setMaze(Wall[][] new_walls, Wall new_additional_wall) {
		this.walls = new_walls;
		this.additional_wall = new_additional_wall;
	}

	public void hooverAt(int mouse_x, int mouse_y, int mode){
		
		int x = (int) ((mouse_x - this.origin.getOX()) / (this.origin.getWidth()));
		int y = (int) ((mouse_y - this.origin.getOY()) / (this.origin.getWidth()));

		if(mode == Maze.MODE_CROSS){
			if(x > 0 && x < this.nbr_of_collumn - 1 && x % 2 != 0){
				for(int i = 0; i < this.nbr_of_line; i++){
					this.walls[x][i].setHoovered(true);
				}
			}
			if(y > 0 && y < this.nbr_of_line - 1 && y % 2 != 0){
				for(int i = 0; i < this.nbr_of_collumn; i++){
					this.walls[i][y].setHoovered(true);
				}
			}
			
		}else{
			if(x >= 0 && x < this.nbr_of_collumn && y >=0 && y < this.nbr_of_line){
				this.walls[x][y].setHoovered(true);
			}
		}
		
		
	}
	

}
