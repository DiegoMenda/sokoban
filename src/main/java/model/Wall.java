package model;

import java.awt.Image;


public class Wall extends ImmovableEntity {

	public Wall(int x, int y, Image textureImage) {
		super(x, y, textureImage, false);
	}
	
	@Override
	public String toString() 
	{
		return "+";
	}

}
