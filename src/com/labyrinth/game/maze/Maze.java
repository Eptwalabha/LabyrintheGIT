package com.labyrinth.game.maze;

import com.labyrinth.game.Origin;
import com.labyrinth.gui.SpriteGUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.labyrinth.utils.graph.GraphVertex;

public class Maze {

	public static final int INSERT_FROM_BOTTOM = 0;
	public static final int INSERT_FROM_LEFT = 1;
	public static final int INSERT_FROM_TOP = 2;
	public static final int INSERT_FROM_RIGHT = 3;

	
	public static final int MODE_CROSS = 0;
	public static final int MODE_DOT = 1;

	private Wall[][] walls;
	private Wall additional_wall;
	
	private SpriteGUI textures;
	
	private Origin origin;
	private int nbr_of_row, nbr_of_line;
	
	private boolean wall_moving = false;
	private int wall_moving_direction = -1;
	private int wall_moving_index;
	private long wall_moving_start_time;
		
	public Maze(Origin origin, SpriteGUI textures){
		this(7, 7, origin, textures);
	}
	
	public Maze(int nbr_of_row, int nbr_of_line, Origin origin, SpriteGUI textures){
		
		this.textures = textures;
		this.origin = origin;
		if(nbr_of_row < 5) nbr_of_row = 5;
		if(nbr_of_row % 2 == 0) nbr_of_row++;
		if(nbr_of_line < 5) nbr_of_line = 5;
		if(nbr_of_line % 2 == 0) nbr_of_line++;
		
		this.nbr_of_row = nbr_of_row;
		this.nbr_of_line = nbr_of_line;
		
		this.additional_wall = new Wall((int)(Math.random() * 2), 11, 0, (int)(Math.random() * 2), "", this.textures, true);
		
		this.labyrinthCreation();
		this.graphCreation();
		
	}
		
	private void labyrinthCreation(){

		this.walls = new Wall[this.nbr_of_row][this.nbr_of_line];
				
		for(int i = 0; i < this.nbr_of_row; i++){
			for(int j = 0; j < this.nbr_of_line; j++){
				
				if((i % 2 == 0) && (j % 2 == 0)){
					if(i == 0){
						if(j == 0) this.walls[i][j] = new Wall(Wall.TYPE_L, i, j, Wall.L_BAS_DROITE, i+"."+j, this.textures, false);
						if(j == this.nbr_of_line - 1) this.walls[i][j] = new Wall(Wall.TYPE_L, i, j, Wall.L_HAUT_DROITE, i+"."+j, this.textures, false);
						if(j > 0 && j < this.nbr_of_line - 1) this.walls[i][j] = new Wall(Wall.TYPE_T, i, j, Wall.T_VERS_LA_DROITE, i+"."+j, this.textures, false);
					}else if(i == this.nbr_of_row - 1){
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
		
		for(int i = 0; i < this.nbr_of_row; i++){
			for(int j = 0; j < this.nbr_of_line; j++){
				
				Wall nord = (j > 0) ? this.walls[i][j - 1] : null;
				Wall est = (i < this.nbr_of_row - 1) ? this.walls[i + 1][j] : null;
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
		int pos_x, pos_y;
		int move_x = 0;
		int move_y = 0;
		int width = this.origin.getWidth();
		
		if(this.wall_moving){
			int dist = (int) ((((this.wall_moving_start_time + 1100) - System.currentTimeMillis()) / 1000f) * width);
			if(dist > width) dist = width;
			switch (this.wall_moving_direction) {
			case 0:
				move_y = 0 - dist;
				break;
			case 1:
				move_x = dist;
				break;
			case 2:
				move_y = dist;
				break;
			case 3:
				move_x = 0 - dist;
				break;
			}
		}

		if(!this.wall_moving || this.wall_moving_direction == 0 || this.wall_moving_direction == 2){
			for(int i = 0; i < this.walls.length; i++){
				for(int j = 0; j < this.walls[i].length; j++){
					
					pos_x = i * width + coox;
					pos_y = j * width + cooy;
					
					if(this.wall_moving && this.wall_moving_index == i){
						this.walls[i][j].render((int) (pos_x - move_x + (5 - Math.random() * 10)), (int) (pos_y - move_y + (2 - Math.random() * 4)), width);
					}else{
						this.walls[i][j].render(pos_x, pos_y, width);
					}
				}
			}
		}else{
			
			for(int j = 0; j < this.walls[0].length; j++){
				for(int i = 0; i < this.walls.length; i++){
					
					pos_x = i * width + coox;
					pos_y = j * width + cooy;
					
					if(this.wall_moving_index == j){
						this.walls[i][j].render((int) (pos_x - move_x + (5 - Math.random() * 10)), (int) (pos_y - move_y + (2 - Math.random() * 4)), width);
					}else{
						this.walls[i][j].render(pos_x, pos_y, width);
					}
				}
			}

		}
		
		if((this.wall_moving_start_time + 1100) - System.currentTimeMillis() <= 0){
			this.wall_moving = false;
		}
	}
	
	public void update(GameContainer gc){
		
	}
	
	public void randomMap() throws SlickException{
		
		this.labyrinthCreation();
		for(int i = 0; i < this.nbr_of_row; i++){
			for(int j = 0; j < this.nbr_of_line; j++){
				this.walls[i][j].resetVerticeWeight();
			}
		}
		this.graphCreation();
		
	}
	
	public void shakeWall(){
		
		for(int i = 0; i < this.nbr_of_row; i++){
			for(int j = 0; j < this.nbr_of_line; j++){
				if(this.walls[i][j].isPushable()) this.walls[i][j].setRotation((int)(Math.random() * 4));
			}
		}
		
		for(int i = 0; i < this.nbr_of_row; i++){
			for(int j = 0; j < this.nbr_of_line; j++){
				this.walls[i][j].resetVerticeWeight();
			}
		}
		this.graphCreation();

	}
	
	public void insertWallHere(int row, int line, int direction){
				
		if(direction == INSERT_FROM_BOTTOM || direction == INSERT_FROM_TOP){
			if(row < this.nbr_of_row && row >= 0 && row % 2 != 0){
				Wall temp = (direction == INSERT_FROM_BOTTOM) ? this.walls[row][0] : this.walls[row][this.nbr_of_line - 1];
				for(int i = 0; i < this.nbr_of_line; i++){
					if(direction == INSERT_FROM_BOTTOM){
						if(i < this.nbr_of_line - 1){
							this.walls[row][i] = this.walls[row][i + 1];
							this.walls[row][i].setCoordinates(row, i);
						}
					}else{
						if(nbr_of_line - 1 - i > 0){
							this.walls[row][nbr_of_line - 1 - i] = this.walls[row][nbr_of_line - 2 - i];
							this.walls[row][nbr_of_line - 1 - i].setCoordinates(row, nbr_of_line - 1 - i);
						}
					}
				}
				if(direction == INSERT_FROM_BOTTOM){
					this.walls[row][this.nbr_of_line - 1] = this.additional_wall;
					this.walls[row][nbr_of_line - 1].setCoordinates(row, nbr_of_line - 1);
				}else{
					this.walls[row][0] = this.additional_wall;
					this.walls[row][0].setCoordinates(row, 0);
				}
				this.additional_wall = temp;
				this.wall_moving = true;
				this.wall_moving_start_time = System.currentTimeMillis();
				this.wall_moving_direction = direction;
				this.wall_moving_index = row;
			} 
		}else{
			if(line < this.nbr_of_line && line >= 0  && line % 2 != 0){
				Wall temp = (direction == INSERT_FROM_RIGHT) ? this.walls[0][line] : this.walls[this.nbr_of_row - 1][line];
				for(int i = 0; i < this.nbr_of_row; i++){
					if(direction == INSERT_FROM_RIGHT){
						if(i < this.nbr_of_row - 1){
							this.walls[i][line] = this.walls[i + 1][line];
							this.walls[i][line].setCoordinates(i, line);
						}
					}else{
						if(nbr_of_row - 1 - i > 0){
							this.walls[nbr_of_row - 1 - i][line] = this.walls[nbr_of_row - 2 - i][line];
							this.walls[nbr_of_row - 1 - i][line].setCoordinates(nbr_of_row - 1 - i, line);
						}
					}
				}
				if(direction == INSERT_FROM_RIGHT){
					this.walls[this.nbr_of_line - 1][line] = this.additional_wall;
					this.walls[this.nbr_of_line - 1][line].setCoordinates(this.nbr_of_line - 1, line);
				}else{
					this.walls[0][line] = this.additional_wall;
					this.walls[0][line].setCoordinates(0, line);
				}
				this.additional_wall = temp;
				this.wall_moving = true;
				this.wall_moving_start_time = System.currentTimeMillis();
				this.wall_moving_direction = direction;
				this.wall_moving_index = line;
			} 
		}
		
		this.graphCreation();
		
		this.additional_wall.setCoordinates(nbr_of_row, 0);
	}

	public Wall getWall(int x, int y){
		return this.walls[x][y];
	}
	
	public Wall getWallAt(int row, int line){
		
		if(row >= 0 && row < this.nbr_of_row && line >=0 && line < this.nbr_of_line){
			return this.walls[row][line];
		}
		
		return null;
	}
	
	public int getNumberOfCollumn(){
		return this.nbr_of_row;
	}
	
	public int getNumberOfLine(){
		return this.nbr_of_line;
	}
	
	public void resetWeightGraph(){
		
		for(int i = 0; i < this.nbr_of_row; i++){
			for(int j = 0; j < this.nbr_of_line; j++){
				this.walls[i][j].resetVerticeWeight();
			}
		}
	}

	public Wall getAdditionalWall(){
		return this.additional_wall;
	}
	
	public void rotateAdditionalWall(int mode){
		this.additional_wall.rotateWall(mode);
	}
	
	public Maze getCopyForAI(){
		
		Maze maze = new Maze(this.nbr_of_row, this.nbr_of_line, this.origin, this.textures);
		
		Wall[][] walls_copy = new Wall[this.nbr_of_row][this.nbr_of_line];
		for(int i = 0; i < this.nbr_of_row; i++){
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
			if(x > 0 && x < this.nbr_of_row - 1 && x % 2 != 0){
				for(int i = 0; i < this.nbr_of_line; i++){
					this.walls[x][i].setHoovered(true);
				}
			}
			if(y > 0 && y < this.nbr_of_line - 1 && y % 2 != 0){
				for(int i = 0; i < this.nbr_of_row; i++){
					this.walls[i][y].setHoovered(true);
				}
			}
			
		}else{
			if(x >= 0 && x < this.nbr_of_row && y >=0 && y < this.nbr_of_line){
				this.walls[x][y].setHoovered(true);
			}
		}
		
		
	}

	public boolean isALegalMove(int row, int line, int direction){
	
		if(direction % 2 > 0){
			if(row < 0 || row >= this.nbr_of_row || line % 2 == 0) return false;
		}else{
			if(line < 0 || line >= this.nbr_of_line || row % 2 == 0) return false;
		}
		
		return true;
	}
	
	public int getLineNumber(int mouse_y){
		return (int) ((mouse_y - this.origin.getOY()) / (this.origin.getWidth()));
	}
	
	public int getRowNumber(int mouse_x){
		return (int) ((mouse_x - this.origin.getOX()) / (this.origin.getWidth()));
	}
}
