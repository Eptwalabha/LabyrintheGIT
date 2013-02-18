package launcher;

import jeu.Plateau;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Labyrinthe extends StateBasedGame{

	private Plateau jeu;  
	private AppGameContainer container;  

	public Labyrinthe() {
		super("Labyrinthe");
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		if (arg0 instanceof AppGameContainer) {  
			this.container = (AppGameContainer) arg0;  
		}
		
		jeu = new Plateau();
		container.setShowFPS(true);
		this.addState(jeu);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			AppGameContainer app = new AppGameContainer(new Labyrinthe());
			app.setDisplayMode(1024, 760, false);
			app.setTargetFrameRate(120);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
