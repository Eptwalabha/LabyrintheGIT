package plateau;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import plateau.nodes.Carte;
import plateau.nodes.CarteDeuxNoeuds;
import plateau.nodes.CarteTroisNoeuds;

public class Plateau extends BasicGameState{

	private Input input;
	
	public static final int ID = 1;
	
	public static final int INSERTION_A_GAUCHE = 0;
	public static final int INSERTION_A_DROITE = 1;
	public static final int INSERTION_EN_HAUT = 2;
	public static final int INSERTION_EN_BAS = 3;
	
	private int taille_x, taille_y;
	private Carte[][] cartes;
	private Carte carte_suplementaire;

	public void setPlateau(int taille_x, int taille_y) throws SlickException{
		
		if(taille_x < 5) taille_x = 5;
		if(taille_y < 5) taille_y = 5;
		if(taille_x % 2 == 0) taille_x++;
		if(taille_y % 2 == 0) taille_y++;
		this.taille_x = taille_x;
		this.taille_y = taille_y;
		
		this.initialisationPlateau();
		this.creationGraph();
		
	}
	
	private void initialisationPlateau() throws SlickException{
		
		this.cartes = new Carte[this.taille_x][this.taille_y];
		
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				
				if((i % 2 == 0) && (j % 2 == 0)){
					if(i == 0){
						if(j == 0) this.cartes[i][j] = new CarteDeuxNoeuds(i, j, CarteDeuxNoeuds.TYPE_L, CarteDeuxNoeuds.L_DROITE_BAS);
						if(j == this.taille_y - 1)	this.cartes[i][j] = new CarteDeuxNoeuds(i, j, CarteDeuxNoeuds.TYPE_L, CarteDeuxNoeuds.L_HAUT_DROITE);
						if(j > 0 && j < this.taille_y - 1)	this.cartes[i][j] = new CarteTroisNoeuds(i, j, CarteTroisNoeuds.VERS_LA_DROITE);
					}else if(i == this.taille_x - 1){
						if(j == 0) this.cartes[i][j] = new CarteDeuxNoeuds(i, j, CarteDeuxNoeuds.TYPE_L, CarteDeuxNoeuds.L_BAS_GAUCHE);
						if(j == this.taille_y - 1)	this.cartes[i][j] = new CarteDeuxNoeuds(i, j, CarteDeuxNoeuds.TYPE_L, CarteDeuxNoeuds.L_GAUCHE_HAUT);
						if(j > 0 && j < this.taille_y - 1)	this.cartes[i][j] = new CarteTroisNoeuds(i, j, CarteTroisNoeuds.VERS_LA_GAUCHE);
					}else{
						if(j == 0) this.cartes[i][j] = new CarteTroisNoeuds(i, j, CarteTroisNoeuds.VERS_LE_BAS);
						if(j == this.taille_y - 1) this.cartes[i][j] = new CarteTroisNoeuds(i, j, CarteTroisNoeuds.VERS_LE_HAUT);
						if(j > 0 && j < this.taille_y - 1) this.cartes[i][j] = new CarteTroisNoeuds(i, j, (int) (Math.random() * 4));
					}
				}else{
					int type = (int) (Math.random() * 2);
					int orientation;
					
					if(type == 0){
						orientation = (int) (Math.random() * 4);
					}else{
						orientation = (int) (Math.random() * 2);
					}
					
					this.cartes[i][j] = new CarteDeuxNoeuds(i, j, type, orientation);
				}
			}
		}
	}
	
	private void creationGraph(){
		
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				
			}
		}
	}
	
	public void insererCarte(int type_insertion, int palier, Carte carte){
		
		
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		this.input = arg0.getInput();
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				this.cartes[i][j].render(10.0f);
			}
		}		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.taille_x; i++){
			for(int j = 0; j < this.taille_y; j++){
				this.cartes[i][j].update(this.input.getMouseX(), this.input.getMouseY());
			}
		}		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
}