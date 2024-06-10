package model;

import java.awt.Image;

public class Worker extends MobileEntity{

	public Worker(int x, int y, Image textureImage) {
		super(x, y, textureImage);

	}
	public Worker() {
		
	}
	@Override
	public String toString() {
		
		return "W";
	}

	
	

}
