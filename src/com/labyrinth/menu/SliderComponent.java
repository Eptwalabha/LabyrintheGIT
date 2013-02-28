package com.labyrinth.menu;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import com.labyrinth.gui.SpriteGUI;

public class SliderComponent extends MenuComponent {

	private int max_value = 100;
	private int cursor = 0;
	private SpriteGUI textures_slider;
	private Image textures_item;
	
	private boolean visible = true;
	private boolean enable = true;
	
	public SliderComponent(int id, int max_value, int value, SpriteGUI textures) {
		super(id);
		
		this.textures_slider = textures;
		this.max_value = max_value;
		this.cursor = value;
		this.setDimention(150 + 20, 20);
		this.setPosition(0, 0);
		
	}

	@Override
	public void render(Graphics g) {
		if(this.visible){
			
			this.textures_slider.getSpriteAt(2, 0, this.getWidth(), this.getHeight()).draw(this.getPosX(), this.getPosY());
			this.textures_slider.getSpriteAt(1, 0, 24, this.getHeight()).draw(this.getPosX() - 24, this.getPosY());
			this.textures_slider.getSpriteAt(3, 0, 24, this.getHeight()).draw(this.getPosX() + this.getWidth(), this.getPosY());
			
			
			
			int pos_x = (int) ((this.cursor / (this.max_value * 1f)) * this.getWidth());
			if(this.enable){
				
				this.textures_slider.getSpriteAt(0, 0, 24, this.getHeight()).draw(this.getPosX() + pos_x, this.getPosY());
				
			}else{
				
				this.textures_slider.getSpriteAt(4, 0, 24, this.getHeight()).draw(this.getPosX() + pos_x, this.getPosY());
				
			}
		}
	}

	@Override
	public void update(Input i) {
		// TODO Auto-generated method stub
		
	}

	public void setItem(Image item){
		this.textures_item = item.getScaledCopy(20, 20);
	}
	
	public void setCursor(int value){
		this.cursor = value;
	}
	
	public void setMaximum(int max){
		this.max_value = max;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	}
	public void setEnable(boolean enable){
		this.enable = enable;
	}
	
}
