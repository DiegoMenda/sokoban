package controller;

import model.Air;
import model.Box;
import model.Entity;
import model.GameWorld;
import model.GameWorldWithHistory;
import model.Goal;
import model.LevelLoader;
import model.LevelSaver;
import model.SokobanLogic;
import model.Wall;
import model.Worker;
import view.GameFrame;
import view.EntitiesRenderer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameController {
    private GameWorld gameWorld;
    SokobanLogic sokobanLogic;
    private GameFrame gameFrame;
    private EntitiesRenderer entitiesRenderer;
    private GameKeyListener keyListener;
	private static final Logger logger = LoggerFactory.getLogger(GameController.class);
	private boolean gameFinished = false;

    public GameController(String levelFilePath) {
    	
        gameWorld = new GameWorld(levelFilePath);
        sokobanLogic = new SokobanLogic(gameWorld);
        entitiesRenderer = new EntitiesRenderer(gameWorld);
        gameFrame = new GameFrame(entitiesRenderer);
        keyListener = new GameKeyListener(this);

        
        initController();
    }

    private final void initController() {
        keyListener.setupKeyBindings(getGameFrame().getRootPane());
        SwingUtilities.invokeLater(() -> {
            getGameFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            getGameFrame().setVisible(true);
            getGameFrame().requestFocusInWindow();

            // buttons
            getGameFrame().setStartButtonAction(e -> startNewGame());
          
            getGameFrame().setRestartLevelButtonAction(e -> restartCurrentLevel());
            getGameFrame().setSaveGameButtonAction(e -> saveLevel());
            getGameFrame().setOpenSaveGameButtonAction(e -> loadLevel());
        });

        Timer gameTimer = new Timer(50, e -> updateGame());
        gameTimer.start();
    }


    private void restartCurrentLevel() {
    	int numero = gameWorld.getLevelNumber();
    	gameWorld.setLevel(LevelLoader.loadLevel(new File("./src/main/java/model/maps/level_"+numero+".txt")));
    	gameWorld.setLevelNumber(numero);
    	gameWorld.getLocalPuntuation().set(numero-1, 0);
    	this.gameWorld.updateFrom(gameWorld);
   	 
    	sokobanLogic.clearHistory();
    	gameFinished = false;
    	updateGame();
    	
		
	}

	private void startNewGame() {
    	gameWorld.setLevel(LevelLoader.loadLevel(new File("./src/main/java/model/maps/level_1.txt")));
    	gameWorld.setLevelNumber(1);
    	gameWorld.setPuntuation(0);
    	List<Integer> lista = new ArrayList<>();
    	lista.add(0);
    	gameWorld.setLocalPuntuation(lista);
    	this.gameWorld.updateFrom(gameWorld);
    	 
    	 sokobanLogic.clearHistory();
    	 gameFinished = false;
    	 updateGame();
    }


	public void updateGame() {
        entitiesRenderer.repaint();
        getGameFrame().setGlobalPuntuation(gameWorld.getPuntuation() + gameWorld.getActualLevelPuntuation());
        getGameFrame().setLevelName(gameWorld.getLevel().getLevelName());
        getGameFrame().setLocalPuntuation(gameWorld.getActualLevelPuntuation());
        if(!gameFinished) checkLevelCompletion();
    }
	
	
	private void checkLevelCompletion() {
	    if (sokobanLogic.isLevelCompleted()) {
	        // force update
	        SwingUtilities.invokeLater(() -> {
	            entitiesRenderer.repaint(); // repaint to see the box

	            // set delay
	            try {
	                Thread.sleep(500); // 0.5 sec delay
	            } catch (InterruptedException ex) {
	                Thread.currentThread().interrupt();
	            }

	            // move to next level
	            if (gameWorld.loadNextLevel()) {
	                sokobanLogic.clearHistory();
	                getGameFrame().centerRenderer();
	                gameFinished = false;
	            } else {
	                gameFinished = true;
	                // show congratulations message.
	                int puntuation = gameWorld.getLocalPuntuation().get(gameWorld.getLevelNumber() - 1);
	                JOptionPane.showMessageDialog(null, "Congratulations! You have reached the end of the game.\nYour score: " + puntuation, "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
	            }
	        });
	    }
	}
    
    
    public void saveLevel() {
        // create a JFileChooser 
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save level XML file");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML files", "xml");
        fileChooser.addChoosableFileFilter(filter);

        // def dir.
        File defaultDirectory = new File("./src/main/java/model/maps/");
        fileChooser.setCurrentDirectory(defaultDirectory);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            // make sure it is an xml file
            if (!fileToSave.getAbsolutePath().endsWith(".xml")) {
                fileToSave = new File(fileToSave + ".xml");
            }
            try {
                // Save to xml
                LevelSaver.saveToXML(new GameWorldWithHistory(gameWorld, sokobanLogic.getHistory()), fileToSave.getAbsolutePath());
                logger.info("Level saved successfully.");
            } catch (JAXBException e) {
                
                logger.error("Error saving level to XML", e);
            }
        }
    }
	
	public void loadLevel() {
	    // create a JFileChooser to select the XML file.
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setDialogTitle("Select saved level XML file");
	    fileChooser.setAcceptAllFileFilterUsed(false);
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("XML file", "xml");
	    fileChooser.addChoosableFileFilter(filter);

	    // def directory
	    File defaultDirectory = new File("./src/main/java/model/maps/");
	    fileChooser.setCurrentDirectory(defaultDirectory);

	    int userSelection = fileChooser.showOpenDialog(null);

	    if (userSelection == JFileChooser.APPROVE_OPTION) {
	        File fileToLoad = fileChooser.getSelectedFile();
	        try {
	            // read the file
	            GameWorldWithHistory newLevelLoaded = LevelSaver.readFromXML(fileToLoad.getAbsolutePath());
	            if (newLevelLoaded != null) {
	                
	                
	                this.gameWorld.updateFrom( newLevelLoaded.getGameWorld());

	                cleanUpArray(newLevelLoaded.getGameWorld().getLevel().getMobileEntities());
	                
	                logger.info("Level to be loaded : {}", gameWorld.getLevel());
	                logger.info("Historical movement history of the level to be uploaded: {} ", newLevelLoaded.getHistory());
	               
	                sokobanLogic.setHistory(newLevelLoaded.getHistory()); // update history
	                logger.info("Level loaded successfully.");
	                gameFinished = false;
	            } else {
	                logger.error("Failed to load level.");
	            }
	        } catch (JAXBException e) {
	            
	            logger.error("Error loading level from XML", e);
	        }
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
	

    
    
	public GameFrame getGameFrame() {
		return gameFrame;
	}

	public void setGameFrame(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}    
    

    public static void main(String[] args) {
        new GameController("./src/main/java/model/maps/level_1.txt");
    }


    
    
}