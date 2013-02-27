package com.labyrinth.launcher;

import com.labyrinth.game.GameBoard;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Launcher extends StateBasedGame{

	private GameBoard game;  
	private AppGameContainer container;  

	public Launcher() {
		super("Labyrinthe");
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		if (arg0 instanceof AppGameContainer) {  
			this.container = (AppGameContainer) arg0;  
		}
		
		game = new GameBoard();
		container.setShowFPS(true);
		this.addState(game);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			AppGameContainer app = new AppGameContainer(new Launcher());
			app.setDisplayMode(1024, 768, false);
			app.setTargetFrameRate(120);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
