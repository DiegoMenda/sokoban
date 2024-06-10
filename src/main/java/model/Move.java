package model;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Move {
    private final Position oldPosition;
    private final Position newPosition;
    private final boolean boxMove;
    private final Position oldBoxPosition;
    private final Position newBoxPosition;
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
    // Getters
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
    @Override
    public String toString() {
        return "Move [oldPosition=" + oldPosition + ", newPosition=" + newPosition + "]";
    }
}
