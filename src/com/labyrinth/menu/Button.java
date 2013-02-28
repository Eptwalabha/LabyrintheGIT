package com.labyrinth.menu;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import com.labyrinth.gui.SpriteGUI;

public class Button extends MenuComponent{

	private SpriteGUI textures;
	
	private boolean visible = false;
	private boolean enable = true;
	private boolean hoover = true;
	
	private boolean pressed = false;
	
	
	public Button(int id, SpriteGUI textures) {
		super(id);
		this.textures = textures;
		this.setDimention(100, 20);
		
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if(this.visible){
			
			if(this.enable){
				if(!this.pressed){
					if(!this.hoover){
						this.textures.getSpriteAt(0, 0, this.getWidth(), this.getHeight()).draw(this.getPosX(), this.getPosY());
					}else{
						this.textures.getSpriteAt(1, 0, this.getWidth(), this.getHeight()).draw(this.getPosX(), this.getPosY());
					}
				}else{
					if(!this.hoover){
						this.textures.getSpriteAt(0, 0, this.getWidth(), this.getHeight()).draw(this.getPosX(), this.getPosY());
					}else{
						this.textures.getSpriteAt(2, 0, this.getWidth(), this.getHeight()).draw(this.getPosX(), this.getPosY());
					}
				}
			}else{
				this.textures.getSpriteAt(3, 0, this.getWidth(), this.getHeight()).draw(this.getPosX(), this.getPosY());
			}
		}
		
	}

	@Override
	public void update(Input i) {
		

		this.hoover = this.hooverComponent(i.getMouseX(), i.getMouseY());
		
		if(!i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && this.pressed){
			if(this.hoover){
				for(int t = 0; t < this.listeners.size(); t++){
					this.listeners.get(t).action(this);
				}
			}
		}
		
		if(i.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			if(this.hoover){
				this.pressed = true;
			}
		}
		
		if(!i.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			this.pressed = false;
		}
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	

}
