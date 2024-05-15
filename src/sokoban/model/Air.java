package model;

public class Air extends ImmovableEntity{

	public Air(int x, int y) {
		super(x, y, true);
	}
	
	@Override
	public String toString() {
		return ".";
	}

}
