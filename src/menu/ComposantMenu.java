package menu;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class ComposantMenu {

	protected final int id;
	protected int taille_x, taille_y, pos_x, pos_y;
	
	protected List<EcouteurComposantMenu> ecouteurs = new ArrayList<EcouteurComposantMenu>();
	
	public ComposantMenu(int id) {
		this.id = id;
	}
	
	public boolean survoleComposantMenu(int mx, int my){
		return (mx > pos_x && my > pos_y && mx < pos_x + taille_x && my < pos_y + taille_y);
	}
	
	public abstract void render(GameContainer gm, Graphics g);
	public abstract void update(GameContainer gm);
	
	public void ajouterEcouteur(EcouteurComposantMenu e){
		this.ecouteurs.add(e);
	}
	
	public void actionSurComposant(){
		for(EcouteurComposantMenu c : ecouteurs){
			c.action(this.id);
		}
	}
}
