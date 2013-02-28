package com.labyrinth.menu;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public abstract class MenuComponent {

	private final int id;
	private int width, height, pos_x, pos_y;
	
	protected List<MenuListener> listeners = new ArrayList<MenuListener>();
	
	public MenuComponent(int id) {
		this.id = id;
	}
	
	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getPosX() {
		return this.pos_x;
	}

	public int getPosY() {
		return this.pos_y;
	}

	public int getId() {
		return this.id;
	}
	
	public void setDimention(int width, int height){
		this.height = height;
		this.width = width;
	}
	
	public void setPosition(int pos_x, int pos_y){
		this.pos_x = pos_x;
		this.pos_y = pos_y;
	}
	
	public boolean hooverComponent(int mx, int my){
		return (mx > pos_x && my > pos_y && mx < pos_x + width && my < pos_y + height);
	}
	
	public abstract void render(Graphics g);
	public abstract void update(Input i);
	
	public void ajouterEcouteur(MenuListener e){
		this.listeners.add(e);
	}
	
	public void actionSurComposant(){
		for(MenuListener c : listeners){
			c.action(this.id);
		}
	}
}
