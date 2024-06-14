package controller;

import model.Air;
import model.Box;
import model.Entity;
import model.GameWorld;
import model.GameWorldWithHistory;
import model.Goal;
import model.LevelSaver;
import model.SokobanLogic;
import model.Wall;
import model.Worker;
import view.GameFrame;
import view.EntitiesRenderer;

import java.util.LinkedList;

import javax.swing.*;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameController {
    private GameWorld gameWorld;
    SokobanLogic sokobanLogic;
    private GameFrame gameFrame;
    private EntitiesRenderer entitiesRenderer;
    private GameKeyListener keyListener;
	private static final Logger logger = LoggerFactory.getLogger(SokobanLogic.class);


    public GameController(String levelFilePath) {
    	
        gameWorld = new GameWorld(levelFilePath);
        sokobanLogic = new SokobanLogic(gameWorld);
        entitiesRenderer = new EntitiesRenderer(gameWorld);
        setGameFrame(new GameFrame(entitiesRenderer));
        keyListener = new GameKeyListener(this);

        
        initController();
    }

    private void initController() {
        keyListener.setupKeyBindings(getGameFrame().getRootPane());
        SwingUtilities.invokeLater(() -> {
            getGameFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            getGameFrame().setVisible(true);
            getGameFrame().requestFocusInWindow();
            
        });
        
        Timer gameTimer = new Timer(50, e -> updateGame());
        gameTimer.start();
    }


    public void updateGame() {
        entitiesRenderer.repaint();
        getGameFrame().setPuntuation(gameWorld.getPuntuation());
        getGameFrame().setLevelName(gameWorld.getLevel().getLevelName());
        checkLevelCompletion();
    }
    
    private void checkLevelCompletion() {
        if (sokobanLogic.isLevelCompleted()) {
            entitiesRenderer.repaint();
            // Introduces a delay of 1 second (1000 ms) before loading the next level.
            //Timer delayTimer = new Timer(1000, evt -> {
                // Cargar el siguiente nivel
                System.err.println(gameWorld.getLevel());
                gameWorld.loadNextLevel();
                sokobanLogic.clearHistory();
                // Update view
                //entitiesRenderer.setMobileEntity(gameWorld.getLevel().getMobileEntities());
                //entitiesRenderer.setImmovableEntity(gameWorld.getLevel().getImmovableEntities());
                getGameFrame().centerRenderer();
                //((Timer) evt.getSource()).stop(); // Detener el timer después de la ejecución
            //});
            //delayTimer.setRepeats(false);
            //delayTimer.start();
        }
    }
    
    
	public void saveLevel() {
		try {
			LevelSaver.saveToXML(new GameWorldWithHistory(gameWorld, sokobanLogic.getHistory()), "./src/main/java/model/maps/guarda.xml");
		} catch (JAXBException e) {

			e.printStackTrace();
			System.out.println("ERRRRRROOOOOOOOOR saveToXML");
		}
		
	}
	
	public void loadLevel() {
	    try {
	        GameWorldWithHistory newLevelLoaded = LevelSaver.readFromXML("./src/main/java/model/maps/guarda.xml");
	        if (newLevelLoaded != null) {
	        	System.err.println(newLevelLoaded.getGameWorld().getLevel());
	        	System.err.println(newLevelLoaded.getGameWorld().getLevel().getImmovableEntities(0, 0).getX());
	            this.gameWorld.updateFrom((GameWorld)newLevelLoaded.getGameWorld());
	                   
	            cleanUpArray(newLevelLoaded.getGameWorld().getLevel().getMobileEntities());
	            
	        	System.err.println(gameWorld.getLevel());

	            sokobanLogic.setHistory(newLevelLoaded.getHistory()); // Update history as well
	            logger.info("Level loaded successfully.");
	        } else {
	            logger.error("Failed to load level.");
	        }
	    } catch (JAXBException e) {
	        e.printStackTrace();
	        logger.error("Error loading level from XML", e);
	    }
	}
	

	
    private <T extends Entity> void cleanUpArray(T[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] != null && !(array[i][j] instanceof Wall) && 
                    !(array[i][j] instanceof Air) && !(array[i][j] instanceof Goal) && 
                    !(array[i][j] instanceof Worker) && !(array[i][j] instanceof Box)) {
                    array[i][j] = null;
                }
            }
        }
    }
	
    
    

    public static void main(String[] args) {
        new GameController("./src/main/java/model/maps/level_1.txt");
    }

	public GameFrame getGameFrame() {
		return gameFrame;
	}

	public void setGameFrame(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}
    
    
}