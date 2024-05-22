package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Level {

	private int col;
	private int row;
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


	

	@Override
	public String toString() {
	    StringBuilder print = new StringBuilder();
	    for (int i = 0; i < row; i++) {
	        for (int j = 0; j < col; j++) {
	            print.append(immovableEntities[i][j]);
	        }
	        print.append('\n');
	    }
	    return print.toString();
	}





	public void setWarehouseMan(Worker warehouseMan) {

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
		return immovableEntities[y][x];
	}
//	public void setImmovableEntities(int x, int y, ImmovableEntity immovableEntity) {
//		immovableEntities[y][x] = immovableEntity;
//	}

	
	public MobileEntity[][] getMobileEntities() {
		return mobileEntities;
	}
	
	public MobileEntity getMobileEntities(int x, int y) {
		return mobileEntities[y][x];
	}
	
	public void setMobileEntities(int x, int y, MobileEntity mobileEntity) {
		 mobileEntities[y][x] = mobileEntity;
	}


}