package jeu;

import jeu.labyrinthe.Labyrinthe;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Plateau extends BasicGameState{

	private Labyrinthe labyrinthe;
	
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		
		this.labyrinthe = new Labyrinthe(11, 11);
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		this.labyrinthe.render(arg0, arg2);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		this.labyrinthe.update(arg0);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
