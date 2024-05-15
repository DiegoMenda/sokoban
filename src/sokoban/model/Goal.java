package model;

public class Goal extends ImmovableEntity{

	private boolean archieved;
	
	public Goal(int x, int y) {
		super(x, y, false);
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
