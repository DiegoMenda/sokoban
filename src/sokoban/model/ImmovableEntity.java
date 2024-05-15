package model;

public class ImmovableEntity extends Entity{

	boolean penetrable;
	
	public ImmovableEntity(int x, int y, boolean penetrable) {
		super(x, y);
		this.penetrable = penetrable;
	}
	
	

}
