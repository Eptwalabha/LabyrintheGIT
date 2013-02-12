package plateau.nodes;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class CarteTroisNoeuds extends Carte{
	
	public static final int VERS_LE_HAUT = 0;
	public static final int VERS_LA_DROITE = 1;
	public static final int VERS_LE_BAS = 2;
	public static final int VERS_LA_GAUCHE = 3;
	
	private Carte noeud_1;
	private Carte noeud_2;
	private Carte noeud_3;
	
	private Image im_carte;
	
	public CarteTroisNoeuds(int pos_x, int pos_y, int orientation) throws SlickException{
		super(pos_x, pos_y);
		this.noeud_1 = null;
		this.noeud_2 = null;
		this.noeud_3 = null;
		this.angle = orientation;
		this.im_carte = new Image("images/murs/mur_T.png").getScaledCopy(64,64);
		
	}
	
	public Carte getNoeud(int num_noeud){
		
		switch (num_noeud) {
		case 2:
			return this.noeud_2;
		case 3:
			return this.noeud_3;
		default:
			return this.noeud_1;
		}
		
	}
	
	public void rotateCarte(){
		this.angle = (this.angle + 1) % 4;
	}

	@Override
	public void deplace(int direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int mx, int my) {
		// TODO Auto-generated method stub
		this.selected = false;
		if(mx >= this.pos_x * 64 && mx < (this.pos_x + 1) * 64 && my >= this.pos_y * 64 && my < (this.pos_y + 1) * 64) this.selected = true;
	}

	@Override
	public void render(float taille) {
		// TODO Auto-generated method stub
		this.im_carte.setRotation(this.angle * 90);
		if(this.selected){
			this.im_carte.getScaledCopy(70, 70).draw(this.pos_x * 64 - 3, this.pos_y * 64 - 3);
		}else{
			this.im_carte.draw(this.pos_x * 64, this.pos_y * 64);			
		}
	}

	@Override
	public int getAngle() {
		return this.angle;
	}

	@Override
	public void setNoeud(int position, Carte noeud) {
		switch (position) {
		case 1:
			this.noeud_1 = noeud;
			break;
		case 2:
			this.noeud_2 = noeud;
			break;
		case 3:
			this.noeud_3 = noeud;
			break;
		default:
			break;
		}
	}

	@Override
	public int getTypeNoeud() {
		return 3;
	}

	@Override
	public void setRenderSize(float render_size) {
		this.render_size = render_size;
	}
	
	
}
