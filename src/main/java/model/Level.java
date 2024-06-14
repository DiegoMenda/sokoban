package model;

import javax.xml.bind.annotation.*;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Wall.class, Air.class, Goal.class, Worker.class, Box.class})
public class Level {

	@XmlElement
	private int col;

	@XmlElement
	private int row;

    @XmlElementWrapper(name="immovableEntities")
    @XmlElement(name="immovableEntity")
	private ImmovableEntity[][] immovableEntities;

    @XmlElementWrapper(name="mobileEntities")
    @XmlElement(name="mobileEntity")
	private MobileEntity[][] mobileEntities;

	@XmlElement
	private String levelName;

	@XmlElement
	private Worker warehouseMan;

	public Level() {
		// Este constructor vacío es necesario para JAXB
	}

	public Level(int col, int row, String levelName, ImmovableEntity[][] immovableEntities, MobileEntity[][] mobileEntities) {
		this.col = col;
		this.row = row;
		this.immovableEntities = immovableEntities;
		this.mobileEntities = mobileEntities;
		this.levelName = levelName;
	}

	public void undoMove(){
		// Lógica para deshacer un movimiento
	}

	//    public String toString() {
	//        StringBuilder print = new StringBuilder();
	//        for (int i = 0; i < row; i++) {
	//            for (int j = 0; j < col; j++) {
	//                print.append(immovableEntities[i][j]);
	//            }
	//            print.append('\n');
	//        }
	//        print.append('\n');
	//        for (int i = 0; i < row; i++) {
	//            for (int j = 0; j < col; j++) {
	//            	if(mobileEntities[i][j] != null)
	//                print.append(mobileEntities[i][j]);
	//            	else print.append('.');
	//            }
	//            print.append('\n');
	//        }
	//        
	//        return print.toString();
	//    }

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

	public MobileEntity[][] getMobileEntities() {
		return mobileEntities;
	}

	public MobileEntity getMobileEntities(int x, int y) {
		return mobileEntities[y][x];
	}

	public void setMobileEntities(int x, int y, MobileEntity mobileEntity) {
		mobileEntities[y][x] = mobileEntity;
	}


	public void updateFrom(Level other) {
		this.col = other.col;
		this.row = other.row;
		this.levelName = other.levelName;
		//this.immovableEntities = new ImmovableEntity[other.row][other.col];
		//this.mobileEntities = new MobileEntity[other.row][other.col];

		
		this.immovableEntities = (ImmovableEntity[][])other.getImmovableEntities();
		this.mobileEntities = (MobileEntity[][])other.getMobileEntities();
		// Clonar las entidades inmóviles
//		for (int i = 0; i < other.row; i++) {
//			System.arraycopy(other.immovableEntities[i], 0, this.immovableEntities[i], 0, other.col);
//		}
//
//		// Clonar las entidades móviles
//		for (int i = 0; i < other.row; i++) {
//			System.arraycopy(other.mobileEntities[i], 0, this.mobileEntities[i], 0, other.col);
//		}

		this.warehouseMan = other.warehouseMan; // Assuming warehouseMan is immutable or correctly handled
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Level Name: ").append(levelName).append("\n");
	    sb.append("Dimensions: ").append(row).append("x").append(col).append("\n");
	    sb.append("WarehouseMan: ").append(warehouseMan).append("\n");
	    sb.append("Immovable Entities:\n");
	    for (int i = 0; i < row; i++) {
	        for (int j = 0; j < col; j++) {
	            sb.append(immovableEntities[i][j] != null ? immovableEntities[i][j].toString() : "null").append(" ");
	        }
	        sb.append("\n");
	    }
	    sb.append("Mobile Entities:\n");
	    for (int i = 0; i < row; i++) {
	        for (int j = 0; j < col; j++) {
	            sb.append(mobileEntities[i][j] != null ? mobileEntities[i][j].toString() : "null").append(" ");
	        }
	        sb.append("\n");
	    }
	    return sb.toString();
	}


}
