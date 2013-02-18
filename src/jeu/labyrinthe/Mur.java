package jeu.labyrinthe;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import utils.graph.Sommet;

public class Mur extends Sommet{

	public static final int TYPE_I = 0;
	public static final int TYPE_L = 1;
	public static final int TYPE_T = 2;

	public static final int I_HORIZONTAL = 0;
	public static final int I_VERTICAL = 1;
	
	public static final int L_HAUT_DROITE = 0;
	public static final int L_DROITE_BAS = 1;
	public static final int L_BAS_GAUCHE = 2;
	public static final int L_GAUCHE_HAUT = 3;
	
	public static final int T_VERS_LE_HAUT = 0;
	public static final int T_VERS_LA_DROITE = 1;
	public static final int T_VERS_LE_BAS = 2;
	public static final int T_VERS_LA_GAUCHE = 3;

	private SpriteSheet textures;
	
	private float scale = 1.0f;
	private int coordonnee_x, coordonnee_y;
	private int position_x, position_y;
	private int angle;
	private int type_mur;
	
//	private boolean hoover = false;
	private boolean mobile = true;
	
	public Mur(int type_mur, int coordonnee_x, int coordonnee_y, int orientation, String name, boolean mobile) throws SlickException{
	
		super.setName(name);
		this.type_mur = type_mur;
		this.coordonnee_x = coordonnee_x;
		this.coordonnee_y = coordonnee_y;
		this.angle = orientation;
		this.mobile = mobile;
				
		Image image = new Image("images/murs/murs3D.png");
		image.setFilter(Image.FILTER_NEAREST);
		this.textures = new SpriteSheet(image, 64, 74);
	}
	
	public void setCoordonnees(int coordonnee_x, int coordonnee_y){
	
		this.coordonnee_x = coordonnee_x;
		this.coordonnee_y = coordonnee_y;
		
	}
	
	public int getCoordonneeX(){
		return this.coordonnee_x;
	}
	
	public int getCoordonneeY(){
		return this.coordonnee_y;
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
	
	public void setSommet(Mur sommet){
		this.setFrere(sommet.getSommetFrere());
		this.setFils((Mur) sommet.getSommetFils());
	}
	
	public int getTypeMur(){
		return this.type_mur;
	}
	
	public void update(int mx, int my){
		
	}
	
	public void render(int posx, int posy, float unix, float uniy){

		if(this.mobile){
			this.textures.getSprite(this.angle, this.type_mur).getScaledCopy(unix).draw(this.coordonnee_x * 64 * unix + posx, this.coordonnee_y * 64 * unix + posy);
		}else{
			this.textures.getSprite(this.angle + 8, this.type_mur).getScaledCopy(unix).draw(this.coordonnee_x * 64 * unix + posx, this.coordonnee_y * 64 * unix + posy);
		}
//		System.out.println(posx + "-" + posy);
		
	}

	public void rotateCarte(){
		
		this.angle = (this.angle + 1) % 4;
		
	}

	public void setRotation(int angle){
		
		this.angle = angle % 4;
		
	}

	public void resetConnexions(){
		this.setFrere(null);
		this.setFils(null);
	}

	
	public void connecterMurs(Mur nord, Mur est, Mur sud, Mur ouest){
		
		this.resetConnexions();
		
		Mur[] liste = new Mur[4];
		liste[0] = nord;
		liste[1] = est;
		liste[2] = sud;
		liste[3] = ouest;
		
		int angle_1, angle_2;
		
		switch (this.type_mur) {
		case 0:
			angle_1 = (0 + this.angle) % 4;
			angle_2 = (2 + this.angle) % 4;
			if(liste[angle_1] != null) if(liste[angle_1].estConnectable(angle_1)){
				this.stack(liste[angle_1]);
			}
			if(liste[angle_2] != null) if(liste[angle_2].estConnectable(angle_2)){
				this.stack(liste[angle_2]);
			}
			break;
		case 1:
			angle_1 = (0 + this.angle) % 4;
			angle_2 = (1 + this.angle) % 4;
			if(liste[angle_1] != null) if(liste[angle_1].estConnectable(angle_1)){
				this.stack(liste[angle_1]);
			}
			if(liste[angle_2] != null) if(liste[angle_2].estConnectable(angle_2)){
				this.stack(liste[angle_2]);
			}
			break;
		case 2:
			angle_1 = (0 + this.angle) % 4;
			angle_2 = (1 + this.angle) % 4;
			int angle_3 = (3 + this.angle) % 4;
			if(liste[angle_1] != null) if(liste[angle_1].estConnectable(angle_1)){
				this.stack(liste[angle_1]);
			}
			if(liste[angle_2] != null) if(liste[angle_2].estConnectable(angle_2)){
				this.stack(liste[angle_2]);
			}
			if(liste[angle_3] != null) if(liste[angle_3].estConnectable(angle_3)){
				this.stack(liste[angle_3]);
			}
			break;
		default:
			break;
		}
		
	}
	
	public boolean estConnectable(int source){
		
		boolean[] connectable = new boolean[4];
		
		switch (this.type_mur) {
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

	public boolean estMobile() {
		return this.mobile;
	}

	public float getScale() {
		return this.scale;
	}
	
}
