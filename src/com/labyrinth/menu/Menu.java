package com.labyrinth.menu;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Menu {

	protected List<MenuComponent> components = new ArrayList<MenuComponent>();

	private int pos_x, pos_y;
	private int width, height;
	
	private boolean visible = false;
	
	public int getPosX(){
		return this.pos_x;
	}
	
	public int getPosY(){
		return this.pos_y;
	}
	
	public int getMenuWidth(){
		return this.width;
	}
	
	public int getMenuHeight(){
		return this.height;
	}
	
	public void addMenuComponent(MenuComponent mc){
		this.components.add(mc);
	}
	
	public void setMenuPosition(int x, int y){
		this.pos_x = x;
		this.pos_y = y;
	}
	
	public void setMenuDimention(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public void setMenuVisible(boolean visible){
		this.visible = visible;
	}
	
	public boolean isMenuVisible(){
		return this.visible;
	}
	
	public abstract void render(Graphics arg0);
	public abstract void update(GameContainer arg0);
	
}
