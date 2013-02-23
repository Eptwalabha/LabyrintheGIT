package com.labyrinth.game.player.ai;

import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.labyrinth.game.Origin;
import com.labyrinth.game.maze.Maze;
import com.labyrinth.game.maze.Wall;
import com.labyrinth.game.player.Player;
import com.labyrinth.game.player.PlayerListener;

public class AIPlayer extends Player implements Runnable{
	
	private Maze maze;
	private Maze maze_copy;
	
	public AIPlayer(int player_id, Origin origin, Wall start_position, PlayerListener listener, Maze maze) {
		super(player_id, "cpu " + player_id, origin, start_position, listener);
		this.maze = maze;
	}

	
	public void newRound(){
		new Thread(this).run();
	}


	@Override
	public void run() {
		
		try {
			this.maze_copy = this.maze.getCopyForAI();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
		
		
		
		
		
		
	}

	
	@Override
	public void update(GameContainer gc) {
		
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		// TODO Auto-generated method stub
		
	}


}
