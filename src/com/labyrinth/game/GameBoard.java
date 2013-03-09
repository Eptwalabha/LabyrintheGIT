package com.labyrinth.game;

import com.labyrinth.cursor.Cursor;
import com.labyrinth.cursor.CursorMovePlayer;
import com.labyrinth.cursor.CursorMoveWall;
import com.labyrinth.game.maze.Maze;
import com.labyrinth.game.maze.Wall;
import com.labyrinth.game.player.Player;
import com.labyrinth.game.player.PlayerEventListener;
import com.labyrinth.game.player.PlayerMovement;
import com.labyrinth.game.player.WallMovement;
import com.labyrinth.game.player.ai.AIPlayer;
import com.labyrinth.game.player.human.HumanPlayer;
import com.labyrinth.game.player.online.OnlinePlayer;
import com.labyrinth.gui.SpriteGUI;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import com.labyrinth.objective.Objective;
import com.labyrinth.utils.graph.GraphVertex;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameBoard extends BasicGameState implements PlayerEventListener{

	public final static int STEP_WAIT = 0;
	public final static int STEP_MOVE_WALL = 1;
	public final static int STEP_MOVE_PLAYER = 2;
	public final static int STEP_END_GAME = 3;
	
	private boolean end_game = false;
	private boolean right_mouse_button;
	private SpriteGUI objective_textures;
	
	private Cursor cursor_move_wall;
	private Cursor cursor_move_player;
	
	private Maze maze;

	private Origin origin;
	private List<Player> players = new ArrayList<Player>();
	private List<Objective> objectives = new ArrayList<Objective>();
	
	private int current_player = 0;
	private int last_line = -1, last_row = -1, last_direction= -1;
	
	private Input input;
	
	
	private TrueTypeFont ttf;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		this.origin = new Origin();
		SpriteGUI wall_texture = new SpriteGUI("images/murs/wall.png", 12, 4);
		SpriteGUI player_texture = new SpriteGUI("images/items/player.png", 1, 4);
		SpriteGUI wheel_texture = new SpriteGUI("images/menu/wheel.png", 3, 1, SpriteGUI.CENTER, SpriteGUI.BOTTOM);
		this.objective_textures = new SpriteGUI("images/items/objective.png", 1, 4);
		
		this.cursor_move_wall = new CursorMoveWall(wheel_texture);
		this.cursor_move_wall.addPlayerListener(this);
		
		this.cursor_move_player = new CursorMovePlayer(wheel_texture);
		this.cursor_move_player.addPlayerListener(this);
		
		this.origin.setWidth(wall_texture.getTileWidth());
		this.players = new ArrayList<Player>();		
		
		this.maze = new Maze(9, 18, this.origin, wall_texture);
		
		this.input = arg0.getInput();
		
		this.clearPlayerList();

		this.players.add(this.createPlayer(Player.HUMAN, 0, player_texture, 0));
		this.players.add(this.createPlayer(Player.AI, 1, player_texture, 0));
		
//		this.players.add(this.createPlayer(Player.AI, 0, player_texture, 0));
//		this.players.add(this.createPlayer(Player.AI, 1, player_texture, 0));
//		this.players.add(this.createPlayer(Player.AI, 2, player_texture, 0));
//		this.players.add(this.createPlayer(Player.AI, 3, player_texture, 0));

//		this.players.add(this.createPlayer(Player.HUMAN, 0, player_texture, 0));
//		this.players.add(this.createPlayer(Player.HUMAN, 1, player_texture, 0));
//		this.players.add(this.createPlayer(Player.HUMAN, 2, player_texture, 0));
//		this.players.add(this.createPlayer(Player.HUMAN, 3, player_texture, 0));

		this.current_player = 0;
		
		int mx = this.maze.getNumberOfCollumn();
		int my = this.maze.getNumberOfLine();
		
		int number_of_objective = this.maze.getNumberOfCollumn() * this.maze.getNumberOfLine() / 8 ;
		
		for(Player p: this.players){
			for(int i = 0; i < number_of_objective; i++){
				Wall w;
				boolean wall_already_used;
				
				do{
					wall_already_used = false;
					w = this.maze.getWall((int)(Math.random() * mx), (int)(Math.random() * my));
					for(int j = 0; j < this.objectives.size(); j++){
						if(w == this.objectives.get(j).getPosition()) wall_already_used = true;
					}
					
				}while(wall_already_used);
				Objective o = new Objective(w, objective_textures, p);
				p.addObjective(o);
				this.objectives.add(o);
			}
		}
		
		this.players.get(this.current_player).setStep(GameBoard.STEP_MOVE_WALL);
		this.players.get(this.current_player).getPlayerObjective().active(true);

		Font f = new Font("Times new roman", Font.BOLD , 20);
		this.ttf = new TrueTypeFont(f, true);
		this.end_game = false;
		
	}
	
	private void clearPlayerList(){
		this.players = new ArrayList<Player>();
		this.objectives = new ArrayList<Objective>();
	}
	
	private Player createPlayer(int player_type, int player_position, SpriteGUI texture, int ai_lvl){
		
		Player p = null;
		String player_name = "Joueur ";
		int id = this.generateUniqueId();
		Wall player_start;
		
		switch (player_position) {
		case 0:
			player_name += "Bleu";
			player_start = this.maze.getWall(0, 0);
			break;
		case 1:
			player_name += "Rouge";
			player_start = this.maze.getWall(this.maze.getNumberOfCollumn() - 1, 0);
			break;
		case 2:
			player_name += "Jaune";
			player_start = this.maze.getWall(0, this.maze.getNumberOfLine() - 1);
			break;
		default:
			player_name += "Vert";
			player_start = this.maze.getWall(this.maze.getNumberOfCollumn() - 1, this.maze.getNumberOfLine() - 1);
			break;
		}
		
		switch (player_type) {
		case Player.HUMAN:
			p = new HumanPlayer(id, player_name, this.origin, texture, player_start, this);
			this.input.addMouseListener((MouseListener) p);
			
			break;
		case Player.ONLINE:
			p = new OnlinePlayer(id, player_name, this.origin, texture, player_start, this);
		default:
			p = new AIPlayer(id, player_name, this.origin, texture, player_start, this, this.maze, ai_lvl);
			break;
		}
		
		p.setPlayerPosition(player_position);
		
		return p;
	}
	
	private int generateUniqueId(){
		
		int result;
		int exist = 0;
		do{
			exist = 0;
			result = (int) (Math.random() * 10000);
			
			for(Player p : this.players){
				exist = (p.getPlayerId() == result) ? exist + 1 : exist;
			}
		}while(exist != 0);
		
		System.out.println("id généné: " + result);
		return result;
	}


	private void printGraph() {
		if(this.players.get(this.current_player).isMoving()){
			this.players.get(this.current_player).getPath().printGraph(0, 0);
		}
		
	}
	
	private boolean isTheCurrentPlayer(int id){
		return (this.players.get(this.current_player).getPlayerId() == id);
	}
	
	private void nextPlayer(){
		
		Player p = this.players.get(this.current_player);
		p.setStep(GameBoard.STEP_WAIT);
		p.getPlayerObjective().active(false);
		
		this.current_player = (this.current_player + 1) % this.players.size();
		
		p = this.players.get(this.current_player);
		p.setStep(GameBoard.STEP_MOVE_WALL);
		p.getPlayerObjective().active(true);
		
	}
	
	private void checkObjectives(){
		
		for(Player p: this.players){
			Objective o = p.getPlayerObjective();
			if(o != null){
				if(p.getPosition() == o.getPosition()){
					p.nextObjective();
					if(p.completeAllObjectTives()) this.endGame();
				}
			}
		}
		
	}
	
	private void endGame() {
		this.end_game = true;
	}

	private void checkIfPlayerPushed(Wall old){

		Wall last = this.maze.getAdditionalWall();
		for(Player p: this.players){
			if(p.getPosition() == last){
				p.setPosition(old);
			}
		}

	}
	
	private void checkLastWallMove(int row, int line) {
		
		if(this.last_direction >= 0){
			
			if(this.last_direction % 2 == 0 && row == this.last_row){
				((CursorMoveWall) this.cursor_move_wall).setArrowEnable((this.last_direction + 2) % 4, false);
			}
			
			if(this.last_direction % 2 > 0 && line == this.last_line){
				((CursorMoveWall) this.cursor_move_wall).setArrowEnable((this.last_direction + 2) % 4, false);
			}
		}	
	}
	
	private void checkIfPlayerCohabit() {
		
		Wall first = null;
		Wall second = null;

		for(int i = 0; i < this.players.size(); i++){
			this.players.get(i).yourPositionIsOccupied(false);
		}
		
		for(int i = 0; i < this.players.size(); i++){
			
			first = this.players.get(i).getPosition();
			this.players.get(i).yourPositionIsOccupied(false);
			
			for(int j = 0; j < this.players.size(); j++){
				
				if(i != j){
					second = this.players.get(j).getPosition();
					
					if(first == second){
						this.players.get(i).yourPositionIsOccupied(true);
						this.players.get(j).yourPositionIsOccupied(true);
					}
				}
			}
		}
	}


	
	private boolean isALegalMove(int row, int line, int direction){
		
		if(this.last_direction >= 0 && (direction + 2) % 4 == this.last_direction){
			
			if(this.last_direction % 2 == 0 && row == this.last_row) return false;
			if(this.last_direction % 2 > 0 && line == this.last_line) return false;
		}
		
		return true;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		this.maze.render(arg0, g);
		
		this.maze.getAdditionalWall().render(75, 75, 150);
		for(Objective o : objectives){
			o.render(g);
		}

		for(Player j : players){
			j.render(arg0, g);
		}
		
		this.cursor_move_player.render(g);
		this.cursor_move_wall.render(g);
		g.setColor(Color.red);
		g.drawLine(300, 0, 300, arg0.getHeight());
		g.setColor(Color.white);
		this.ttf.drawString(25, 350, "Tab \nFaire pivoter le mur", Color.white);
		int pos = 400;
		for(Player j : players){
			
			String s = "Joueur " + (j.getPlayerPosition() + 1) + ": " + j.getPlayerScore() + " point(s)";
			this.ttf.drawString(25, pos, s, j.getPlayerColor());
			pos += 25;
			
		}
		
		String explications = this.getExplanations();
		
		this.ttf.drawString(310, 10, explications, this.players.get(this.current_player).getPlayerColor());
		

	}

	private String getExplanations(){
		String result = "";
		
		result = this.players.get(this.current_player).getPlayerName();
		
		result += ", c'est ton tour (";
		
		switch (this.players.get(this.current_player).getStep()) {
		case 1:
			result += "pousse un mur!";
			break;
		case 2:
			result += "déplace toi!";
			break;
		default:
			break;
		}
		
		result += ")";
		
		return result;
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		
		
		
		if(!this.end_game){
			this.origin.setBounds(300, 0,
					arg0.getWidth() - 300, arg0.getHeight(),
					this.maze.getNumberOfCollumn() * this.origin.getWidth(),
					this.maze.getNumberOfLine() * this.origin.getWidth());
			
			Input in = arg0.getInput();
	
			if(in.isKeyDown(Input.KEY_LEFT)){
				this.origin.setOriginPosition(this.origin.getOX() - 5, this.origin.getOY());
			}
			if(in.isKeyDown(Input.KEY_RIGHT)){
				this.origin.setOriginPosition(this.origin.getOX() + 5, this.origin.getOY());
			}
			if(in.isKeyDown(Input.KEY_UP)){
				this.origin.setOriginPosition(this.origin.getOX(), this.origin.getOY() - 5);
			}
			if(in.isKeyDown(Input.KEY_DOWN)){
				this.origin.setOriginPosition(this.origin.getOX(), this.origin.getOY() + 5);
			}
			
			for(Player p: players){
				p.update(arg0);
				if(p.isMoving()){
					p.move();
					this.checkIfPlayerCohabit();
					this.checkObjectives();
				}
			}
			
			if(in.isKeyPressed(Input.KEY_TAB)){
				this.maze.rotateAdditionalWall(Wall.TO_THE_RIGHT);
			}
			
			if(in.isKeyPressed(Input.KEY_SPACE)) this.maze.shakeWall();
	
			if(in.isKeyPressed(Input.KEY_ENTER)){
				this.players.get(this.current_player).setNewDestination(null);
			}
	
			if(in.isKeyPressed(Input.KEY_G)) this.printGraph();
		}else{
			this.init(arg0, arg1);
		}
	}
	
	@Override
	public int getID() {
		return 0;
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		
		if(this.right_mouse_button){
			int x = this.origin.getOX() - (arg0 - arg2);
			int y = this.origin.getOY() - (arg1 - arg3);
			
			this.origin.setOriginPosition(x, y);
		}
		
	}
	
	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		if(arg0 == Input.MOUSE_RIGHT_BUTTON){
			this.right_mouse_button = true;
		}
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		if(arg0 == Input.MOUSE_RIGHT_BUTTON){
			this.right_mouse_button = false;
		}
		
	}
	
	@Override
	public void mouseWheelMoved(int move){
		
		if(move != 0){
			int old_width = this.origin.getWidth();
			if(move < 0){
				this.origin.setWidth(old_width - 2);
			}
			if(move > 0){
				this.origin.setWidth(old_width + 2);
			}
			
			int mx = this.input.getMouseX();
			int my = this.input.getMouseY();
			int x2 = (int) (mx - (mx - this.origin.getOX()) * this.origin.getWidth() / old_width);
			int y2 = (int) (my - (my - this.origin.getOY()) * this.origin.getWidth() / old_width);
			
			this.origin.setOriginPosition(x2, y2);
			
		}
		
		
	}

	@Override
	public boolean playerWantsToPushWall(int id_player, WallMovement wall_movement) {

		if(this.isTheCurrentPlayer(id_player)){

			int row = wall_movement.getRowNumber();
			int line = wall_movement.getLineNumber();
			int direction = wall_movement.getDirection();

			if(this.maze.isALegalMove(row, line, direction) && this.isALegalMove(row, line, direction)){
				
				this.endAnimations();
				
				this.last_row = row;
				this.last_line = line;
				this.last_direction = direction;
	
				Wall old = this.maze.getAdditionalWall();
				this.maze.insertWallHere(row, line, direction);
				this.checkIfPlayerPushed(old);
				
				this.players.get(this.current_player).setStep(GameBoard.STEP_MOVE_PLAYER);
				this.checkObjectives();
				
				return true;
			}

		}
		return false;
	}

	@Override
	public boolean playerWantsToMove(int id_player, PlayerMovement player_movement) {
		if(this.isTheCurrentPlayer(id_player)){
	
			int row = player_movement.getRowNumber();
			int line = player_movement.getLineNumber();
			
			Wall dest = this.maze.getWallAt(row, line);
			
			if(dest != null){
				this.maze.resetWeightGraph();
				GraphVertex path = this.players.get(this.current_player).getPosition().getShortestPathRecursive(dest);
				if(path != null){
					
					this.endAnimations();
					this.players.get(this.current_player).setNewDestination(path);
					this.nextPlayer();
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void playerWantsToRotateAdditionalWall(int id_player, int mode) {
		if(this.isTheCurrentPlayer(id_player)){
			this.maze.rotateAdditionalWall(mode);
		}
	}

	@Override
	public void playerHasFinishedHisRound(int id_player) {
		if(this.isTheCurrentPlayer(id_player)){
			this.nextPlayer();
		}
	}

	@Override
	public void playerClicksToPushWall(int id_player, int mouse_x, int mouse_y, int direction) {
		if(this.isTheCurrentPlayer(id_player)){
			
			int row = this.maze.getRowNumber(mouse_x);
			int line = this.maze.getLineNumber(mouse_y);
			
			if(this.maze.isALegalMove(row, line, direction) && this.isALegalMove(row, line, direction)){

				this.endAnimations();
				
				this.last_row = row;
				this.last_line = line;
				this.last_direction = direction;
				
				Wall old = this.maze.getAdditionalWall();
				this.maze.insertWallHere(row, line, direction);
				this.checkIfPlayerPushed(old);
					
				this.players.get(this.current_player).setStep(GameBoard.STEP_MOVE_PLAYER);
				this.checkObjectives();
				
			}
		}
	}

	@Override
	public void playerClicksToMove(int id_player, int mouse_x, int mouse_y) {
		if(this.isTheCurrentPlayer(id_player)){
			
			int row = this.maze.getRowNumber(mouse_x);
			int line = this.maze.getLineNumber(mouse_y);
			
			Wall dest = this.maze.getWallAt(row, line);
			
			if(dest != null){
				this.maze.resetWeightGraph();
				GraphVertex path = this.players.get(this.current_player).getPosition().getShortestPathRecursive(dest);
				if(path != null){
					
					this.endAnimations();
					this.players.get(this.current_player).setNewDestination(path);
					this.nextPlayer();
				}
			}
		}
	}

	@Override
	public void highLight(int mouse_x, int mouse_y, int mode) {
		this.maze.hooverAt(mouse_x, mouse_y, mode);
	}

	@Override
	public void eventMousePressed(int player_id, int mouse_x, int mouse_y) {
		
		if(this.isTheCurrentPlayer(player_id)){
			if(this.players.get(this.current_player).getTypeOfPlayer() == Player.HUMAN){
				
				switch (this.players.get(this.current_player).getStep()) {
				case GameBoard.STEP_MOVE_WALL:
					if(!this.cursor_move_wall.isEnable()){
						this.cursor_move_wall.invoke(mouse_x, mouse_y);
						int line = this.maze.getLineNumber(mouse_y);
						int row = this.maze.getRowNumber(mouse_x);
						((CursorMoveWall) this.cursor_move_wall).setLineEnable(line % 2 != 0);
						((CursorMoveWall) this.cursor_move_wall).setRowEnable(row % 2 != 0);
						this.checkLastWallMove(row, line);
					}
					break;
				case GameBoard.STEP_MOVE_PLAYER:
					if(!this.cursor_move_player.isEnable()){
						this.cursor_move_player.invoke(mouse_x, mouse_y);
					}
					break;
				}
			}
		}
		
	}

	@Override
	public void eventMouseDrag(int player_id, int mouse_x, int mouse_y) {
		
		if(this.isTheCurrentPlayer(player_id)){
			if(this.players.get(this.current_player).getTypeOfPlayer() == Player.HUMAN){
				
				switch (this.players.get(this.current_player).getStep()) {
				case GameBoard.STEP_MOVE_WALL:
					if(this.cursor_move_wall.isEnable()){
						this.cursor_move_wall.drag(mouse_x, mouse_y);
					}
					break;
				case GameBoard.STEP_MOVE_PLAYER:
					if(this.cursor_move_player.isEnable()){
						this.cursor_move_player.drag(mouse_x, mouse_y);
					}
					break;
				}
			}
		}
	}

	@Override
	public void eventMouseReleased(int player_id, int mouse_x, int mouse_y) {
		
		if(this.isTheCurrentPlayer(player_id)){
			
			if(this.players.get(this.current_player).getTypeOfPlayer() == Player.HUMAN){
				
				switch (this.players.get(this.current_player).getStep()) {
				case GameBoard.STEP_MOVE_WALL:
					if(this.cursor_move_wall.isEnable()){
						this.cursor_move_wall.release(player_id, mouse_x, mouse_y);
					}
					break;
				case GameBoard.STEP_MOVE_PLAYER:
					if(this.cursor_move_player.isEnable()){
						this.cursor_move_player.release(player_id, mouse_x, mouse_y);
					}
					break;
				}
			}
			
		}
		
	}

	private void endAnimations(){
		
		for(int i = 0; i < this.players.size(); i++){
			if(i != this.current_player){
				this.players.get(i).finishYourMove();
			}
		}
	}
}
