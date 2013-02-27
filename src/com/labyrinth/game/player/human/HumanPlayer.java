package com.labyrinth.game.player.human;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import com.labyrinth.game.Origin;
import com.labyrinth.game.maze.Wall;
import com.labyrinth.game.player.Player;
import com.labyrinth.game.player.PlayerListener;
import com.labyrinth.gui.SpriteGUI;
import com.labyrinth.menu.wheel.MenuWheel;

public class HumanPlayer extends Player {

	private MenuWheel menu_wheel;
	
	public HumanPlayer(int player_id, String player_name, Origin origin, SpriteGUI textures, Wall start_position, PlayerListener listener, MenuWheel wheel) {
		super(player_id, player_name, origin, textures, start_position, listener);
		this.menu_wheel = wheel;
	}


	@Override
	public void update(GameContainer gc) {
		
		if(this.isPlaying()){
			Input i = gc.getInput();

			if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				this.menu_wheel.setClickOrigin(i.getMouseX(), i.getMouseY());
			}

			if(i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				this.menu_wheel.update(gc);
//				this.playerWantsToMove(i.getMouseX(), i.getMouseY());
			}
			
			if(!i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && this.menu_wheel.isVisible()){
				
				this.menu_wheel.selectIsDone();
				if(this.menu_wheel.hasChoosedADirection()){
					this.playerWantsToPushWall(this.menu_wheel.getPositionX(), this.menu_wheel.getPositionY(), this.menu_wheel.getDestination());
				}
			}
			
			if(i.isKeyPressed(Input.KEY_NUMPAD5)){
				this.playerHasFinishedHisRound();
			}
			
			
			
		}
	}
	
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		super.render(gc, g);
		
		if(this.menu_wheel.isVisible()){
			this.menu_wheel.render(g);
		}
		
	}

}
