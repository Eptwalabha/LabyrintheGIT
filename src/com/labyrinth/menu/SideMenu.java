package com.labyrinth.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.labyrinth.gui.SpriteGUI;

public class SideMenu extends Menu{
	
	private Button turn_left, turn_right;
	private Slider player_1, player_2;
	
	public SideMenu() throws SlickException{
		
		SpriteGUI button_texture = new SpriteGUI("images/menu/button.png", 4, 1);
		SpriteGUI slider_texture = new SpriteGUI("images/menu/slider.png", 5, 1);
		
		this.turn_left = new Button(0, button_texture);
		this.turn_left.setPosition(0, 0);
		this.turn_left.setDimention(100, 20);
		this.turn_left.setVisible(true);
		
		this.turn_right = new Button(1, button_texture);
		this.turn_right.setPosition(0, 30);
		this.turn_right.setDimention(100, 20);
		this.turn_right.setVisible(true);
		
		this.player_1 = new Slider(2, 10, 0, slider_texture);
		this.player_1.setPosition(0, 60);
		this.player_1.setDimention(100, 20);
		
		this.player_2 = new Slider(2, 10, 0, slider_texture);
		this.player_2.setPosition(0, 90);
		this.player_2.setDimention(100, 20);
		
		this.setMenuDimention(300, 768);
		this.setMenuPosition(0, 0);
		this.setMenuVisible(true);
		
		this.addMenuComponent(turn_left);
		this.addMenuComponent(turn_right);
		this.addMenuComponent(player_1);
		this.addMenuComponent(player_2);
		
	}

	@Override
	public void render(Graphics arg2) {
		if(this.isMenuVisible())
		for(MenuComponent mc : this.components){
			mc.render(arg2);
		}
	}

	@Override
	public void update(GameContainer arg0) {
		Input i = arg0.getInput();
		for(MenuComponent mc : this.components){
			mc.update(i);
		}		
	}
	
}
