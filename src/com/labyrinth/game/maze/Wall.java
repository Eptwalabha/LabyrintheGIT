package com.labyrinth.game.maze;

import com.labyrinth.gui.SpriteGUI;
import com.labyrinth.utils.graph.Coordinate2D;
import com.labyrinth.utils.graph.GraphVertex;

public class Wall extends GraphVertex{

	public static final int TYPE_I = 0;
	public static final int TYPE_L = 1;
	public static final int TYPE_T = 2;

	public static final int I_HORIZONTAL = 0;
	public static final int I_VERTICAL = 1;
	
	public static final int L_HAUT_DROITE = 0;
	public static final int L_BAS_DROITE = 1;
	public static final int L_BAS_GAUCHE = 2;
	public static final int L_HAUT_GAUCHE = 3;
	
	public static final int T_VERS_LE_HAUT = 0;
	public static final int T_VERS_LA_DROITE = 1;
	public static final int T_VERS_LE_BAS = 2;
	public static final int T_VERS_LA_GAUCHE = 3;

	public static final int TO_THE_LEFT = 0;
	public static final int TO_THE_RIGHT = 1;

	private SpriteGUI textures;
	
	private float scale = 1.0f;
	private int coordinates_x, coordinates_y;
	private int pos_x = 0, pos_y = 0;
	private int width = 0;
	
	private int angle;
	private int wall_type;
	
	private boolean pushable = true;
	private boolean hoover = false;
	
	public Wall(int type_mur, int coordonnee_x,
				int coordonnee_y, int orientation,
				String name, SpriteGUI textures, boolean mobile){
	
		super.setName(name);
		this.wall_type = type_mur;
		this.coordinates_x = coordonnee_x;
		this.coordinates_y = coordonnee_y;
		this.angle = orientation;
		this.pushable = mobile;
		
		this.textures = textures;
	}
	
	public void setCoordinates(int coordonnee_x, int coordonnee_y){
	
		this.coordinates_x = coordonnee_x;
		this.coordinates_y = coordonnee_y;
		
	}
	
	public int getCoordinateX(){
		return this.coordinates_x;
	}
	
	public int getCoordinateY(){
		return this.coordinates_y;
	}
	
	public Coordinate2D getCoordinate2D(){
		return new Coordinate2D(this.coordinates_x, this.coordinates_y);
	}
	
	public void setPosition(int x, int y){
		this.pos_x = x;
		this.pos_y = y;
	}
	
	public int getPositionX(){
		return this.pos_x;
	}
	
	public int getPositionY(){
		return this.pos_y;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getAngle(){
		return this.angle;
	}
	
	public Wall makeCopy(){
		Wall copy = new Wall(this.wall_type, this.coordinates_x, this.coordinates_y, this.angle, "copy of " + this.getName(), this.textures, this.pushable);
		return copy;
	}
	
	public void setScale(float scale){
		this.scale = scale;
	}
	
	public void setRenderSize(float render_size){
//		this.render_size = render_size;
	}
	
	public void setVertice(Wall vertex){
		this.setBrother(vertex.getBrother());
		this.setSon((Wall) vertex.getSon());
	}
	
	public int getWallType(){
		return this.wall_type;
	}
	
	public void setHoovered(boolean hoover){
		this.hoover = hoover;
	}
	
	public void update(int mx, int my){
		
	}

	public void render(int pos_x, int pos_y, int width){

		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.width = width;
		
		if(this.hoover){
			this.textures.getSpriteAt(this.angle + 4, this.wall_type, width).draw(pos_x, pos_y);
		}else{
			if(this.pushable){
				this.textures.getSpriteAt(this.angle, this.wall_type, width).draw(pos_x, pos_y);
			}else{
				this.textures.getSpriteAt(this.angle + 8, this.wall_type, width).draw(pos_x, pos_y);
			}
		}
		
		this.hoover = false;
	}
	public void rotateWall(int mode){
		
		if(mode == Wall.TO_THE_RIGHT){
			this.angle = (this.angle + 1) % 4;
		}else{
			this.angle = (this.angle - 1) % 4;
		}
		
	}

	public void setRotation(int angle){
		
		this.angle = angle % 4;
		
	}

	public void resetConnexions(){
		this.setBrother(null);
		this.setSon(null);
	}

	
	public void linkWalls(Wall nord, Wall est, Wall sud, Wall ouest){
		
		this.resetConnexions();
		
		Wall[] liste = new Wall[4];
		liste[0] = nord;
		liste[1] = est;
		liste[2] = sud;
		liste[3] = ouest;
		
		int angle_1, angle_2;
		
		switch (this.wall_type) {
		case 0:
			angle_1 = (0 + this.angle) % 4;
			angle_2 = (2 + this.angle) % 4;
			if(liste[angle_1] != null) if(liste[angle_1].isLinkable(angle_1)){
				this.stack(liste[angle_1]);
			}
			if(liste[angle_2] != null) if(liste[angle_2].isLinkable(angle_2)){
				this.stack(liste[angle_2]);
			}
			break;
		case 1:
			angle_1 = (0 + this.angle) % 4;
			angle_2 = (1 + this.angle) % 4;
			if(liste[angle_1] != null) if(liste[angle_1].isLinkable(angle_1)){
				this.stack(liste[angle_1]);
			}
			if(liste[angle_2] != null) if(liste[angle_2].isLinkable(angle_2)){
				this.stack(liste[angle_2]);
			}
			break;
		case 2:
			angle_1 = (0 + this.angle) % 4;
			angle_2 = (1 + this.angle) % 4;
			int angle_3 = (3 + this.angle) % 4;
			if(liste[angle_1] != null) if(liste[angle_1].isLinkable(angle_1)){
				this.stack(liste[angle_1]);
			}
			if(liste[angle_2] != null) if(liste[angle_2].isLinkable(angle_2)){
				this.stack(liste[angle_2]);
			}
			if(liste[angle_3] != null) if(liste[angle_3].isLinkable(angle_3)){
				this.stack(liste[angle_3]);
			}
			break;
		default:
			break;
		}
		
	}
	
	public boolean isLinkable(int source){
		
		boolean[] connectable = new boolean[4];
		
		switch (this.wall_type) {
		case 0:
			connectable[(0 + this.angle) % 4] = true;
			connectable[(1 + this.angle) % 4] = false;
			connectable[(2 + this.angle) % 4] = true;
			connectable[(3 + this.angle) % 4] = false;
			break;
		case 1:
			connectable[(0 + this.angle) % 4] = true;
			connectable[(1 + this.angle) % 4] = true;
			connectable[(2 + this.angle) % 4] = false;
			connectable[(3 + this.angle) % 4] = false;
			break;
		case 2:
			connectable[(0 + this.angle) % 4] = true;
			connectable[(1 + this.angle) % 4] = true;
			connectable[(2 + this.angle) % 4] = false;
			connectable[(3 + this.angle) % 4] = true;
			break;
		default:
			break;
		}
		
		return connectable[(source + 2) % 4];
	}

	public boolean isPushable() {
		return this.pushable;
	}

	public float getScale() {
		return this.scale;
	}
	
}
