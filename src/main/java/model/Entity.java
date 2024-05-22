package model;

import java.awt.Graphics;
import java.awt.Image;






public class Entity {
	
	private int exp = 36;


	
	int x;
	int y;
	Image textureImage;
	
	public Entity(int x, int y, Image textureImage) {
		
		this.x = x;
		this.y = y;
		this.textureImage = textureImage;
	}
	
	
    public void draw(Graphics g) {
    	int xdis = x*exp;
    	int ydis = y*exp;

        if (textureImage != null) {
            g.drawImage(textureImage, xdis, ydis, null);
        }
    }
	
	public String toString(){
			return "Entity";
	}
	
	public int getX() {
		return this.x;
	}
	
	
	public int getY() {
		return this.y;
	}
	
	public void setImage(Image textura) {
		textureImage = textura;
	}
	

		
	

}
