package model;

public class Level {

	private int col, row;
	private ImmovableEntity[][] immovableEntities;
	private MobileEntity[][] mobileEntities;

	private String levelName;
	private Worker warehouseMan;


	public Level(int col, int row, String levelName, ImmovableEntity[][] immovableEntity, MobileEntity[][] mobileEntities) {
		this.col = col;
		this.row = row;
		this.immovableEntities = immovableEntity;
		this.mobileEntities = mobileEntities;
		this.levelName = levelName;
	}




	public void undoMove(){

	}

	public String toString(){
		String print = "";
		for(int i = 0; i < col; i++) {

			for(int j = 0; j < row; j++) {
				print = print + immovableEntities[i][j].toString();
			}
			if(i!=col-1) {print = print + "\n";}
		}
		return print;
	}


	public void setWarehouseMan(Worker warehouseMan) {
		if (warehouseMan == null) {
			throw new IllegalArgumentException("El parámetro warehouseMan no puede ser nulo");
		}
		this.warehouseMan = warehouseMan;

	}
	
	public Worker getWarehouseMan() {
		return this.warehouseMan;
	}


	public int getRow() {

		return this.row;
	}

	public int getCol() {
		return this.col;
	}
	

	public String getLevelName() {
		return levelName;
	}
	
	public ImmovableEntity[][] getImmovableEntities() {
		return immovableEntities;
	}
	
	public ImmovableEntity getImmovableEntities(int x, int y) {
		return immovableEntities[x][y];
	}
	public void setImmovableEntities(int x, int y, ImmovableEntity immovableEntity) {
		immovableEntities[x][y] = immovableEntity;
	}

	
	public MobileEntity[][] getMobileEntities() {
		return mobileEntities;
	}
	
	public MobileEntity getMobileEntities(int x, int y) {
		return mobileEntities[x][y];
	}
	
	public void setMobileEntities(int x, int y, MobileEntity mobileEntity) {
		 mobileEntities[x][y] = mobileEntity;
	}


}