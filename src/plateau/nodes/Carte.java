package plateau.nodes;

public abstract class Carte {

	public static final int HAUT = 0;
	public static final int DROITE = 1;
	public static final int BAS = 2;
	public static final int GAUCHE = 3;
	
	protected int pos_x, pos_y;
	protected int angle;
	protected float render_size;
	
	protected boolean selected = false;
	
	public Carte(){
		
	}
	public Carte(int pos_x, int pos_y){
		this.pos_x = pos_x;
		this.pos_y = pos_y;
	}
	
	public int getPosX() {
		return pos_x;
	}
	
	public int getPosY() {
		return pos_y;
	}
	
	public abstract void deplace(int direction);
	
	public abstract int getAngle();
	public abstract void setRenderSize(float render_size);
	public abstract void setNoeud(int position, Carte noeud);
	public abstract int getTypeNoeud();
	public abstract void update(int mx, int my);
	public abstract void render(float taille);
	
	
}
