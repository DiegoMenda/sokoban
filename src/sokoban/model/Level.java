package model;

public class Level {

	int col, row;
	public Entity[][] pos;
	String levelName;
	
	public Level(int col, int row, String levelName, Entity[][] pos) {
		this.col = col;
		this.row = row;
		this.pos = pos;
		this.levelName = levelName;
	}

	
	
	
	public void undoMove(){

	}

	public String toString(){
		String print = "";
		for(int i = 0; i < col; i++) {

			for(int j = 0; j < row; j++) {
				print = print + pos[i][j].toString();
			}
			if(i!=col-1) {print = print + "\n";}
		}
		return print;
	}


}
