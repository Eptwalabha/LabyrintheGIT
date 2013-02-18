package jeu;

public class Origine{

	private int position_x =  0, position_y = 0;
	private float unite_x = 1.0f, unite_y = 1.0f;
	
	public void setPositionOrigine(int x, int y){
		this.position_x = x;
		this.position_y = y;
	}
	
	public void setUniteeOrigine(float x, float y){
		if(x < 0.2f) x = 0.2f;
		if(y < 0.2f) y = 0.2f;
		
		this.unite_x = x;
		this.unite_y = y;
	}
	
	public int getOX(){
		return this.position_x;
	}
	
	public int getOY(){
		return this.position_y;
	}

	public float getTailleX(){
		return this.unite_x;
	}
	
	public float getTailleY(){
		return this.unite_y;
	}

}
