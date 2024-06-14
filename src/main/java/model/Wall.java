package model;

import java.awt.Image;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Wall extends ImmovableEntity {

	public Wall(int x, int y, Image textureImage) {
		super(x, y, textureImage, false);
	}
	
    public Wall() {
        // Constructor sin argumentos necesario para JAXB
    }
	
	
	@Override
	public String toString() 
	{
		return "+";
	}

}
