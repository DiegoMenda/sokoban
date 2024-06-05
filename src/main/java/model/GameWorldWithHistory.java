package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Stack;

@XmlRootElement
public class GameWorldWithHistory {

    private GameWorld gameWorld;
    private Stack<Move> history;

    // Constructor vacío
    public GameWorldWithHistory() {
        
    }

    public GameWorldWithHistory(GameWorld gameWorld, Stack<Move> history) {
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
    public Stack<Move> getHistory() {
        return history;
    }

    public void setHistory(Stack<Move> history) {
        this.history = history;
    }
}
