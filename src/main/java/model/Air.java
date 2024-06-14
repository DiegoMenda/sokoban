package model;

import java.awt.Image;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Air extends ImmovableEntity{

	public Air(int x, int y, Image textureImage) {
		super(x, y, textureImage, true);
	}
	
    public Air() {
        // Constructor sin argumentos necesario para JAXB
    }
	
	@Override
	public String toString() {
		return ".";
	}

}
