package model;

public class Wall extends ImmovableEntity {

	public Wall(int x, int y) {
		super(x, y, false);
	}
	
	@Override
	public String toString() {
		return "+";
	}

}
