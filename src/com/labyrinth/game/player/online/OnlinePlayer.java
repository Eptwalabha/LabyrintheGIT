package com.labyrinth.game.player.online;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.labyrinth.game.Origin;
import com.labyrinth.game.maze.Wall;
import com.labyrinth.game.player.Player;
import com.labyrinth.game.player.PlayerListener;
import com.labyrinth.gui.SpriteGUI;

/**
 * Au cas où...
 * @author Benhï
 *
 */
public class OnlinePlayer extends Player{

	public OnlinePlayer(int player_id, String name, Origin origin, SpriteGUI textures, Wall start_position, PlayerListener listener) {
		super(player_id, name, origin, textures, start_position, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer gc) {
		// TODO Auto-generated method stub
		
	}

}
