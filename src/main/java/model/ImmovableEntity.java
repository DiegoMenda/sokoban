package model;

import java.awt.Image;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlRootElement
@XmlSeeAlso({Wall.class, Air.class, Goal.class})
public class ImmovableEntity extends Entity{

	boolean penetrable;
	
	public ImmovableEntity(int x, int y, Image textureImage, boolean penetrable) {
		super(x, y, textureImage);
		this.penetrable = penetrable;
	}
    public ImmovableEntity() {
        // No es necesario inicializar penetrable, su valor predeterminado es false
    }
	
	public boolean getPenetrable() {
		return this.penetrable;
	}
	

	
	
	

}
