package model;

public class Level {

	int col, row;
	Entity[][] pos;

	public Level(int col, int row, Entity[][] pos) {
		this.col = col;
		this.row = row;
		this.pos = pos;
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
