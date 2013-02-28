package com.labyrinth.gui;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SpriteGUI {
	
	public static final int LEFT = 0;
	public static final int TOP = 0;
	public static final int CENTER = 1;
	public static final int RIGHT = 2;
	public static final int BOTTOM = 2;
	
	private float pos_rot_x, pos_rot_y;
	private SpriteSheet sprites;
	private int tile_width;
	private float ratio_width_height = 1.0f;

	public SpriteGUI(String url, int nbr_column, int nbr_line) throws SlickException{
		this(url, nbr_column, nbr_line, SpriteGUI.LEFT, SpriteGUI.TOP);
	}
	
	public SpriteGUI(String url, int nbr_column, int nbr_line, int axe_x, int axe_y) throws SlickException{
		Image image = new Image(url);
		image.setFilter(Image.FILTER_NEAREST);
		
		this.tile_width = image.getWidth() / nbr_column;
		this.ratio_width_height = (image.getHeight() / (nbr_line * 1.0f)) / (image.getWidth() / (nbr_column* 1.0f));
//		System.out.println("ratio = " + this.ratio_width_height);
		this.sprites = new SpriteSheet(image, image.getWidth() / nbr_column, image.getHeight() / nbr_line);
		
		this.setCenterOfRotation(axe_x, axe_y);
		
	}

	public void setCenterOfRotation(int axe_x, int axe_y){
		
		this.pos_rot_x = this.calculCenterOfRotation((float) this.tile_width, axe_x);
		this.pos_rot_y = this.calculCenterOfRotation((float) (this.tile_width * this.ratio_width_height), axe_y);

	}
	
	private float calculCenterOfRotation(float mesure, int mode){
		
		float result = 0f;
		
		switch (mode) {
		case 1:
			result = mesure / 2.0f;
			break;
		case 2:
			result = mesure;
			break;
		}
		
		return result;
	}
	
	public Image getSpriteAt(int x, int y, int width){
		return this.sprites.getSprite(x, y).getScaledCopy(width, (int) (width * this.ratio_width_height));
	}

	public Image getSpriteAt(int x, int y, int width, float angle){
		
		float ratio = this.tile_width / (width * 1f);
//		System.out.println("sans =" + this.pos_rot_x + "; ratio =" + (this.pos_rot_x / ratio));
		Image image =this.sprites.getSprite(x, y).getScaledCopy(width, (int) (width * this.ratio_width_height));
		image.setCenterOfRotation(this.pos_rot_x / ratio, this.pos_rot_y / ratio);
		image.setRotation(angle);
		return image;
	}

	public Image getSpriteAt(int x, int y, int width, int height){
		return this.sprites.getSprite(x, y).getScaledCopy(width, height);		
	}

	public int getTileWidth(){
		return this.tile_width;
	}
	
	public int getTileHeight(){
		return (int) (this.tile_width * this.ratio_width_height);
	}
	
	public float getTileRatio(){
		return this.ratio_width_height;
	}
}
