package model;

import java.awt.Image;

public class ImmovableEntity extends Entity{

	boolean penetrable;
	
	public ImmovableEntity(int x, int y, Image textureImage, boolean penetrable) {
		super(x, y, textureImage);
		this.penetrable = penetrable;
	}
	
	

}
