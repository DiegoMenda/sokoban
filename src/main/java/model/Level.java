package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Level {

	private int col, row;
	private ImmovableEntity[][] immovableEntities;
	private MobileEntity[][] mobileEntities;

	private String levelName;
	private Worker warehouseMan;
	private static final Logger logger = LoggerFactory.getLogger(Level.class);

	public Level(int col, int row, String levelName, ImmovableEntity[][] immovableEntity, MobileEntity[][] mobileEntities) {
		this.col = col;
		this.row = row;
		this.immovableEntities = immovableEntity;
		this.mobileEntities = mobileEntities;
		this.levelName = levelName;
	}




	public void undoMove(){
		logger.info("Undoing last movement");
	}

	public String toString(){
		String print = "";
		for(int i = 0; i < row; i++) {

			for(int j = 0; j < col; j++) {
				print = print + immovableEntities[i][j].toString();
			}
			if(i!=col-1) {print = print + "\n";}
		}
		return print;
	}


	public void setWarehouseMan(Worker warehouseMan) {
		if (warehouseMan == null) {
			logger.error("setWarehouseMan error: The warehouseMan parameter is null");
			throw new IllegalArgumentException("The warehouseMan parameter cannot be null");
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