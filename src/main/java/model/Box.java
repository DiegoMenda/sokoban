package model;

import java.awt.Image;

public class Box extends MobileEntity {

	private Image normalBox;
	private Image boxOnGoal;

	public Box(int x, int y, Image textureImage1, Image textureImage2) {
		super(x, y, textureImage1);
		normalBox = textureImage1;
		boxOnGoal = textureImage2;
	}

	@Override
	public String toString() {

		return "#";
	}


	public void setNormalBoxTexture() {

		textureImage = normalBox;
	}

	public void setBoxOnGoalTexture() {

		textureImage = boxOnGoal;
	}

}
