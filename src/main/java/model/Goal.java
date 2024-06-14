package model;

import java.awt.Image;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Goal extends ImmovableEntity{

	private boolean archieved;
	
	public Goal(int x, int y, Image textureImage) {
		super(x, y, textureImage, true);
		this.archieved = false;
		
	}
	
    public Goal() {
        // Constructor sin argumentos necesario para JAXB
    }
	
	public boolean isGoalArchieved() {
		return archieved;
	}
	
	
	public void setGoalArchieved(boolean archieved) {
		this.archieved = archieved;
	}
	
	
	@Override
	public String toString() {
		return "*";
	}

}