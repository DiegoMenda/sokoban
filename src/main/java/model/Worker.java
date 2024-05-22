package model;

import java.awt.Image;

public class Worker extends MobileEntity{

	public Worker(int x, int y, Image textureImage) {
		super(x, y, textureImage);

	}
	
	@Override
	public String toString() {
		
		return "W";
	}

	public void setX(int x) {
		// TODO Auto-generated method stub
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;		
	}
	
	

}
