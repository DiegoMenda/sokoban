package model;

import java.awt.Image;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({Worker.class, Box.class})
public class MobileEntity extends Entity{

	public MobileEntity(int x, int y, Image textureImage) {
		super(x, y, textureImage);
	}
	public MobileEntity() {
		
	}
	public void move(int posx, int posy) {
		this.x = posx;
		this.y = posy;
	}
	

}
