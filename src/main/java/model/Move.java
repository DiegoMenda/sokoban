package model;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Move {
    private final int oldX, oldY;
    private final int newX, newY;
    private final boolean boxMove;
    private final int oldBoxX, oldBoxY, newBoxX, newBoxY;

    public Move(int oldX, int oldY, int newX, int newY) {
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
        this.boxMove = false;
        this.oldBoxX = this.oldBoxY = this.newBoxX = this.newBoxY = -1;
    }

    public Move() {
        // Constructor por defecto
        this.oldX = this.oldY = this.newX = this.newY = -1;
        this.boxMove = false;
        this.oldBoxX = this.oldBoxY = this.newBoxX = this.newBoxY = -1;
    }

    public Move(int oldX, int oldY, int newX, int newY, int oldBoxX, int oldBoxY, int newBoxX, int newBoxY) {
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
        this.boxMove = true;
        this.oldBoxX = oldBoxX;
        this.oldBoxY = oldBoxY;
        this.newBoxX = newBoxX;
        this.newBoxY = newBoxY;
    }

    // Getters

    @XmlElement
    public int getOldX() {
        return oldX;
    }

    @XmlElement
    public int getOldY() {
        return oldY;
    }

    @XmlElement
    public int getNewX() {
        return newX;
    }

    @XmlElement
    public int getNewY() {
        return newY;
    }

    @XmlElement
    public boolean isBoxMove() {
        return boxMove;
    }

    @XmlElement
    public int getOldBoxX() {
        return oldBoxX;
    }

    @XmlElement
    public int getOldBoxY() {
        return oldBoxY;
    }

    @XmlElement
    public int getNewBoxX() {
        return newBoxX;
    }

    @XmlElement
    public int getNewBoxY() {
        return newBoxY;
    }

    @Override
    public String toString() {
        return "Move [oldX=" + oldX + ", oldY=" + oldY + ", newX=" + newX + ", newY=" + newY + "]";
    }
}
