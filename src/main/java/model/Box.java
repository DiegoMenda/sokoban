package model;

import java.awt.Image;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Box extends MobileEntity {

	private Image normalBox;
	private Image boxOnGoal;

	public Box(int x, int y, Image textureImage1, Image textureImage2) {
		super(x, y, textureImage1);
		normalBox = textureImage1;
		boxOnGoal = textureImage2;
	}
	
    public Box() {
        // Constructor sin argumentos necesario para JAXB
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
