package com.labyrinth.game.player.ai;

import org.newdawn.slick.GameContainer;

import com.labyrinth.game.Origin;
import com.labyrinth.game.maze.Maze;
import com.labyrinth.game.maze.Wall;
import com.labyrinth.game.player.Player;
import com.labyrinth.game.player.PlayerEventListener;
import com.labyrinth.game.player.PlayerMovement;
import com.labyrinth.game.player.WallMovement;
import com.labyrinth.game.player.ai.strategy.AIEasyStrategy;
import com.labyrinth.game.player.ai.strategy.AIHardStrategy;
import com.labyrinth.game.player.ai.strategy.AIMediumStrategy;
import com.labyrinth.game.player.ai.strategy.AIStrategy;
import com.labyrinth.gui.SpriteGUI;
import com.labyrinth.utils.graph.GraphVertex;

public class AIPlayer extends Player implements Runnable{
	
	public final static int AI_EASY = 0;
	public final static int AI_MEDIUM = 1;
	public final static int AI_HARD = 2;
	
	private AIStrategy playing_strategy;
	
	private int ai_lvl = 0;
	
	private Maze maze;
	private Maze maze_copy;
	
	public AIPlayer(int player_id, String player_name, Origin origin, SpriteGUI textures, Wall start_position, PlayerEventListener listener, Maze maze, int ai_lvl) {
		super(player_id, player_name, origin, textures, start_position, listener);
		this.maze = maze;
		this.maze_copy = this.maze.getCopyForAI();
		this.ai_lvl = ai_lvl;
		
		this.playing_strategy = new AIEasyStrategy(this.maze_copy, this.getPosition(), this.getPlayerObjective());
		
		if(this.ai_lvl > AI_EASY){
			this.playing_strategy = new AIMediumStrategy(this.playing_strategy);
			
			if(this.ai_lvl > AI_MEDIUM){
				this.playing_strategy = new AIHardStrategy(this.playing_strategy);
			}
		}
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
		
		this.playing_strategy.processSolutions();
		
		long time;
		long timet;
		long next_timet = 1000;
		
		time = System.currentTimeMillis();
		do{
			timet = System.currentTimeMillis() - time;
			
			if(timet >= next_timet){
				System.out.print(timet / 1000 + ", ");
				next_timet += 1000;
			}
			
		}while(timet < 1000);
		
		int id = this.getPlayerId();
		int row, line, direction;
		WallMovement wall_move;
		
		do{
			row = (int) (Math.random() * this.maze.getNumberOfCollumn());
			line = (int) (Math.random() * this.maze.getNumberOfLine());
			direction = (int) (Math.random() * 4);
			wall_move = new WallMovement(row, line, direction);
			System.out.println("demande de " + id + " pour d�placer un mur (random) (" + row + ", " + line + ", " + direction + ")");
		}while(!this.listeners.get(0).playerWantsToPushWall(id, wall_move));
		
		this.maze.resetWeightGraph();
		GraphVertex list = this.getPosition().getAllVerticesConnectedTo();
		this.maze.resetWeightGraph();
		
		int list_size = list.countBrother();
		
		PlayerMovement player_move;
		Wall wall_temp;
		
		time = System.currentTimeMillis();
		do{
			timet = System.currentTimeMillis() - time;
			
			if(timet >= next_timet){
				System.out.print(timet / 1000 + ", ");
				next_timet += 1000;
			}
			
		}while(timet < 1000);
		
		do{
			
			wall_temp = (Wall) list.getBrother((int) (Math.random() * list_size)).getSon(); 
			player_move = new PlayerMovement(wall_temp.getCoordinate2D());
			System.out.println("demande de " + id + " pour se d�placer (random)");
		}while(!this.listeners.get(0).playerWantsToMove(id, player_move));		
//		
//		this.playerHasFinishedHisRound();
		
	}

	
	@Override
	public void update(GameContainer gc) {
		
	}
	
	@Override
	public int getTypeOfPlayer() {
		return Player.AI;
	}

}
