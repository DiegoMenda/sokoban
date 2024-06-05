package model;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Level {

    @XmlElement
    private int col;

    @XmlElement
    private int row;

    @XmlElementWrapper(name="immovableEntities")
    @XmlElement(name="row")
    private ImmovableEntity[][] immovableEntities;

    @XmlElementWrapper(name="mobileEntities")
    @XmlElement(name="row")
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
