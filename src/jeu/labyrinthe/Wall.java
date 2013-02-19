package jeu.labyrinthe;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import utils.graph.GraphVertice;

public class Wall extends GraphVertice{

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

	private SpriteSheet textures;
	
	private float scale = 1.0f;
	private int coordinates_x, coordinates_y;
	private int position_x, position_y;
	private int angle;
	private int wall_type;
	
//	private boolean hoover = false;
	private boolean pushable = true;
	
	public Wall(int type_mur, int coordonnee_x, int coordonnee_y, int orientation, String name, boolean mobile) throws SlickException{
	
		super.setName(name);
		this.wall_type = type_mur;
		this.coordinates_x = coordonnee_x;
		this.coordinates_y = coordonnee_y;
		this.angle = orientation;
		this.pushable = mobile;
				
		Image image = new Image("images/murs/murs3D.png");
		image.setFilter(Image.FILTER_NEAREST);
		this.textures = new SpriteSheet(image, 64, 74);
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
	
	public int getRelativePositionX(){
		return this.position_x;
	}
	
	public int getRelativePositionY(){
		return this.position_y;
	}
	
	
	public int getAngle(){
		return this.angle;
	}
	
	public void setScale(float scale){
		this.scale = scale;
	}
	
	public void setRenderSize(float render_size){
//		this.render_size = render_size;
	}
	
	public void setVertice(Wall vertice){
		this.setBrother(vertice.getVerticeBrother());
		this.setSon((Wall) vertice.getVerticeSon());
	}
	
	public int getWallType(){
		return this.wall_type;
	}
	
	public void update(int mx, int my){
		
	}
	
	public void render(int posx, int posy, float unix, float uniy){

		if(this.pushable){
			this.textures.getSprite(this.angle, this.wall_type).getScaledCopy(unix).draw(this.coordinates_x * 64 * unix + posx, this.coordinates_y * 64 * unix + posy);
		}else{
			this.textures.getSprite(this.angle + 8, this.wall_type).getScaledCopy(unix).draw(this.coordinates_x * 64 * unix + posx, this.coordinates_y * 64 * unix + posy);
		}
//		System.out.println(posx + "-" + posy);
		
	}

	public void rotateWall(){
		
		this.angle = (this.angle + 1) % 4;
		
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
