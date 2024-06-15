package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "history")
public class Move {
    private Position oldPosition;
    private Position newPosition;
    private boolean boxMove;
    private Position oldBoxPosition;
    private Position newBoxPosition;

    public Move(Position oldPosition, Position newPosition) {
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
        this.boxMove = false;
        this.oldBoxPosition = this.newBoxPosition = null;
    }

    public Move() {
        // Constructor por defecto
        this.oldPosition = this.newPosition = null;
        this.boxMove = false;
        this.oldBoxPosition = this.newBoxPosition = null;
    }

    public Move(Position oldPosition, Position newPosition, Position oldBoxPosition, Position newBoxPosition) {
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
        this.boxMove = true;
        this.oldBoxPosition = oldBoxPosition;
        this.newBoxPosition = newBoxPosition;
    }

    // Getters and setters for JAXB
    @XmlElement
    public int getOldX() {
        return oldPosition != null ? oldPosition.getX() : -1;
    }

    @XmlElement
    public int getOldY() {
        return oldPosition != null ? oldPosition.getY() : -1;
    }

    @XmlElement
    public int getNewX() {
        return newPosition != null ? newPosition.getX() : -1;
    }

    @XmlElement
    public int getNewY() {
        return newPosition != null ? newPosition.getY() : -1;
    }

    @XmlElement
    public boolean isBoxMove() {
        return boxMove;
    }

    @XmlElement
    public int getOldBoxX() {
        return oldBoxPosition != null ? oldBoxPosition.getX() : -1;
    }

    @XmlElement
    public int getOldBoxY() {
        return oldBoxPosition != null ? oldBoxPosition.getY() : -1;
    }

    @XmlElement
    public int getNewBoxX() {
        return newBoxPosition != null ? newBoxPosition.getX() : -1;
    }

    @XmlElement
    public int getNewBoxY() {
        return newBoxPosition != null ? newBoxPosition.getY() : -1;
    }

    public void setOldX(int x) {
        if (this.oldPosition == null) this.oldPosition = new Position();
        this.oldPosition.setX(x);
    }

    public void setOldY(int y) {
        if (this.oldPosition == null) this.oldPosition = new Position();
        this.oldPosition.setY(y);
    }

    public void setNewX(int x) {
        if (this.newPosition == null) this.newPosition = new Position();
        this.newPosition.setX(x);
    }

    public void setNewY(int y) {
        if (this.newPosition == null) this.newPosition = new Position();
        this.newPosition.setY(y);
    }

    public void setBoxMove(boolean boxMove) {
        this.boxMove = boxMove;
    }

    public void setOldBoxX(int x) {
        if (this.oldBoxPosition == null) this.oldBoxPosition = new Position();
        this.oldBoxPosition.setX(x);
    }

    public void setOldBoxY(int y) {
        if (this.oldBoxPosition == null) this.oldBoxPosition = new Position();
        this.oldBoxPosition.setY(y);
    }

    public void setNewBoxX(int x) {
        if (this.newBoxPosition == null) this.newBoxPosition = new Position();
        this.newBoxPosition.setX(x);
    }

    public void setNewBoxY(int y) {
        if (this.newBoxPosition == null) this.newBoxPosition = new Position();
        this.newBoxPosition.setY(y);
    }

    @Override
    public String toString() {
        return "Move [" + oldPosition + "->" + newPosition + "]";
    }
}
