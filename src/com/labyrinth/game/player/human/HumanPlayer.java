package com.labyrinth.game.player.human;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

import com.labyrinth.game.Origin;
import com.labyrinth.game.maze.Wall;
import com.labyrinth.game.player.Player;
import com.labyrinth.game.player.PlayerEventListener;
import com.labyrinth.gui.SpriteGUI;

public class HumanPlayer extends Player implements MouseListener {
	
	public HumanPlayer(int player_id, String player_name, Origin origin, SpriteGUI textures, Wall start_position, PlayerEventListener listener) {
		super(player_id, player_name, origin, textures, start_position, listener);
	}

	/**
	 * Les boutons by-pass le GameContainer!!!!!!
	 */
	@Override
	public void update(GameContainer gc) {
		Input i = gc.getInput();

		if(i.isKeyPressed(Input.KEY_NUMPAD5)){
			this.playerHasFinishedHisRound();
		}
		
	}
	
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		super.render(gc, g);
	}


	@Override
	public void inputEnded() {}


	@Override
	public void inputStarted() {}


	@Override
	public boolean isAcceptingInput() {
		return true;
	}


	@Override
	public void setInput(Input arg0) {}


	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {}


	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		if(this.getStep() > 0){
			for(PlayerEventListener p : this.listeners){
				p.eventMouseDrag(this.getPlayerId(), arg2, arg3);
			}
		}
	}

	
	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {}


	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		
		if(this.getStep() > 0 && arg0 == Input.MOUSE_LEFT_BUTTON){
			for(PlayerEventListener p : this.listeners){
				p.eventMousePressed(this.getPlayerId(), arg1, arg2);
			}
		}

	}


	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		if(this.getStep() > 0 && arg0 == Input.MOUSE_LEFT_BUTTON){
			for(PlayerEventListener p : this.listeners){
				p.eventMouseReleased(this.getPlayerId(), arg1, arg2);
			}
		}		
	}


	@Override
	public void mouseWheelMoved(int arg0) {}


	@Override
	public int getTypeOfPlayer() {
		return Player.HUMAN;
	}

}
