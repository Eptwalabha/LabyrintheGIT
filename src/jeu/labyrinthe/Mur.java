package jeu.labyrinthe;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

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

	private int pos_x, pos_y;
	private int angle;
	private int type_mur;
	
	private Image im_mur;
//	private float render_size;
	private boolean hoover = false;
	private boolean mobile = true;
	
	public Mur(int type_mur, int pos_x, int pos_y, int orientation, String name, boolean mobile) throws SlickException{
	
		super.setName(name);
		this.type_mur = type_mur;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.angle = orientation;
		this.mobile = mobile;
		String url = "";
		
		switch (this.type_mur) {
		case 0:
			url = "images/murs/mur_I";
			break;
		case 1:
			url = "images/murs/mur_L";
			break;
		case 2:
			url = "images/murs/mur_T";
			break;			
		default:
			this.type_mur = 0;
			url = "images/murs/mur_I";
			break;
		}
		
		url += (!mobile) ? "_im.png" : ".png";
		this.im_mur = new Image(url).getScaledCopy(20, 20);
		
	}
	
	public int getPosX(){
		return this.pos_x;
	}
	
	public int getPosY(){
		return this.pos_y;
	}
	
	public int getAngle(){
		return this.angle;
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
	
	public void render(float taille){

		this.im_mur.setRotation(this.angle * 90);
		if(this.hoover){
			this.im_mur.getScaledCopy(70, 70).draw(this.pos_x * 64 - 3, this.pos_y * 64 - 3);
		}else{
			this.im_mur.draw(this.pos_x * 20, this.pos_y * 20);			
		}

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
		
//		System.out.println("pour le mur: " + this.pos_x + "; " + this.pos_y);
//		
//		System.out.println("Liste des murs trouvés:");
//		if(nord != null){
//			System.out.println("nord  : " + nord.getPosX() + "." + nord.getPosY());
//		}else{
//			System.out.println("nord  : null;");
//		}
//		if(est != null){
//			System.out.println("est   : " + est.getPosX() + "." + est.getPosY());
//		}else{
//			System.out.println("est   : null;");
//		}
//		if(sud != null){
//			System.out.println("sud   : " + sud.getPosX() + "." + sud.getPosY());
//		}else{
//			System.out.println("sud   : null;");
//		}
//		if(ouest != null){
//			System.out.println("ouest : " + ouest.getPosX() + "." + ouest.getPosY());
//		}else{
//			System.out.println("ouest : null;");
//		}
		
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
//				System.out.println("connexion " + this.getName() + " -> " + liste[angle_1].getName());
				this.stack(liste[angle_1]);
			}
			if(liste[angle_2] != null) if(liste[angle_2].estConnectable(angle_2)){
//				System.out.println("connexion " + this.getName() + " -> " + liste[angle_2].getName());
				this.stack(liste[angle_2]);
			}
			break;
		case 1:
			angle_1 = (0 + this.angle) % 4;
			angle_2 = (1 + this.angle) % 4;
			if(liste[angle_1] != null) if(liste[angle_1].estConnectable(angle_1)){
//				System.out.println("connexion " + this.getName() + " -> " + liste[angle_1].getName());
				this.stack(liste[angle_1]);
			}
			if(liste[angle_2] != null) if(liste[angle_2].estConnectable(angle_2)){
//				System.out.println("connexion " + this.getName() + " -> " + liste[angle_2].getName());
				this.stack(liste[angle_2]);
			}
			break;
		case 2:
			angle_1 = (0 + this.angle) % 4;
			angle_2 = (1 + this.angle) % 4;
			int angle_3 = (3 + this.angle) % 4;
			if(liste[angle_1] != null) if(liste[angle_1].estConnectable(angle_1)){
//				System.out.println("connexion " + this.getName() + " -> " + liste[angle_1].getName());
				this.stack(liste[angle_1]);
			}
			if(liste[angle_2] != null) if(liste[angle_2].estConnectable(angle_2)){
//				System.out.println("connexion " + this.getName() + " -> " + liste[angle_2].getName());
				this.stack(liste[angle_2]);
			}
			if(liste[angle_3] != null) if(liste[angle_3].estConnectable(angle_3)){
//				System.out.println("connexion " + this.getName() + " -> " + liste[angle_3].getName());
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
	
}
