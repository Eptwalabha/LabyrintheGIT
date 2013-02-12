package plateau.nodes;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class CarteDeuxNoeuds extends Carte{
	
	public static final int TYPE_I = 0;
	public static final int TYPE_L = 1;

	public static final int I_HORIZONTAL = 0;
	public static final int I_VERTICAL = 1;
	
	public static final int L_HAUT_DROITE = 0;
	public static final int L_DROITE_BAS = 1;
	public static final int L_BAS_GAUCHE = 2;
	public static final int L_GAUCHE_HAUT = 3;

	private Image im_carte;
	private Carte noeud_1;
	private Carte noeud_2;
	private int type_carte;
	
	public CarteDeuxNoeuds(int pos_x, int pos_y, int type_carte, int orientation) throws SlickException{
		super(pos_x, pos_y);
		this.noeud_1 = null;
		this.noeud_2 = null;
		this.type_carte = type_carte;
		if(this.type_carte == 0){
			this.im_carte = new Image("images/murs/mur_I.png").getScaledCopy(64,64);
		}else{
			this.im_carte = new Image("images/murs/mur_L.png").getScaledCopy(64,64);
		}
		this.angle = orientation;
	}

	public int getTypeCarte(){
		return this.type_carte;
	}
	
	public Carte getNoeud(int num_noeud){
		
		switch (num_noeud) {
		case 1:
			return this.noeud_1;
		case 2:
			return this.noeud_2;
		default:
			return this.noeud_1;
		}
		
	}
	public void rotateCarte(){
		
		if(this.type_carte == TYPE_L)
			this.angle = (this.angle + 1) % 4;
		
		if(this.type_carte == TYPE_I)
			this.angle = (this.angle + 1) % 2;
		
	}
	
	public void setNodes(Carte noeud_1, Carte noeud_2){
		this.noeud_1 = noeud_1;
		this.noeud_2 = noeud_2;
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
		default:
			break;
		}
	}

	@Override
	public int getTypeNoeud() {
		return 2;
	}

	@Override
	public void setRenderSize(float render_size) {
		this.render_size = render_size;
	}

}
