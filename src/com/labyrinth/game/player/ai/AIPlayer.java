package com.labyrinth.game.player.ai;

import org.newdawn.slick.GameContainer;

import com.labyrinth.game.Origin;
import com.labyrinth.game.maze.Maze;
import com.labyrinth.game.maze.Wall;
import com.labyrinth.game.player.Player;
import com.labyrinth.game.player.PlayerEventListener;
import com.labyrinth.gui.SpriteGUI;

public class AIPlayer extends Player implements Runnable{
	
	private Maze maze;
	private Maze maze_copy;
	
	public AIPlayer(int player_id, Origin origin, SpriteGUI textures, Wall start_position, PlayerEventListener listener, Maze maze) {
		super(player_id, "cpu " + player_id, origin, textures, start_position, listener);
		this.maze = maze;
	}
	
	public void newRound(){
		new Thread(this).run();
	}

	@Override
	public void setStep(int step){
		super.setStep(step);
		
		if(step == 1){
			Thread t = new Thread(this);
			t.start();
		}
		
	}
	
	@Override
	public void run() {
		
		this.maze_copy = this.maze.getCopyForAI();
		
		long time = System.currentTimeMillis();
		long timet;
		long next_timet = 1000;
		
		do{
			timet = System.currentTimeMillis() - time;
			
			if(timet >= next_timet){
				System.out.print(timet / 1000 + ", ");
				next_timet += 1000;
			}
			
		}while(timet < 5000);
		
		System.out.println("fin");
		this.playerHasFinishedHisRound();
		
	}

	
	@Override
	public void update(GameContainer gc) {
		
	}
	
	@Override
	public int getTypeOfPlayer() {
		return Player.AI;
	}


}
