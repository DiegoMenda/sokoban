package model;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;


@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Wall.class, Air.class, Goal.class, Worker.class, Box.class})
public class GameWorldWithHistory {

    private GameWorld gameWorld;
    private List<Move> history;

    // Constructor vacío
    public GameWorldWithHistory() {
        
    }

    public GameWorldWithHistory(GameWorld gameWorld, List<Move> history) {
        this.history=history;
        this.gameWorld = gameWorld;
    }

    @XmlElement
    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    @XmlElement
    public List<Move>getHistory() {
        return history;
    }

    public void setHistory(List<Move> history) {
        this.history = history;
    }
}
