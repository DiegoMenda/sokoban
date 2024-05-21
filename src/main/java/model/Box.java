package model;

import java.awt.Image;

public class Box extends MobileEntity {

	public Box(int x, int y, Image textureImage) {
		super(x, y, textureImage);
	}
	
	@Override
	public String toString() {
		
		return "#";
	}

}
