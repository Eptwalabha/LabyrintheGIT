package com.labyrinth.menu;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class SideMenu extends Menu{
	
	private List<MenuComponent> components = new ArrayList<MenuComponent>();
	
	public SideMenu(){
		
	}
	
	public void update(GameContainer gc){
		Input i = gc.getInput();
		for(MenuComponent mc : this.components){
			mc.update(i);
		}
	}

	public void render(Graphics g){
		for(MenuComponent mc : this.components){
			mc.render(g);
		}
	}
	
}
