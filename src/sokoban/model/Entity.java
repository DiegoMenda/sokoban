package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;


public class Entity {
	
	private int exp = 36;
	
	public static void main(String[] args) {
		
		Entity wall = new Wall(0,0, TexturePaths.generateImage(TexturePaths.TEXTURE_WALL));

	}
	
	int x,y;
	Image textureImage;
	
	public Entity(int x, int y, Image textureImage) {
		
		this.x = x;
		this.y = y;
		this.textureImage = textureImage;
	}
	
	
    public void draw(Graphics g) {
    	int x_dis = x*exp;
    	int y_dis = y*exp;
    	System.out.println("DIBUJANDO --> "+g);
        if (textureImage != null) {
            g.drawImage(textureImage, x_dis, y_dis, null);
        }
    }
	
	public String toString(){
			return "Entity";
	}
		
	

}
