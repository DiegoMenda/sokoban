package model;

public class MobileEntity extends Entity{

	public MobileEntity(int x, int y) {
		super(x, y);
	}
	
	public void move(int posx, int posy) {
		this.x = posx;
		this.y = posy;
	}

}
