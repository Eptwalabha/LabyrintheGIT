package com.labyrinth.game.player.human;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import com.labyrinth.game.Origin;
import com.labyrinth.game.maze.Wall;
import com.labyrinth.game.player.Player;
import com.labyrinth.game.player.PlayerListener;
import com.labyrinth.gui.SpriteGUI;

public class HumanPlayer extends Player {

	public HumanPlayer(int player_id, String player_name, Origin origin, SpriteGUI textures, Wall start_position, PlayerListener listener) {
		super(player_id, player_name, origin, textures, start_position, listener);
	}


	@Override
	public void update(GameContainer gc) {
		
		if(this.isPlaying()){
			Input i = gc.getInput();
			
			if(i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				this.playerWantsToMove(i.getMouseX(), i.getMouseY());
			}
			
			if(i.isKeyPressed(Input.KEY_NUMPAD5)){
				this.playerHasFinishedHisRound();
			}
		}
	}
	


}
