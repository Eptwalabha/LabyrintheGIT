package com.labyrinth.gui;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SpriteGUI {
	
	private SpriteSheet sprites;
	private int tile_width;
	private float ratio_width_height = 1.0f;
	
	public SpriteGUI(String url, int nbr_column, int nbr_line) throws SlickException{
		Image image = new Image(url);
		image.setFilter(Image.FILTER_NEAREST);
		
		this.tile_width = image.getWidth() / nbr_column;
		this.ratio_width_height = (image.getHeight() / (nbr_line * 1.0f)) / (image.getWidth() / (nbr_column* 1.0f));
		System.out.println("ratio = " + this.ratio_width_height);
		this.sprites = new SpriteSheet(image, image.getWidth() / nbr_column, image.getHeight() / nbr_line);
		
	}
	
	public Image getSpriteAt(int x, int y, int width){
		return this.sprites.getSprite(x, y).getScaledCopy(width, (int) (width * this.ratio_width_height));
	}

	public int getTileWidth(){
		return this.tile_width;
	}
}
