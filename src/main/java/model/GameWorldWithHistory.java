package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Deque;


@XmlRootElement
public class GameWorldWithHistory {

    private GameWorld gameWorld;
    private Deque<Move> history;

    // Constructor vacío
    public GameWorldWithHistory() {
        
    }

    public GameWorldWithHistory(GameWorld gameWorld, Deque<Move> history) {
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
    public Deque<Move>getHistory() {
        return history;
    }

    public void setHistory(Deque<Move> history) {
        this.history = history;
    }
}
