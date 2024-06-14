package model;

import java.awt.Image;



public class Air extends ImmovableEntity{

	public Air(int x, int y, Image textureImage) {
		super(x, y, textureImage, true);
	}
	
	@Override
	public String toString() {
		return ".";
	}

}
