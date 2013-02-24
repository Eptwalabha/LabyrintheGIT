package com.labyrinth.gui;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SpriteGUI {
	
	private SpriteSheet sprites;
	
	public SpriteGUI(String url, int nbr_column, int nbr_line) throws SlickException{
		
		Image image = new Image(url);
		image.setFilter(Image.FILTER_NEAREST);
		
		this.sprites = new SpriteSheet(image, image.getWidth() / nbr_column, image.getHeight() / nbr_line);
		
	}
	
	public Image getSpriteAt(int x, int y, float scale){
		return this.sprites.getSprite(x, y).getScaledCopy(scale);
	}

}
