package model;

import java.awt.Image;


public class Goal extends ImmovableEntity{

	private boolean archieved;
	
	public Goal(int x, int y, Image textureImage) {
		super(x, y, textureImage, true);
		this.archieved = false;
		
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